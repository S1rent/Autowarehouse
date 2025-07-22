# Kafka Notification System Implementation Tasks

## Overview
This document outlines the tasks needed to implement a Kafka-based notification system for the Autowarehouse application. The system will handle real-time notifications for both customers and admins based on specific business events.

## Notification Requirements

### Customer Notifications (when admin actions occur):
- **orderShipped**: Admin marks order as shipped
- **orderDelivered**: Admin marks order as delivered  
- **orderCancelled**: Admin marks order as cancelled
- **orderRefunded**: Admin marks order as refunded
- **customerService**: Admin sends customer service chat message

### Admin Notifications (when customer actions occur):
- **orderConfirmed**: Customer places an order
- **adminCustomerService**: Customer sends customer service chat message

## Current System Analysis

### Backend Status:
- ✅ Kafka dependencies exist in pom.xml (currently commented out)
- ✅ Notification entity and service exist
- ✅ NotificationType enum has required types (missing some new ones)
- ✅ Order service has status update methods
- ✅ Chat service exists for customer service
- ❌ No Kafka producers/consumers implemented
- ❌ No Kafka configuration active

### Frontend Status:
- ✅ User notification view exists (with mock data)
- ✅ Admin notification view exists (with mock data)
- ❌ No real-time notification integration
- ❌ No API integration for notifications

## Local Kafka Setup Tasks

### 1. Kafka Infrastructure Setup
```bash
# Create docker-compose.yml for local Kafka setup
version: '3.8'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.4.0
    hostname: zookeeper
    container_name: zookeeper
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  kafka:
    image: confluentinc/cp-kafka:7.4.0
    hostname: kafka
    container_name: kafka
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
      - "9997:9997"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
      KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS: 0
      KAFKA_JMX_PORT: 9997
      KAFKA_JMX_HOSTNAME: localhost

  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    container_name: kafka-ui
    depends_on:
      - kafka
    ports:
      - "8080:8080"
    environment:
      KAFKA_CLUSTERS_0_NAME: local
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka:29092
      KAFKA_CLUSTERS_0_ZOOKEEPER: zookeeper:2181
```

### 2. Kafka Topics Creation
Create script to initialize required topics:
```bash
#!/bin/bash
# create-topics.sh

# Wait for Kafka to be ready
sleep 30

# Create topics
docker exec kafka kafka-topics --create --topic notification-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
docker exec kafka kafka-topics --create --topic order-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
docker exec kafka kafka-topics --create --topic customer-service-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

# List topics to verify
docker exec kafka kafka-topics --list --bootstrap-server localhost:9092
```

## Backend Implementation Tasks

### 3. Update Backend Configuration
**File**: `autowarehouse-backend/src/main/resources/application.properties`

```properties
# Uncomment and update Kafka Configuration
kafka.bootstrap.servers=${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}

# Notification Events Channel
mp.messaging.outgoing.notification-events.connector=smallrye-kafka
mp.messaging.outgoing.notification-events.topic=notification-events
mp.messaging.outgoing.notification-events.value.serializer=org.apache.kafka.common.serialization.StringSerializer
mp.messaging.outgoing.notification-events.key.serializer=org.apache.kafka.common.serialization.StringSerializer

# Order Events Channel
mp.messaging.outgoing.order-events.connector=smallrye-kafka
mp.messaging.outgoing.order-events.topic=order-events
mp.messaging.outgoing.order-events.value.serializer=org.apache.kafka.common.serialization.StringSerializer

# Customer Service Events Channel
mp.messaging.outgoing.customer-service-events.connector=smallrye-kafka
mp.messaging.outgoing.customer-service-events.topic=customer-service-events
mp.messaging.outgoing.customer-service-events.value.serializer=org.apache.kafka.common.serialization.StringSerializer

# Incoming notification processing
mp.messaging.incoming.notification-processor.connector=smallrye-kafka
mp.messaging.incoming.notification-processor.topic=notification-events
mp.messaging.incoming.notification-processor.value.deserializer=org.apache.kafka.common.serialization.StringDeserializer
mp.messaging.incoming.notification-processor.group.id=notification-processor-group

# Enable Kafka Dev Services for development
quarkus.kafka.devservices.enabled=false
```

### 4. Update NotificationType Enum
**File**: `autowarehouse-backend/src/main/java/com/autowarehouse/entity/NotificationType.java`

Add missing notification types:
```java
// Add these new types
ORDER_REFUNDED("order_refunded"),
CUSTOMER_SERVICE_MESSAGE("customer_service_message"),
ADMIN_CUSTOMER_SERVICE_MESSAGE("admin_customer_service_message");

// Update getDisplayName() method to include new types
case ORDER_REFUNDED: return "Order Refunded";
case CUSTOMER_SERVICE_MESSAGE: return "Customer Service Message";
case ADMIN_CUSTOMER_SERVICE_MESSAGE: return "Admin Customer Service";

// Update getDefaultIcon() method
case ORDER_REFUNDED: return "fas fa-undo";
case CUSTOMER_SERVICE_MESSAGE: return "fas fa-comments";
case ADMIN_CUSTOMER_SERVICE_MESSAGE: return "fas fa-headset";
```

### 5. Create Kafka Event DTOs
**File**: `autowarehouse-backend/src/main/java/com/autowarehouse/dto/NotificationEvent.java`

```java
package com.autowarehouse.dto;

import com.autowarehouse.entity.NotificationType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationEvent {
    @JsonProperty("eventType")
    public String eventType;
    
    @JsonProperty("userId")
    public Long userId;
    
    @JsonProperty("title")
    public String title;
    
    @JsonProperty("message")
    public String message;
    
    @JsonProperty("notificationType")
    public NotificationType notificationType;
    
    @JsonProperty("referenceId")
    public Long referenceId;
    
    @JsonProperty("referenceType")
    public String referenceType;
    
    @JsonProperty("priority")
    public String priority;
    
    @JsonProperty("actionUrl")
    public String actionUrl;
    
    @JsonProperty("timestamp")
    public String timestamp;
    
    // Constructors, getters, setters
    public NotificationEvent() {}
    
    public NotificationEvent(String eventType, Long userId, String title, String message, 
                           NotificationType notificationType, Long referenceId, String referenceType) {
        this.eventType = eventType;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.notificationType = notificationType;
        this.referenceId = referenceId;
        this.referenceType = referenceType;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }
}
```

### 6. Create Kafka Producer Service
**File**: `autowarehouse-backend/src/main/java/com/autowarehouse/kafka/NotificationProducer.java`

```java
package com.autowarehouse.kafka;

import com.autowarehouse.dto.NotificationEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

@ApplicationScoped
public class NotificationProducer {
    
    private static final Logger LOG = Logger.getLogger(NotificationProducer.class);
    
    @Inject
    @Channel("notification-events")
    Emitter<Record<String, String>> notificationEmitter;
    
    @Inject
    ObjectMapper objectMapper;
    
    public void sendNotificationEvent(NotificationEvent event) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            String key = event.eventType + "_" + event.userId;
            
            notificationEmitter.send(Record.of(key, eventJson));
            LOG.infof("Sent notification event: %s for user %d", event.eventType, event.userId);
            
        } catch (Exception e) {
            LOG.errorf("Failed to send notification event: %s", e.getMessage());
        }
    }
    
    public void sendOrderEvent(String eventType, Long orderId, Long userId, String details) {
        NotificationEvent event = new NotificationEvent();
        event.eventType = eventType;
        event.userId = userId;
        event.referenceId = orderId;
        event.referenceType = "ORDER";
        event.title = getOrderEventTitle(eventType);
        event.message = details;
        event.notificationType = getOrderNotificationType(eventType);
        
        sendNotificationEvent(event);
    }
    
    public void sendCustomerServiceEvent(String eventType, Long ticketId, Long userId, String message) {
        NotificationEvent event = new NotificationEvent();
        event.eventType = eventType;
        event.userId = userId;
        event.referenceId = ticketId;
        event.referenceType = "TICKET";
        event.title = getCustomerServiceEventTitle(eventType);
        event.message = message;
        event.notificationType = getCustomerServiceNotificationType(eventType);
        
        sendNotificationEvent(event);
    }
    
    private String getOrderEventTitle(String eventType) {
        switch (eventType) {
            case "orderConfirmed": return "New Order Received";
            case "orderShipped": return "Order Shipped";
            case "orderDelivered": return "Order Delivered";
            case "orderCancelled": return "Order Cancelled";
            case "orderRefunded": return "Order Refunded";
            default: return "Order Update";
        }
    }
    
    private com.autowarehouse.entity.NotificationType getOrderNotificationType(String eventType) {
        switch (eventType) {
            case "orderConfirmed": return com.autowarehouse.entity.NotificationType.ORDER_CONFIRMED;
            case "orderShipped": return com.autowarehouse.entity.NotificationType.ORDER_SHIPPED;
            case "orderDelivered": return com.autowarehouse.entity.NotificationType.ORDER_DELIVERED;
            case "orderCancelled": return com.autowarehouse.entity.NotificationType.ORDER_CANCELLED;
            case "orderRefunded": return com.autowarehouse.entity.NotificationType.ORDER_REFUNDED;
            default: return com.autowarehouse.entity.NotificationType.GENERAL;
        }
    }
    
    private String getCustomerServiceEventTitle(String eventType) {
        switch (eventType) {
            case "customerService": return "New Message from Support";
            case "adminCustomerService": return "New Customer Message";
            default: return "Customer Service Update";
        }
    }
    
    private com.autowarehouse.entity.NotificationType getCustomerServiceNotificationType(String eventType) {
        switch (eventType) {
            case "customerService": return com.autowarehouse.entity.NotificationType.CUSTOMER_SERVICE_MESSAGE;
            case "adminCustomerService": return com.autowarehouse.entity.NotificationType.ADMIN_CUSTOMER_SERVICE_MESSAGE;
            default: return com.autowarehouse.entity.NotificationType.GENERAL;
        }
    }
}
```

### 7. Create Kafka Consumer Service
**File**: `autowarehouse-backend/src/main/java/com/autowarehouse/kafka/NotificationConsumer.java`

```java
package com.autowarehouse.kafka;

import com.autowarehouse.dto.NotificationEvent;
import com.autowarehouse.entity.User;
import com.autowarehouse.service.NotificationService;
import com.autowarehouse.service.WebSocketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class NotificationConsumer {
    
    private static final Logger LOG = Logger.getLogger(NotificationConsumer.class);
    
    @Inject
    NotificationService notificationService;
    
    @Inject
    WebSocketService webSocketService;
    
    @Inject
    ObjectMapper objectMapper;
    
    @Incoming("notification-processor")
    public void processNotification(Record<String, String> record) {
        try {
            String eventJson = record.value();
            NotificationEvent event = objectMapper.readValue(eventJson, NotificationEvent.class);
            
            LOG.infof("Processing notification event: %s for user %d", event.eventType, event.userId);
            
            // Find the user
            User user = User.findById(event.userId);
            if (user == null) {
                LOG.errorf("User not found: %d", event.userId);
                return;
            }
            
            // Create database notification
            notificationService.createNotification(
                user, 
                event.title, 
                event.message, 
                event.notificationType, 
                event.referenceId, 
                event.referenceType
            );
            
            // Send real-time notification via WebSocket
            if (webSocketService != null) {
                webSocketService.sendNotification(event.userId, event.message);
            }
            
            LOG.infof("Successfully processed notification for user %d", event.userId);
            
        } catch (Exception e) {
            LOG.errorf("Failed to process notification: %s", e.getMessage());
        }
    }
}
```

### 8. Update OrderService to Send Kafka Events
**File**: `autowarehouse-backend/src/main/java/com/autowarehouse/service/OrderService.java`

Add Kafka producer injection and update methods:

```java
@Inject
NotificationProducer notificationProducer;

// Update existing methods to send Kafka events:

// In createOrderFromCartWithDetails method, replace:
// notificationService.notifyOrderCreated(user, order);
// With:
notificationProducer.sendOrderEvent("orderConfirmed", order.id, user.id, 
    "New order #" + order.orderNumber + " has been placed.");

// In updateOrderStatus method, add Kafka events:
switch (newStatus) {
    case CONFIRMED:
        notificationProducer.sendOrderEvent("orderConfirmed", order.id, order.user.id,
            "Your order #" + order.orderNumber + " has been confirmed.");
        break;
    case SHIPPED:
        notificationProducer.sendOrderEvent("orderShipped", order.id, order.user.id,
            "Your order #" + order.orderNumber + " has been shipped.");
        break;
    case DELIVERED:
        notificationProducer.sendOrderEvent("orderDelivered", order.id, order.user.id,
            "Your order #" + order.orderNumber + " has been delivered.");
        break;
    case CANCELLED:
        notificationProducer.sendOrderEvent("orderCancelled", order.id, order.user.id,
            "Your order #" + order.orderNumber + " has been cancelled.");
        break;
}

// In processRefundFromPending method, add:
notificationProducer.sendOrderEvent("orderRefunded", order.id, order.user.id,
    "Your refund for order #" + order.orderNumber + " has been processed.");
```

### 9. Update ChatService to Send Kafka Events
**File**: `autowarehouse-backend/src/main/java/com/autowarehouse/service/ChatService.java`

Add Kafka producer injection and update sendMessage method:

```java
@Inject
NotificationProducer notificationProducer;

// In sendMessage method, add after message creation:
// Determine who should receive the notification
if (senderType == SenderType.CUSTOMER) {
    // Customer sent message, notify admins
    List<User> admins = User.findAdmins();
    for (User admin : admins) {
        notificationProducer.sendCustomerServiceEvent("adminCustomerService", 
            request.ticketId, admin.id, 
            "New message from " + sender.getFullName() + ": " + sanitizedMessage.substring(0, Math.min(100, sanitizedMessage.length())));
    }
} else {
    // Admin sent message, notify customer
    notificationProducer.sendCustomerServiceEvent("customerService", 
        request.ticketId, ticket.customerId, 
        "New message from support: " + sanitizedMessage.substring(0, Math.min(100, sanitizedMessage.length())));
}
```

### 10. Create Notification REST API
**File**: `autowarehouse-backend/src/main/java/com/autowarehouse/resource/NotificationResource.java`

```java
package com.autowarehouse.resource;

import com.autowarehouse.entity.Notification;
import com.autowarehouse.entity.NotificationType;
import com.autowarehouse.entity.User;
import com.autowarehouse.service.NotificationService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path("/api/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    NotificationService notificationService;

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    public Response getUserNotifications(@Context SecurityContext ctx) {
        try {
            Long userId = Long.parseLong(jwt.getSubject());
            User user = User.findById(userId);
            
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found").build();
            }

            List<Notification> notifications = Notification.findByUser(user);
            return Response.ok(notifications).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error fetching notifications: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/unread")
    @RolesAllowed({"USER", "ADMIN"})
    public Response getUnreadNotifications(@Context SecurityContext ctx) {
        try {
            Long userId = Long.parseLong(jwt.getSubject());
            User user = User.findById(userId);
            
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found").build();
            }

            List<Notification> notifications = Notification.findUnreadByUser(user);
            return Response.ok(notifications).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error fetching unread notifications: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/count/unread")
    @RolesAllowed({"USER", "ADMIN"})
    public Response getUnreadCount(@Context SecurityContext ctx) {
        try {
            Long userId = Long.parseLong(jwt.getSubject());
            User user = User.findById(userId);
            
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found").build();
            }

            long count = Notification.countUnreadByUser(user);
            return Response.ok(new UnreadCountResponse(count)).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error fetching unread count: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}/read")
    @RolesAllowed({"USER", "ADMIN"})
    @Transactional
    public Response markAsRead(@PathParam("id") Long notificationId, @Context SecurityContext ctx) {
        try {
            Long userId = Long.parseLong(jwt.getSubject());
            User user = User.findById(userId);
            
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found").build();
            }

            Notification notification = Notification.findById(notificationId);
            if (notification == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("Notification not found").build();
            }

            if (!notification.user.id.equals(userId)) {
                return Response.status(Response.Status.FORBIDDEN)
                    .entity("Not authorized to modify this notification").build();
            }

            notification.markAsRead();
            notification.persist();

            return Response.ok(notification).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error marking notification as read: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/read-all")
    @RolesAllowed({"USER", "ADMIN"})
    @Transactional
    public Response markAllAsRead(@Context SecurityContext ctx) {
        try {
            Long userId = Long.parseLong(jwt.getSubject());
            User user = User.findById(userId);
            
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found").build();
            }

            List<Notification> notifications = Notification.findUnreadByUser(user);
            for (Notification notification : notifications) {
                notification.markAsRead();
                notification.persist();
            }

            return Response.ok(new MessageResponse("All notifications marked as read")).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error marking all notifications as read: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"USER", "ADMIN"})
    @Transactional
    public Response deleteNotification(@PathParam("id") Long notificationId, @Context SecurityContext ctx) {
        try {
            Long userId = Long.parseLong(jwt.getSubject());
            User user = User.findById(userId);
            
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found").build();
            }

            Notification notification = Notification.findById(notificationId);
            if (notification == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("Notification not found").build();
            }

            if (!notification.user.id.equals(userId)) {
                return Response.status(Response.Status.FORBIDDEN)
                    .entity("Not authorized to delete this notification").build();
            }

            notification.delete();

            return Response.ok(new MessageResponse("Notification deleted")).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error deleting notification: " + e.getMessage()).build();
        }
    }

    // Response DTOs
    public static class UnreadCountResponse {
        public long count;
        
        public UnreadCountResponse(long count) {
            this.count = count;
        }
    }

    public static class MessageResponse {
        public String message;
        
        public MessageResponse(String message) {
            this.message = message;
        }
    }
}
```

## Frontend Implementation Tasks

### 11. Create Notification Service
**File**: `autowarehouse-frontend/src/services/notificationService.ts`

```typescript
import axios from 'axios'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8081'

export interface Notification {
  id: number
  title: string
  message: string
  type: string
  isRead: boolean
  referenceId?: number
  referenceType?: string
  actionUrl?: string
  iconClass?: string
  priority: string
  expiresAt?: string
  createdAt: string
  user: {
    id: number
    email: string
  }
}

export interface NotificationResponse {
  notifications: Notification[]
  totalElements: number
  totalPages: number
  currentPage: number
  size: number
}

class NotificationService {
  private getAuthHeaders() {
    const token = localStorage.getItem('token')
    return {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    }
  }

  async getNotifications(): Promise<Notification[]> {
    try {
      const response = await axios.get(`${API_BASE_URL}/api/notifications`, {
        headers: this.getAuthHeaders()
      })
      return response.data
    } catch (error) {
      console.error('Error fetching notifications:', error)
      throw error
    }
  }

  async getUnreadNotifications(): Promise<Notification[]> {
    try {
      const response = await axios.get(`${API_BASE_URL}/api/notifications/unread`, {
        headers: this.getAuthHeaders()
      })
      return response.data
    } catch (error) {
      console.error('Error fetching unread notifications:', error)
      throw error
    }
  }

  async getUnreadCount(): Promise<number> {
    try {
      const response = await axios.get(`${API_BASE_URL}/api/notifications/count/unread`, {
        headers: this.getAuthHeaders()
      })
      return response.data.count
    } catch (error) {
      console.error('Error fetching unread count:', error)
      throw error
    }
  }

  async markAsRead(notificationId: number): Promise<Notification> {
    try {
      const response = await axios.put(`${API_BASE_URL}/api/notifications/${notificationId}/read`, {}, {
        headers: this.getAuthHeaders()
      })
      return response.data
    } catch (error) {
      console.error('Error marking notification as read:', error)
      throw error
    }
  }

  async markAllAsRead(): Promise<void> {
    try {
      await axios.put(`${API_BASE_URL}/api/notifications/read-all`, {}, {
        headers: this.getAuthHeaders()
      })
    } catch (error) {
      console.error('Error marking all notifications as read:', error)
      throw error
    }
  }

  async deleteNotification(notificationId: number): Promise<void> {
    try {
      await axios.delete(`${API_BASE_URL}/api/notifications/${notificationId}`, {
        headers: this.getAuthHeaders()
      })
    } catch (error) {
      console.error('Error deleting notification:', error)
      throw error
    }
  }
}

export default new NotificationService()
```

### 12. Create WebSocket Service for Real-time Notifications
**File**: `autowarehouse-frontend/src/services/websocketService.ts`

```typescript
import { ref, reactive } from 'vue'

interface WebSocketMessage {
  type: string
  data: any
  timestamp: string
}

class WebSocketService {
  private ws: WebSocket | null = null
  private reconnectAttempts = 0
  private maxReconnectAttempts = 5
  private reconnectInterval = 5000
  
  public isConnected = ref(false)
  public notifications = reactive<any[]>([])
  
  connect(userId: number, token: string) {
    const wsUrl = `ws://localhost:8081/ws/notifications?userId=${userId}&token=${token}`
    
    try {
      this.ws = new WebSocket(wsUrl)
      
      this.ws.onopen = () => {
        console.log('WebSocket connected')
        this.isConnected.value = true
        this.reconnectAttempts = 0
      }
      
      this.ws.onmessage = (event) => {
        try {
          const message: WebSocketMessage = JSON.parse(event.data)
          this.handleMessage(message)
        } catch (error) {
          console.error('Error parsing WebSocket message:', error)
        }
      }
      
      this.ws.onclose = () => {
        console.log('WebSocket disconnected')
        this.isConnected.value = false
        this.attemptReconnect(userId, token)
      }
      
      this.ws.onerror = (error) => {
        console.error('WebSocket error:', error)
      }
      
    } catch (error) {
      console.error('Error connecting to WebSocket:', error)
    }
  }
  
  private handleMessage(message: WebSocketMessage) {
    switch (message.type) {
      case 'notification':
        this.notifications.push(message.data)
        this.showNotificationToast(message.data)
        break
      case 'ping':
        this.sendPong()
        break
      default:
        console.log('Unknown message type:', message.type)
    }
  }
  
  private showNotificationToast(notification: any) {
    // Show browser notification if permission granted
    if (Notification.permission === 'granted') {
      new Notification(notification.title, {
        body: notification.message,
        icon: '/favicon.ico'
      })
    }
    
    // Emit custom event for UI components
    window.dispatchEvent(new CustomEvent('newNotification', { 
      detail: notification 
    }))
  }
  
  private attemptReconnect(userId: number, token: string) {
    if (this.reconnectAttempts < this.maxReconnectAttempts) {
      this.reconnectAttempts++
      console.log(`Attempting to reconnect... (${this.reconnectAttempts}/${this.maxReconnectAttempts})`)
      
      setTimeout(() => {
        this.connect(userId, token)
      }, this.reconnectInterval)
    } else {
      console.error('Max reconnection attempts reached')
    }
  }
  
  private sendPong() {
    if (this.ws && this.ws.readyState === WebSocket.OPEN) {
      this.ws.send(JSON.stringify({ type: 'pong' }))
    }
  }
  
  disconnect() {
    if (this.ws) {
      this.ws.close()
      this.ws = null
      this.isConnected.value = false
    }
  }
  
  // Request notification permission
  async requestNotificationPermission(): Promise<boolean> {
    if (!('Notification' in window)) {
      console.log('This browser does not support notifications')
      return false
    }
    
    if (Notification.permission === 'granted') {
      return true
    }
    
    if (Notification.permission !== 'denied') {
      const permission = await Notification.requestPermission()
      return permission === 'granted'
    }
    
    return false
  }
}

export default new WebSocketService()
```

### 13. Update Frontend Notification Views

**File**: `autowarehouse-frontend/src/views/UserNotificationsView.vue`

Replace the mock data with real API integration:

```vue
<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import UserNavbar from '../components/UserNavbar.vue'
import Footer from '../components/Footer.vue'
import notificationService, { type Notification } from '../services/notificationService'
import websocketService from '../services/websocketService'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const authStore = useAuthStore()

// State
const activeFilter = ref('all')
const currentPage = ref(1)
const itemsPerPage = 5
const loading = ref(false)
const error = ref('')

// Notifications data
const notifications = ref<Notification[]>([])

// Computed properties
const filteredNotifications = computed(() => {
  let filtered = notifications.value
  
  switch (activeFilter.value) {
    case 'customerService':
      filtered = notifications.value.filter(n => !n.isRead)
      break
    case 'order':
      filtered = notifications.value.filter(n => n.priority === 'HIGH' || n.priority === 'URGENT')
      break
    case 'read':
      filtered = notifications.value.filter(n => n.isRead)
      break
  }
  
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const unreadCount = computed(() => notifications.value.filter(n => !n.isRead).length)
const importantCount = computed(() => notifications.value.filter(n => n.priority === 'HIGH' || n.priority === 'URGENT').length)
const totalPages = computed(() => Math.ceil(notifications.value.length / itemsPerPage))

// Methods
const loadNotifications = async () => {
  try {
    loading.value = true
    error.value = ''
    notifications.value = await notificationService.getNotifications()
  } catch (err) {
    error.value = 'Failed to load notifications'
    console.error('Error loading notifications:', err)
  } finally {
    loading.value = false
  }
}

const markAsRead = async (id: number) => {
  try {
    await notificationService.markAsRead(id)
    const notification = notifications.value.find(n => n.id === id)
    if (notification) {
      notification.isRead = true
    }
  } catch (err) {
    console.error('Error marking notification as read:', err)
  }
}

const markAllAsRead = async () => {
  try {
    await notificationService.markAllAsRead()
    notifications.value.forEach(notification => {
      notification.isRead = true
    })
  } catch (err) {
    console.error('Error marking all notifications as read:', err)
  }
}

const deleteAll = async () => {
  if (confirm('Are you sure you want to delete all notifications?')) {
    try {
      // Delete all notifications one by one (API doesn't have bulk delete)
      for (const notification of notifications.value) {
        await notificationService.deleteNotification(notification.id)
      }
      notifications.value = []
    } catch (err) {
      console.error('Error deleting notifications:', err)
    }
  }
}

const handleAction = (action: any, notificationId: number) => {
  console.log('Action clicked:', action.label, 'for notification:', notificationId)
  // Handle specific actions based on notification type
}

// Real-time notification handling
const handleNewNotification = (event: CustomEvent) => {
  const newNotification = event.detail
  notifications.value.unshift(newNotification)
}

onMounted(async () => {
  await loadNotifications()
  
  // Connect to WebSocket for real-time notifications
  if (authStore.user) {
    websocketService.connect(authStore.user.id, authStore.token)
    await websocketService.requestNotificationPermission()
  }
  
  // Listen for new notifications
  window.addEventListener('newNotification', handleNewNotification)
})

onUnmounted(() => {
  websocketService.disconnect()
  window.removeEventListener('newNotification', handleNewNotification)
})

// Rest of the methods remain the same...
</script>
```

### 14. Update Admin Notification View

**File**: `autowarehouse-frontend/src/views/admin/AdminNotificationView.vue`

Similar updates for admin view with real API integration.

### 15. Create Docker Compose for Local Development

**File**: `docker-compose.kafka.yml`

```yaml
version: '3.8'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.4.0
    hostname: zookeeper
    container_name: autowarehouse-zookeeper
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    networks:
      - autowarehouse-network

  kafka:
    image: confluentinc/cp-kafka:7.4.0
    hostname: kafka
    container_name: autowarehouse-kafka
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
      - "9997:9997"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
      KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS: 0
      KAFKA_JMX_PORT: 9997
      KAFKA_JMX_HOSTNAME: localhost
    networks:
      - autowarehouse-network

  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    container_name: autowarehouse-kafka-ui
    depends_on:
      - kafka
    ports:
      - "8080:8080"
    environment:
      KAFKA_CLUSTERS_0_NAME: local
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka:29092
      KAFKA_CLUSTERS_0_ZOOKEEPER: zookeeper:2181
    networks:
      - autowarehouse-network

networks:
  autowarehouse-network:
    driver: bridge
```

### 16. Create Kafka Setup Scripts

**File**: `scripts/setup-kafka.sh`

```bash
#!/bin/bash

echo "Setting up Kafka for Autowarehouse..."

# Start Kafka services
echo "Starting Kafka services..."
docker-compose -f docker-compose.kafka.yml up -d

# Wait for Kafka to be ready
echo "Waiting for Kafka to be ready..."
sleep 30

# Create topics
echo "Creating Kafka topics..."
docker exec autowarehouse-kafka kafka-topics --create --topic notification-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
docker exec autowarehouse-kafka kafka-topics --create --topic order-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
docker exec autowarehouse-kafka kafka-topics --create --topic customer-service-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

# List topics to verify
echo "Verifying topics..."
docker exec autowarehouse-kafka kafka-topics --list --bootstrap-server localhost:9092

echo "Kafka setup complete!"
echo "Kafka UI available at: http://localhost:8080"
```

**File**: `scripts/stop-kafka.sh`

```bash
#!/bin/bash

echo "Stopping Kafka services..."
docker-compose -f docker-compose.kafka.yml down

echo "Kafka services stopped."
```

## Testing and Validation Tasks

### 17. Create Test Scripts

**File**: `scripts/test-notifications.sh`

```bash
#!/bin/bash

echo "Testing Kafka notification system..."

# Test order notification
echo "Testing order notification..."
docker exec autowarehouse-kafka kafka-console-producer --topic order-events --bootstrap-server localhost:9092 << EOF
{"eventType":"orderConfirmed","userId":1,"title":"Test Order","message":"Test order notification","notificationType":"ORDER_CONFIRMED","referenceId":123,"referenceType":"ORDER","timestamp":"$(date -Iseconds)"}
EOF

# Test customer service notification
echo "Testing customer service notification..."
docker exec autowarehouse-kafka kafka-console-producer --topic customer-service-events --bootstrap-server localhost:9092 << EOF
{"eventType":"customerService","userId":1,"title":"Test Message","message":"Test customer service notification","notificationType":"CUSTOMER_SERVICE_MESSAGE","referenceId":456,"referenceType":"TICKET","timestamp":"$(date -Iseconds)"}
EOF

echo "Test messages sent. Check the application logs and UI for notifications."
```

## Implementation Checklist

### Backend Tasks:
- [ ] 1. Set up Kafka infrastructure (Docker Compose)
- [ ] 2. Create Kafka topics
- [ ] 3. Update application.properties with Kafka config
- [ ] 4. Update NotificationType enum with new types
- [ ] 5. Create NotificationEvent DTO
- [ ] 6. Create NotificationProducer service
- [ ] 7. Create NotificationConsumer service
- [ ] 8. Update OrderService with Kafka events
- [ ] 9. Update ChatService with Kafka events
- [ ] 10. Create NotificationResource REST API
- [ ] 11. Add User.findAdmins() method if missing

### Frontend Tasks:
- [ ] 12. Create notification service
- [ ] 13. Create WebSocket service
- [ ] 14. Update UserNotificationsView with real API
- [ ] 15. Update AdminNotificationView with real API
- [ ] 16. Add notification permission handling
- [ ] 17. Add real-time notification toasts
- [ ] 18. Update navigation bars with notification badges

### Infrastructure Tasks:
- [ ] 19. Create Docker Compose file
- [ ] 20. Create setup scripts
- [ ] 21. Create test scripts
- [ ] 22. Update main docker-compose.yml to include Kafka

### Testing Tasks:
- [ ] 23. Test order status change notifications
- [ ] 24. Test customer service chat notifications
- [ ] 25. Test real-time WebSocket notifications
- [ ] 26. Test notification persistence
- [ ] 27. Test notification API endpoints
- [ ] 28. Test Kafka producer/consumer functionality

## Running the System

### 1. Start Kafka Infrastructure:
```bash
chmod +x scripts/setup-kafka.sh
./scripts/setup-kafka.sh
```

### 2. Start Backend with Kafka Enabled:
```bash
cd autowarehouse-backend
# Uncomment Kafka configuration in application.properties
mvn quarkus:dev
```

### 3. Start Frontend:
```bash
cd autowarehouse-frontend
npm run dev
```

### 4. Test the System:
```bash
chmod +x scripts/test-notifications.sh
./scripts/test-notifications.sh
```

## Monitoring and Debugging

- **Kafka UI**: http://localhost:8080
- **Backend Logs**: Check Quarkus dev console
- **Frontend Console**: Check browser developer tools
- **Database**: Check notifications table for persistence

## Notes

1. **Security**: Ensure WebSocket connections are properly authenticated
2. **Performance**: Monitor Kafka topic partitions and consumer lag
3. **Reliability**: Implement proper error handling and retry mechanisms
4. **Scalability**: Consider message ordering and duplicate handling
5. **Testing**: Create comprehensive integration tests for the notification flow

This implementation provides a complete Kafka-based notification system that handles all the required business scenarios with real-time delivery and proper persistence.
