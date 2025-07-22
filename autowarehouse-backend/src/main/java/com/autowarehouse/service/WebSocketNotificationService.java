package com.autowarehouse.service;

import com.autowarehouse.entity.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ApplicationScoped
public class WebSocketNotificationService {

    private static final Logger LOG = Logger.getLogger(WebSocketNotificationService.class);

    @Inject
    ObjectMapper objectMapper;

    // Store active WebSocket sessions by user ID
    private final Map<Long, Set<Session>> userSessions = new ConcurrentHashMap<>();

    public void addUserSession(Long userId, Session session) {
        userSessions.computeIfAbsent(userId, k -> new CopyOnWriteArraySet<>()).add(session);
        LOG.infof("Added WebSocket session for user %d. Total sessions: %d", userId, userSessions.get(userId).size());
    }

    public void removeUserSession(Long userId, Session session) {
        Set<Session> sessions = userSessions.get(userId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                userSessions.remove(userId);
            }
            LOG.infof("Removed WebSocket session for user %d. Remaining sessions: %d", userId, sessions.size());
        }
    }

    public void sendNotificationToUser(Long userId, Notification notification) {
        Set<Session> sessions = userSessions.get(userId);
        if (sessions == null || sessions.isEmpty()) {
            LOG.infof("No active WebSocket sessions for user %d", userId);
            return;
        }

        try {
            // Create notification DTO for WebSocket
            NotificationWebSocketDTO dto = new NotificationWebSocketDTO();
            dto.id = notification.id;
            dto.title = notification.title;
            dto.message = notification.message;
            dto.type = notification.type.toString();
            dto.referenceId = notification.referenceId;
            dto.referenceType = notification.referenceType;
            dto.isRead = notification.isRead;
            dto.createdAt = notification.createdAt.toString();
            dto.icon = notification.type.getDefaultIcon();

            String jsonMessage = objectMapper.writeValueAsString(dto);

            // Send to all active sessions for this user
            sessions.removeIf(session -> {
                try {
                    if (session.isOpen()) {
                        session.getAsyncRemote().sendText(jsonMessage);
                        return false; // Keep session
                    } else {
                        return true; // Remove closed session
                    }
                } catch (Exception e) {
                    LOG.errorf(e, "Error sending WebSocket message to user %d", userId);
                    return true; // Remove problematic session
                }
            });

            LOG.infof("Sent WebSocket notification to %d sessions for user %d", sessions.size(), userId);

        } catch (Exception e) {
            LOG.errorf(e, "Error creating WebSocket notification for user %d", userId);
        }
    }

    public void sendMessageToUser(Long userId, String message) {
        Set<Session> sessions = userSessions.get(userId);
        if (sessions == null || sessions.isEmpty()) {
            LOG.infof("No active WebSocket sessions for user %d", userId);
            return;
        }

        sessions.removeIf(session -> {
            try {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendText(message);
                    return false; // Keep session
                } else {
                    return true; // Remove closed session
                }
            } catch (Exception e) {
                LOG.errorf(e, "Error sending WebSocket message to user %d", userId);
                return true; // Remove problematic session
            }
        });

        LOG.infof("Sent WebSocket message to %d sessions for user %d", sessions.size(), userId);
    }

    public boolean hasActiveSession(Long userId) {
        Set<Session> sessions = userSessions.get(userId);
        return sessions != null && !sessions.isEmpty();
    }

    public int getActiveSessionCount(Long userId) {
        Set<Session> sessions = userSessions.get(userId);
        return sessions != null ? sessions.size() : 0;
    }

    public int getTotalActiveSessionCount() {
        return userSessions.values().stream().mapToInt(Set::size).sum();
    }

    // DTO for WebSocket notification messages
    public static class NotificationWebSocketDTO {
        public Long id;
        public String title;
        public String message;
        public String type;
        public Long referenceId;
        public String referenceType;
        public Boolean isRead;
        public String createdAt;
        public String icon;
    }
}
