package com.autowarehouse.service;

import com.autowarehouse.dto.MessageResponse;
import com.autowarehouse.dto.TicketResponse;
import com.autowarehouse.dto.WebSocketMessage;
import com.autowarehouse.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.Session;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ApplicationScoped
public class WebSocketService {

    private static final Logger LOG = Logger.getLogger(WebSocketService.class);

    @Inject
    ObjectMapper objectMapper;

    // Store active WebSocket sessions
    // Key: userId, Value: Set of sessions (user can have multiple tabs/devices)
    private final Map<Long, Set<Session>> userSessions = new ConcurrentHashMap<>();
    
    // Store ticket room memberships
    // Key: ticketId, Value: Set of userIds in that room
    private final Map<Long, Set<Long>> ticketRooms = new ConcurrentHashMap<>();
    
    // Store session metadata
    // Key: sessionId, Value: userId
    private final Map<String, Long> sessionUsers = new ConcurrentHashMap<>();

    public void addUserSession(Long userId, Session session) {
        LOG.infof("Adding WebSocket session for user %d", userId);
        
        userSessions.computeIfAbsent(userId, k -> new CopyOnWriteArraySet<>()).add(session);
        sessionUsers.put(session.getId(), userId);
        
        // Update user online status asynchronously to avoid blocking IO thread
        updateUserOnlineStatusAsync(userId, true, session.getId());
        
        // Notify other users that this user is online (skip user validation for now)
        broadcastUserOnlineSimple(userId);
        
        LOG.infof("User %d now has %d active sessions", userId, userSessions.get(userId).size());
    }

    public void removeUserSession(Long userId, Session session) {
        LOG.infof("Removing WebSocket session for user %d", userId);
        
        Set<Session> sessions = userSessions.get(userId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                userSessions.remove(userId);
                // Update user offline status
                updateUserOnlineStatus(userId, false, null);
                // Notify other users that this user is offline
                broadcastUserOffline(userId);
            }
        }
        
        sessionUsers.remove(session.getId());
        
        // Remove user from all ticket rooms
        removeUserFromAllRooms(userId);
        
        LOG.infof("Removed session for user %d", userId);
    }

    public void joinTicketRoom(Long ticketId, Long userId) {
        LOG.infof("User %d joining ticket room %d", userId, ticketId);
        
        ticketRooms.computeIfAbsent(ticketId, k -> new CopyOnWriteArraySet<>()).add(userId);
        
        // Notify other users in the room (skip database lookup to avoid blocking IO thread)
        WebSocketMessage message = WebSocketMessage.joinRoom(ticketId, userId, "User " + userId);
        broadcastToTicketRoom(ticketId, message, userId);
        
        LOG.infof("User %d joined ticket room %d", userId, ticketId);
    }

    public void leaveTicketRoom(Long ticketId, Long userId) {
        LOG.infof("User %d leaving ticket room %d", userId, ticketId);
        
        Set<Long> roomUsers = ticketRooms.get(ticketId);
        if (roomUsers != null) {
            roomUsers.remove(userId);
            if (roomUsers.isEmpty()) {
                ticketRooms.remove(ticketId);
            }
        }
        
        // Notify other users in the room (skip database lookup to avoid blocking IO thread)
        WebSocketMessage message = WebSocketMessage.leaveRoom(ticketId, userId, "User " + userId);
        broadcastToTicketRoom(ticketId, message, userId);
        
        LOG.infof("User %d left ticket room %d", userId, ticketId);
    }

    public void broadcastNewMessage(MessageResponse messageResponse) {
        LOG.infof("Broadcasting new message for ticket %d", messageResponse.ticketId);
        
        WebSocketMessage wsMessage = WebSocketMessage.newMessage(messageResponse);
        broadcastToTicketRoom(messageResponse.ticketId, wsMessage, null);
    }

    public void broadcastTicketCreated(TicketResponse ticket) {
        LOG.infof("Broadcasting ticket created: %d", ticket.id);
        
        WebSocketMessage wsMessage = WebSocketMessage.ticketCreated(ticket);
        
        // Broadcast to all online agents
        broadcastToAgents(wsMessage);
        
        // Broadcast to the customer who created the ticket
        sendToUser(ticket.customerId, wsMessage);
    }

    public void broadcastTicketUpdated(TicketResponse ticket) {
        LOG.infof("Broadcasting ticket updated: %d", ticket.id);
        
        WebSocketMessage wsMessage = WebSocketMessage.ticketUpdated(ticket);
        broadcastToTicketRoom(ticket.id, wsMessage, null);
    }

    public void broadcastAgentAssigned(Long ticketId, Long agentId, String agentName) {
        LOG.infof("Broadcasting agent assigned to ticket %d", ticketId);
        
        WebSocketMessage wsMessage = WebSocketMessage.agentAssigned(ticketId, agentId, agentName);
        broadcastToTicketRoom(ticketId, wsMessage, null);
    }

    public void broadcastTypingStart(Long ticketId, Long userId) {
        // Skip database lookup to avoid blocking IO thread
        WebSocketMessage wsMessage = WebSocketMessage.typingStart(ticketId, userId, "User " + userId);
        broadcastToTicketRoom(ticketId, wsMessage, userId);
    }

    public void broadcastTypingStop(Long ticketId, Long userId) {
        // Skip database lookup to avoid blocking IO thread
        WebSocketMessage wsMessage = WebSocketMessage.typingStop(ticketId, userId, "User " + userId);
        broadcastToTicketRoom(ticketId, wsMessage, userId);
    }

    public void sendErrorMessage(Long userId, String errorMessage) {
        WebSocketMessage wsMessage = WebSocketMessage.error(errorMessage);
        sendToUser(userId, wsMessage);
    }

    public void sendSuccessMessage(Long userId, String successMessage) {
        WebSocketMessage wsMessage = WebSocketMessage.success(successMessage);
        sendToUser(userId, wsMessage);
    }

    public void sendNotification(Long userId, String notificationMessage) {
        WebSocketMessage wsMessage = WebSocketMessage.notification(notificationMessage);
        sendToUser(userId, wsMessage);
    }

    // Private helper methods
    private void sendToUser(Long userId, WebSocketMessage message) {
        Set<Session> sessions = userSessions.get(userId);
        if (sessions != null && !sessions.isEmpty()) {
            String messageJson = serializeMessage(message);
            if (messageJson != null) {
                sessions.removeIf(session -> {
                    try {
                        if (session.isOpen()) {
                            session.getAsyncRemote().sendText(messageJson);
                            return false;
                        } else {
                            return true; // Remove closed sessions
                        }
                    } catch (Exception e) {
                        LOG.errorf("Error sending message to user %d: %s", userId, e.getMessage());
                        return true; // Remove problematic sessions
                    }
                });
            }
        }
    }

    private void broadcastToTicketRoom(Long ticketId, WebSocketMessage message, Long excludeUserId) {
        Set<Long> roomUsers = ticketRooms.get(ticketId);
        if (roomUsers != null && !roomUsers.isEmpty()) {
            for (Long userId : roomUsers) {
                if (excludeUserId == null || !userId.equals(excludeUserId)) {
                    sendToUser(userId, message);
                }
            }
        }
    }

    private void broadcastToAgents(WebSocketMessage message) {
        // Broadcast to all online users (assuming admins are online)
        // In a production environment, you would maintain a separate list of admin user IDs
        // or use a non-blocking approach to check user roles
        userSessions.keySet().forEach(userId -> {
            // For now, broadcast to all online users to avoid blocking database calls
            // This can be optimized later by maintaining admin user IDs in memory
            sendToUser(userId, message);
        });
    }

    private void broadcastUserOnline(Long userId) {
        User user = User.findById(userId);
        if (user != null) {
            WebSocketMessage message = WebSocketMessage.userOnline(userId, user.getFullName());
            
            // Broadcast to all other online users
            userSessions.keySet().forEach(otherUserId -> {
                if (!otherUserId.equals(userId)) {
                    sendToUser(otherUserId, message);
                }
            });
        }
    }

    private void broadcastUserOffline(Long userId) {
        User user = User.findById(userId);
        if (user != null) {
            WebSocketMessage message = WebSocketMessage.userOffline(userId, user.getFullName());
            
            // Broadcast to all other online users
            userSessions.keySet().forEach(otherUserId -> {
                if (!otherUserId.equals(userId)) {
                    sendToUser(otherUserId, message);
                }
            });
        }
    }

    private void removeUserFromAllRooms(Long userId) {
        ticketRooms.entrySet().removeIf(entry -> {
            entry.getValue().remove(userId);
            return entry.getValue().isEmpty();
        });
    }

    private void updateUserOnlineStatus(Long userId, boolean isOnline, String sessionId) {
        try {
            User user = User.findById(userId);
            if (user != null) {
                if (isOnline) {
                    user.setOnline(sessionId);
                } else {
                    user.setOffline();
                }
                user.persist();
            }
        } catch (Exception e) {
            LOG.errorf("Error updating user online status for user %d: %s", userId, e.getMessage());
        }
    }

    private void updateUserOnlineStatusAsync(Long userId, boolean isOnline, String sessionId) {
        // For now, just skip the database update to avoid blocking IO thread
        // In a production environment, you would use @Async or a message queue
        LOG.debugf("Skipping database update for user %d online status to avoid blocking IO thread", userId);
    }

    private void broadcastUserOnlineSimple(Long userId) {
        // Simple broadcast without database lookup to avoid blocking IO thread
        WebSocketMessage message = WebSocketMessage.userOnline(userId, "User " + userId);
        
        // Broadcast to all other online users
        userSessions.keySet().forEach(otherUserId -> {
            if (!otherUserId.equals(userId)) {
                sendToUser(otherUserId, message);
            }
        });
    }

    private String serializeMessage(WebSocketMessage message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            LOG.errorf("Error serializing WebSocket message: %s", e.getMessage());
            return null;
        }
    }

    // Public utility methods
    public boolean isUserOnline(Long userId) {
        return userSessions.containsKey(userId) && !userSessions.get(userId).isEmpty();
    }

    public int getOnlineUserCount() {
        return userSessions.size();
    }

    public Set<Long> getOnlineUsers() {
        return userSessions.keySet();
    }

    public Set<Long> getUsersInTicketRoom(Long ticketId) {
        return ticketRooms.getOrDefault(ticketId, new CopyOnWriteArraySet<>());
    }

    public int getActiveSessionCount() {
        return userSessions.values().stream().mapToInt(Set::size).sum();
    }

    public Long getUserBySessionId(String sessionId) {
        return sessionUsers.get(sessionId);
    }

    public void cleanup() {
        LOG.info("Cleaning up WebSocket service");
        
        // Remove closed sessions
        userSessions.entrySet().removeIf(entry -> {
            entry.getValue().removeIf(session -> !session.isOpen());
            return entry.getValue().isEmpty();
        });
        
        // Clean up session metadata
        sessionUsers.entrySet().removeIf(entry -> {
            Long userId = entry.getValue();
            Set<Session> sessions = userSessions.get(userId);
            return sessions == null || sessions.isEmpty();
        });
        
        LOG.infof("WebSocket cleanup completed. Active users: %d, Active sessions: %d", 
                 getOnlineUserCount(), getActiveSessionCount());
    }
}
