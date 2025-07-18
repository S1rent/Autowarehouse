package com.autowarehouse.websocket;

import com.autowarehouse.dto.SendMessageRequest;
import com.autowarehouse.dto.WebSocketMessage;
import com.autowarehouse.entity.User;
import com.autowarehouse.service.ChatService;
import com.autowarehouse.service.WebSocketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.quarkus.arc.runtime.BeanContainer;
import io.quarkus.arc.Arc;
import io.quarkus.arc.ManagedContext;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@ServerEndpoint("/ws/customer-service/{userId}")
@ApplicationScoped
public class CustomerServiceWebSocket {

    private static final Logger LOG = Logger.getLogger(CustomerServiceWebSocket.class);

    @Inject
    WebSocketService webSocketService;

    @Inject
    ChatService chatService;

    @Inject
    ObjectMapper objectMapper;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        LOG.infof("WebSocket connection opened for user %d", userId);
        
        try {
            // Basic validation - just check if userId is valid
            if (userId == null || userId <= 0) {
                LOG.errorf("Invalid user ID: %d, closing connection", userId);
                session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "Invalid user ID"));
                return;
            }

            // Add user session (this will handle user validation asynchronously)
            webSocketService.addUserSession(userId, session);
            
            // Send welcome message
            webSocketService.sendSuccessMessage(userId, "Connected to customer service");
            
            LOG.infof("User %d successfully connected to WebSocket", userId);
            
        } catch (Exception e) {
            LOG.errorf("Error opening WebSocket connection for user %d: %s", userId, e.getMessage());
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, "Connection error"));
            } catch (IOException ioException) {
                LOG.errorf("Error closing WebSocket session: %s", ioException.getMessage());
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userId") Long userId) {
        LOG.debugf("Received WebSocket message from user %d: %s", userId, message);
        
        try {
            // Parse incoming message
            WebSocketMessage wsMessage = objectMapper.readValue(message, WebSocketMessage.class);
            
            // Handle different message types
            switch (wsMessage.type) {
                case JOIN_ROOM:
                    handleJoinRoom(wsMessage, userId);
                    break;
                    
                case LEAVE_ROOM:
                    handleLeaveRoom(wsMessage, userId);
                    break;
                    
                case SEND_MESSAGE:
                    handleSendMessage(wsMessage, userId);
                    break;
                    
                case TYPING_START:
                    handleTypingStart(wsMessage, userId);
                    break;
                    
                case TYPING_STOP:
                    handleTypingStop(wsMessage, userId);
                    break;
                    
                case MESSAGE_READ:
                    handleMessageRead(wsMessage, userId);
                    break;
                    
                default:
                    LOG.warnf("Unknown message type: %s from user %d", wsMessage.type, userId);
                    webSocketService.sendErrorMessage(userId, "Unknown message type");
            }
            
        } catch (Exception e) {
            LOG.errorf("Error processing WebSocket message from user %d: %s", userId, e.getMessage());
            webSocketService.sendErrorMessage(userId, "Error processing message: " + e.getMessage());
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") Long userId, CloseReason closeReason) {
        LOG.infof("WebSocket connection closed for user %d. Reason: %s", userId, closeReason.getReasonPhrase());
        
        try {
            webSocketService.removeUserSession(userId, session);
        } catch (Exception e) {
            LOG.errorf("Error closing WebSocket connection for user %d: %s", userId, e.getMessage());
        }
    }

    @OnError
    public void onError(Session session, @PathParam("userId") Long userId, Throwable throwable) {
        LOG.errorf("WebSocket error for user %d: %s", userId, throwable.getMessage());
        
        try {
            webSocketService.sendErrorMessage(userId, "Connection error occurred");
            webSocketService.removeUserSession(userId, session);
        } catch (Exception e) {
            LOG.errorf("Error handling WebSocket error for user %d: %s", userId, e.getMessage());
        }
    }

    // Message handlers
    private void handleJoinRoom(WebSocketMessage wsMessage, Long userId) {
        if (wsMessage.ticketId == null) {
            webSocketService.sendErrorMessage(userId, "Ticket ID is required to join room");
            return;
        }

        // Run database operations on a worker thread with proper CDI context
        CompletableFuture.runAsync(() -> {
            ManagedContext requestContext = Arc.container().requestContext();
            if (requestContext.isActive()) {
                executeJoinRoom(wsMessage, userId);
            } else {
                requestContext.activate();
                try {
                    executeJoinRoom(wsMessage, userId);
                } finally {
                    requestContext.terminate();
                }
            }
        });
    }

    private void executeJoinRoom(WebSocketMessage wsMessage, Long userId) {
        try {
            // Validate user can access this ticket
            if (!chatService.validateMessageAccess(wsMessage.ticketId, userId)) {
                webSocketService.sendErrorMessage(userId, "Not authorized to access this ticket");
                return;
            }

            webSocketService.joinTicketRoom(wsMessage.ticketId, userId);
            webSocketService.sendSuccessMessage(userId, "Joined ticket room " + wsMessage.ticketId);
            
        } catch (Exception e) {
            LOG.errorf("Error joining room for user %d: %s", userId, e.getMessage());
            webSocketService.sendErrorMessage(userId, "Error joining room: " + e.getMessage());
        }
    }

    private void handleLeaveRoom(WebSocketMessage wsMessage, Long userId) {
        if (wsMessage.ticketId == null) {
            webSocketService.sendErrorMessage(userId, "Ticket ID is required to leave room");
            return;
        }

        try {
            webSocketService.leaveTicketRoom(wsMessage.ticketId, userId);
            webSocketService.sendSuccessMessage(userId, "Left ticket room " + wsMessage.ticketId);
            
        } catch (Exception e) {
            LOG.errorf("Error leaving room for user %d: %s", userId, e.getMessage());
            webSocketService.sendErrorMessage(userId, "Error leaving room: " + e.getMessage());
        }
    }

    private void handleSendMessage(WebSocketMessage wsMessage, Long userId) {
        if (wsMessage.ticketId == null || wsMessage.message == null || wsMessage.message.trim().isEmpty()) {
            webSocketService.sendErrorMessage(userId, "Ticket ID and message are required");
            return;
        }

        // Run database operations on a worker thread with proper CDI context
        CompletableFuture.runAsync(() -> {
            ManagedContext requestContext = Arc.container().requestContext();
            if (requestContext.isActive()) {
                executeSendMessage(wsMessage, userId);
            } else {
                requestContext.activate();
                try {
                    executeSendMessage(wsMessage, userId);
                } finally {
                    requestContext.terminate();
                }
            }
        });
    }

    private void executeSendMessage(WebSocketMessage wsMessage, Long userId) {
        try {
            // Create send message request
            SendMessageRequest request = new SendMessageRequest(wsMessage.ticketId, wsMessage.message);
            
            // Send message through chat service
            var messageResponse = chatService.sendMessage(request, userId);
            
            // Broadcast to all users in the ticket room
            webSocketService.broadcastNewMessage(messageResponse);
            
        } catch (Exception e) {
            LOG.errorf("Error sending message for user %d: %s", userId, e.getMessage());
            webSocketService.sendErrorMessage(userId, "Error sending message: " + e.getMessage());
        }
    }

    private void handleTypingStart(WebSocketMessage wsMessage, Long userId) {
        if (wsMessage.ticketId == null) {
            webSocketService.sendErrorMessage(userId, "Ticket ID is required for typing indicator");
            return;
        }

        // Run database operations on a worker thread with proper CDI context
        CompletableFuture.runAsync(() -> {
            ManagedContext requestContext = Arc.container().requestContext();
            if (requestContext.isActive()) {
                executeTypingStart(wsMessage, userId);
            } else {
                requestContext.activate();
                try {
                    executeTypingStart(wsMessage, userId);
                } finally {
                    requestContext.terminate();
                }
            }
        });
    }

    private void executeTypingStart(WebSocketMessage wsMessage, Long userId) {
        try {
            // Validate user can access this ticket
            if (!chatService.validateMessageAccess(wsMessage.ticketId, userId)) {
                webSocketService.sendErrorMessage(userId, "Not authorized to access this ticket");
                return;
            }

            webSocketService.broadcastTypingStart(wsMessage.ticketId, userId);
            
        } catch (Exception e) {
            LOG.errorf("Error handling typing start for user %d: %s", userId, e.getMessage());
        }
    }

    private void handleTypingStop(WebSocketMessage wsMessage, Long userId) {
        if (wsMessage.ticketId == null) {
            webSocketService.sendErrorMessage(userId, "Ticket ID is required for typing indicator");
            return;
        }

        // Run database operations on a worker thread with proper CDI context
        CompletableFuture.runAsync(() -> {
            ManagedContext requestContext = Arc.container().requestContext();
            if (requestContext.isActive()) {
                executeTypingStop(wsMessage, userId);
            } else {
                requestContext.activate();
                try {
                    executeTypingStop(wsMessage, userId);
                } finally {
                    requestContext.terminate();
                }
            }
        });
    }

    private void executeTypingStop(WebSocketMessage wsMessage, Long userId) {
        try {
            // Validate user can access this ticket
            if (!chatService.validateMessageAccess(wsMessage.ticketId, userId)) {
                webSocketService.sendErrorMessage(userId, "Not authorized to access this ticket");
                return;
            }

            webSocketService.broadcastTypingStop(wsMessage.ticketId, userId);
            
        } catch (Exception e) {
            LOG.errorf("Error handling typing stop for user %d: %s", userId, e.getMessage());
        }
    }

    private void handleMessageRead(WebSocketMessage wsMessage, Long userId) {
        if (wsMessage.ticketId == null) {
            webSocketService.sendErrorMessage(userId, "Ticket ID is required to mark messages as read");
            return;
        }

        // Run database operations on a worker thread with proper CDI context
        CompletableFuture.runAsync(() -> {
            ManagedContext requestContext = Arc.container().requestContext();
            if (requestContext.isActive()) {
                executeMessageRead(wsMessage, userId);
            } else {
                requestContext.activate();
                try {
                    executeMessageRead(wsMessage, userId);
                } finally {
                    requestContext.terminate();
                }
            }
        });
    }

    private void executeMessageRead(WebSocketMessage wsMessage, Long userId) {
        try {
            chatService.markMessagesAsRead(wsMessage.ticketId, userId);
            webSocketService.sendSuccessMessage(userId, "Messages marked as read");
            
        } catch (Exception e) {
            LOG.errorf("Error marking messages as read for user %d: %s", userId, e.getMessage());
            webSocketService.sendErrorMessage(userId, "Error marking messages as read: " + e.getMessage());
        }
    }
}
