# 🔧 Backend Implementation - Autowarehouse System

## Overview
Comprehensive documentation of the Autowarehouse backend implementation using Quarkus framework with Java 17. This document covers the complete backend architecture, implementation details, and current status as of January 8, 2025.

## 🎯 Implementation Status: 95% Complete

### ✅ Fully Implemented
- **Project Infrastructure**: 100% Complete
- **Database Schema**: 100% Complete  
- **Entity Model**: 100% Complete
- **Security Foundation**: 100% Complete
- **Service Layer**: 100% Complete
- **REST API Layer**: 100% Complete
- **External Integrations**: 90% Complete

### 🟡 Partially Implemented
- **Real-time Features**: 80% Complete
- **Testing**: 0% Complete
- **Production Deployment**: 20% Complete

---

## 🏗️ Project Infrastructure

### Framework & Dependencies
```xml
<!-- Core Framework -->
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-arc</artifactId>
    <version>3.6.4</version>
</dependency>

<!-- REST API -->
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-resteasy-reactive</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-resteasy-reactive-jackson</artifactId>
</dependency>

<!-- Database -->
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-hibernate-orm-panache</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-jdbc-postgresql</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-flyway</artifactId>
</dependency>

<!-- Security -->
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-smallrye-jwt</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-security-jpa</artifactId>
</dependency>

<!-- Real-time & Messaging -->
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-websockets</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-smallrye-reactive-messaging-kafka</artifactId>
</dependency>

<!-- Caching & External Services -->
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-redis-client</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-mailer</artifactId>
</dependency>
```

### Application Configuration
```properties
# Application Configuration
quarkus.application.name=autowarehouse-backend
quarkus.application.version=1.0.0

# HTTP Configuration
quarkus.http.port=8080
quarkus.http.cors=true
quarkus.http.cors.origins=http://localhost:3000,http://localhost:5173

# Database Configuration
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=postgres
quarkus.datasource.password=admin
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5434/autowarehouse

# Security Configuration
quarkus.security.jpa.enabled=true
mp.jwt.verify.publickey.location=META-INF/resources/publicKey.pem
smallrye.jwt.sign.key.location=META-INF/resources/privateKey.pem

# External Services
quarkus.redis.hosts=redis://localhost:6379
kafka.bootstrap.servers=localhost:9092
```

---

## 📊 Database Schema Implementation

### Core Tables Structure

#### Users Table
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20),
    role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER',
    is_active BOOLEAN DEFAULT TRUE,
    is_email_verified BOOLEAN DEFAULT FALSE,
    google_id VARCHAR(255),
    profile_image_url TEXT,
    email_verification_token VARCHAR(255),
    password_reset_token VARCHAR(255),
    password_reset_token_expiry TIMESTAMP,
    last_login_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### Products Table
```sql
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    short_description VARCHAR(500),
    price DECIMAL(12,2) NOT NULL,
    original_price DECIMAL(12,2),
    stock_quantity INTEGER NOT NULL DEFAULT 0,
    sku VARCHAR(100) UNIQUE NOT NULL,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    specifications TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    is_featured BOOLEAN DEFAULT FALSE,
    is_on_sale BOOLEAN DEFAULT FALSE,
    average_rating DECIMAL(3,2) DEFAULT 0.00,
    total_reviews INTEGER DEFAULT 0,
    category_id BIGINT NOT NULL REFERENCES categories(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### Auctions Table
```sql
CREATE TABLE auctions (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES products(id),
    title VARCHAR(200) NOT NULL,
    description TEXT,
    starting_price DECIMAL(12,2) NOT NULL,
    current_price DECIMAL(12,2) NOT NULL,
    buy_now_price DECIMAL(12,2),
    minimum_bid_increment DECIMAL(12,2) NOT NULL DEFAULT 1000.00,
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    winner_id BIGINT REFERENCES users(id),
    total_bids INTEGER DEFAULT 0,
    watchers_count INTEGER DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Performance Optimization
- **Comprehensive Indexing**: All frequently queried columns have indexes
- **Foreign Key Constraints**: Proper referential integrity
- **Query Optimization**: Efficient JPA queries with Panache
- **Connection Pooling**: Optimized database connections

---

## 🏛️ Entity Model Implementation

### User Entity
```java
@Entity
@Table(name = "users")
@UserDefinition
public class User extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true, nullable = false)
    @Username
    @Email
    @NotBlank
    public String email;

    @Column(nullable = false)
    @Password
    @Size(min = 6)
    public String password;

    @Column(name = "first_name", nullable = false)
    @NotBlank
    @Size(max = 50)
    public String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank
    @Size(max = 50)
    public String lastName;

    @Column(nullable = false)
    @Roles
    public String role = "CUSTOMER";

    // Additional fields and relationships...
    
    // Static finder methods
    public static User findByEmail(String email) {
        return find("email", email).firstResult();
    }
    
    public static List<User> findByRole(String role) {
        return find("role", role).list();
    }
}
```

### Product Entity
```java
@Entity
@Table(name = "products")
public class Product extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 200)
    public String name;

    @Column(columnDefinition = "TEXT")
    public String description;

    @Column(nullable = false, precision = 12, scale = 2)
    @DecimalMin("0.00")
    public BigDecimal price;

    @Column(name = "stock_quantity", nullable = false)
    @Min(0)
    public Integer stockQuantity = 0;

    @Column(unique = true, nullable = false)
    @NotBlank
    @Size(max = 100)
    public String sku;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    public Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Review> reviews;

    // Business logic methods
    public boolean isInStock() {
        return stockQuantity > 0;
    }
    
    public boolean isLowStock() {
        return stockQuantity <= 5;
    }
}
```

### Auction Entity
```java
@Entity
@Table(name = "auctions")
public class Auction extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    public Product product;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 200)
    public String title;

    @Column(name = "starting_price", nullable = false, precision = 12, scale = 2)
    @DecimalMin("0.00")
    public BigDecimal startingPrice;

    @Column(name = "current_price", nullable = false, precision = 12, scale = 2)
    public BigDecimal currentPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public AuctionStatus status = AuctionStatus.SCHEDULED;

    @Column(name = "start_time", nullable = false)
    public LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    public LocalDateTime endTime;

    // Business logic methods
    public boolean isLive() {
        return status == AuctionStatus.LIVE && 
               LocalDateTime.now().isAfter(startTime) && 
               LocalDateTime.now().isBefore(endTime);
    }
    
    public boolean canPlaceBid() {
        return isLive() && status == AuctionStatus.LIVE;
    }
}
```

---

## 🔐 Security Implementation

### JWT Authentication
```java
@ApplicationScoped
public class JwtService {
    
    @ConfigProperty(name = "smallrye.jwt.sign.key.location")
    String privateKeyLocation;
    
    @ConfigProperty(name = "smallrye.jwt.new-token.lifespan")
    Long tokenLifespan;

    public String generateToken(User user) {
        return Jwt.issuer("https://autowarehouse.com")
                .upn(user.email)
                .groups(Set.of(user.role))
                .claim("userId", user.id)
                .claim("firstName", user.firstName)
                .claim("lastName", user.lastName)
                .expiresAt(Instant.now().plusSeconds(tokenLifespan))
                .sign();
    }

    public String generateRefreshToken(User user) {
        return Jwt.issuer("https://autowarehouse.com")
                .upn(user.email)
                .groups(Set.of("REFRESH"))
                .claim("userId", user.id)
                .expiresAt(Instant.now().plusSeconds(tokenLifespan * 7)) // 7x longer
                .sign();
    }
}
```

### Password Security
```java
@ApplicationScoped
public class UserService {
    
    @Transactional
    public User createUser(@Valid User user) {
        // Check if email already exists
        if (User.findByEmail(user.email) != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Hash password with BCrypt
        user.password = BcryptUtil.bcryptHash(user.password);
        
        // Generate email verification token
        user.emailVerificationToken = jwtService.generateEmailVerificationToken(user);
        
        user.persist();
        
        // Send verification email
        emailService.sendEmailVerification(user, user.emailVerificationToken);
        
        return user;
    }

    public User authenticate(String email, String password) {
        User user = User.findByEmail(email);
        if (user == null || !user.isActive) {
            return null;
        }

        if (BcryptUtil.matches(password, user.password)) {
            updateLastLogin(user);
            return user;
        }
        
        return null;
    }
}
```

---

## 🛠️ Service Layer Implementation

### User Service
```java
@ApplicationScoped
public class UserService {

    @Inject
    EmailService emailService;

    @Inject
    JwtService jwtService;

    @Transactional
    public User registerCustomer(String email, String password, String firstName, String lastName) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.firstName = firstName;
        user.lastName = lastName;
        user.role = "CUSTOMER";
        
        return createUser(user);
    }

    @Transactional
    public void resetPassword(String email) {
        User user = User.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        String resetToken = jwtService.generatePasswordResetToken(user);
        user.passwordResetToken = resetToken;
        user.passwordResetTokenExpiry = LocalDateTime.now().plusHours(24);
        user.persist();

        emailService.sendPasswordResetEmail(user, resetToken);
    }

    @Transactional
    public void verifyEmail(String token) {
        User user = User.findByEmailVerificationToken(token);
        if (user == null) {
            throw new IllegalArgumentException("Invalid verification token");
        }

        user.isEmailVerified = true;
        user.emailVerificationToken = null;
        user.persist();

        emailService.sendWelcomeEmail(user);
    }
}
```

### Product Service
```java
@ApplicationScoped
public class ProductService {

    public List<Product> findProducts(ProductFilters filters) {
        PanacheQuery<Product> query = Product.find("isActive = true");
        
        if (filters.getCategory() != null) {
            query = query.filter("category.id = ?1", filters.getCategory());
        }
        
        if (filters.getBrand() != null) {
            query = query.filter("brand = ?1", filters.getBrand());
        }
        
        if (filters.getMinPrice() != null) {
            query = query.filter("price >= ?1", filters.getMinPrice());
        }
        
        if (filters.getMaxPrice() != null) {
            query = query.filter("price <= ?1", filters.getMaxPrice());
        }
        
        if (filters.getSearch() != null) {
            query = query.filter("LOWER(name) LIKE LOWER(?1) OR LOWER(description) LIKE LOWER(?1)", 
                               "%" + filters.getSearch() + "%");
        }
        
        return query.page(filters.getPage(), filters.getSize()).list();
    }

    public List<Product> findFeaturedProducts(int limit) {
        return Product.find("isActive = true AND isFeatured = true")
                     .page(0, limit)
                     .list();
    }

    public List<Product> findOnSaleProducts(int limit) {
        return Product.find("isActive = true AND isOnSale = true")
                     .page(0, limit)
                     .list();
    }
}
```

### Auction Service
```java
@ApplicationScoped
public class AuctionService {

    @Inject
    NotificationService notificationService;

    public List<Auction> findLiveAuctions() {
        return Auction.find("status = ?1 AND startTime <= ?2 AND endTime > ?2", 
                          AuctionStatus.LIVE, LocalDateTime.now()).list();
    }

    public List<Auction> findUpcomingAuctions() {
        return Auction.find("status = ?1 AND startTime > ?2", 
                          AuctionStatus.SCHEDULED, LocalDateTime.now()).list();
    }

    public List<Auction> findEndingSoonAuctions() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourLater = now.plusHours(1);
        
        return Auction.find("status = ?1 AND endTime > ?2 AND endTime <= ?3", 
                          AuctionStatus.LIVE, now, oneHourLater).list();
    }

    @Transactional
    public Bid placeBid(Long auctionId, BidRequest bidRequest) {
        Auction auction = Auction.findById(auctionId);
        if (auction == null || !auction.canPlaceBid()) {
            throw new IllegalArgumentException("Cannot place bid on this auction");
        }

        if (bidRequest.getBidAmount().compareTo(auction.currentPrice.add(auction.minimumBidIncrement)) < 0) {
            throw new IllegalArgumentException("Bid amount too low");
        }

        Bid bid = new Bid();
        bid.auction = auction;
        bid.user = User.findById(bidRequest.getUserId());
        bid.amount = bidRequest.getBidAmount();
        bid.isWinning = true;
        bid.persist();

        // Update auction current price
        auction.currentPrice = bidRequest.getBidAmount();
        auction.totalBids++;
        auction.persist();

        // Mark previous bids as not winning
        Bid.update("isWinning = false WHERE auction.id = ?1 AND id != ?2", 
                  auctionId, bid.id);

        // Send notifications
        notificationService.notifyAuctionBid(auction, bid);

        return bid;
    }
}
```

---

## 🌐 REST API Implementation

### Authentication Resource
```java
@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication", description = "Authentication and user management endpoints")
public class AuthResource {

    @Inject
    UserService userService;

    @Inject
    JwtService jwtService;

    @POST
    @Path("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT tokens")
    public Response login(@Valid LoginRequest request) {
        try {
            User user = userService.authenticate(request.email, request.password);
            
            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new AuthResponse("Invalid email or password", null, null, null, null, null, null, null, false))
                    .build();
            }

            if (!user.isEmailVerified) {
                return Response.status(Response.Status.FORBIDDEN)
                    .entity(new AuthResponse("Please verify your email before logging in", null, null, null, null, null, null, null, false))
                    .build();
            }

            String accessToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            return Response.ok(new AuthResponse(
                "Login successful",
                accessToken,
                refreshToken,
                user.id,
                user.email,
                user.firstName,
                user.lastName,
                user.role,
                user.isEmailVerified
            )).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new AuthResponse("Login failed. Please try again.", null, null, null, null, null, null, null, false))
                .build();
        }
    }

    @POST
    @Path("/register")
    @Operation(summary = "Register new user", description = "Register a new user account and send email verification")
    public Response register(@Valid RegisterRequest request) {
        try {
            User user = userService.registerCustomer(
                request.email,
                request.password,
                request.firstName,
                request.lastName
            );

            return Response.status(Response.Status.CREATED)
                .entity(new AuthResponse(
                    "Registration successful. Please check your email to verify your account.",
                    null,
                    null,
                    user.id,
                    user.email,
                    user.firstName,
                    user.lastName,
                    user.role,
                    false
                ))
                .build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new AuthResponse(e.getMessage(), null, null, null, null, null, null, null, false))
                .build();
        }
    }
}
```

### Product Resource
```java
@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    @Operation(summary = "Get products", description = "Get products with filtering and pagination")
    public Response getProducts(@BeanParam ProductFilters filters) {
        try {
            List<Product> products = productService.findProducts(filters);
            return Response.ok(products).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("message", "Failed to fetch products"))
                .build();
        }
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get product by ID", description = "Get detailed product information")
    public Response getProduct(@PathParam("id") Long id) {
        try {
            Product product = Product.findById(id);
            if (product == null || !product.isActive) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("message", "Product not found"))
                    .build();
            }
            return Response.ok(product).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("message", "Failed to fetch product"))
                .build();
        }
    }

    @GET
    @Path("/featured/popular")
    @Operation(summary = "Get popular products", description = "Get most popular products")
    public Response getPopularProducts(@QueryParam("limit") @DefaultValue("10") int limit) {
        try {
            List<Product> products = productService.findPopularProducts(limit);
            return Response.ok(products).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("message", "Failed to fetch popular products"))
                .build();
        }
    }
}
```

### Auction Resource
```java
@Path("/api/auctions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuctionResource {

    @Inject
    AuctionService auctionService;

    @GET
    @Path("/live")
    @Operation(summary = "Get live auctions", description = "Get currently active auctions")
    public Response getLiveAuctions() {
        try {
            List<Auction> auctions = auctionService.findLiveAuctions();
            return Response.ok(auctions).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("message", "Failed to fetch live auctions"))
                .build();
        }
    }

    @POST
    @Path("/{id}/bids")
    @Operation(summary = "Place bid", description = "Place a bid on an auction")
    public Response placeBid(@PathParam("id") Long auctionId, @Valid BidRequest bidRequest) {
        try {
            Bid bid = auctionService.placeBid(auctionId, bidRequest);
            return Response.status(Response.Status.CREATED).entity(bid).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("message", e.getMessage()))
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("message", "Failed to place bid"))
                .build();
        }
    }
}
```

---

## 🔄 Real-time Features Implementation

### WebSocket Configuration
```java
@ServerEndpoint("/websocket/auctions/{auctionId}")
@ApplicationScoped
public class AuctionWebSocket {

    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("auctionId") String auctionId) {
        sessions.put(session.getId(), session);
        // Join auction room
        session.getUserProperties().put("auctionId", auctionId);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // Handle incoming WebSocket messages
        // Parse bid updates, auction status changes, etc.
    }

    public void broadcastAuctionUpdate(Long auctionId, Object update) {
        String message = Json.encode(update);
        sessions.values().stream()
            .filter(session -> auctionId.toString().equals(session.getUserProperties().get("auctionId")))
            .forEach(session -> {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    // Handle error
                }
            });
    }
}
```

### Kafka Event Processing
```java
@ApplicationScoped
public class AuctionEventProcessor {

    @Inject
    AuctionWebSocket auctionWebSocket;

    @Incoming("auction-events")
    public void processAuctionEvent(String event) {
        // Parse auction event
        AuctionEvent auctionEvent = Json.decodeValue(event, AuctionEvent.class);
        
        // Broadcast to WebSocket clients
        auctionWebSocket.broadcastAuctionUpdate(auctionEvent.getAuctionId(), auctionEvent);
        
        // Process business logic based on event type
        switch (auctionEvent.getType()) {
            case BID_PLACED:
                handleBidPlaced(auctionEvent);
                break;
            case AUCTION_ENDED:
                handleAuctionEnded(auctionEvent);
                break;
            case AUCTION_EXTENDED:
                handleAuctionExtended(auctionEvent);
                break;
        }
    }

    @Outgoing("auction-events")
    public Multi<String> generateAuctionEvents() {
        // Generate auction events (e.g., auction ending notifications)
        return Multi.createFrom().ticks().every(Duration.ofSeconds(30))
            .map(tick -> checkEndingAuctions())
            .filter(Objects::nonNull);
    }
}
```

---

## 📧 External Service Integrations

### Email Service
```java
@ApplicationScoped
public class EmailService {

    @Inject
    Mailer mailer;

    @ConfigProperty(name = "app.frontend.url")
    String frontendUrl;

    public void sendEmailVerification(User user, String token) {
        String verificationUrl = frontendUrl + "/verify-email?token=" + token;
        
        Mail mail = Mail.withText(user.email, 
            "Verify Your Autowarehouse Account",
            buildEmailVerificationText(user.firstName, verificationUrl))
            .setFrom("noreply@autowarehouse.com");
            
        mailer.send(mail);
    }

    public void sendPasswordResetEmail(User user, String token) {
        String resetUrl = frontendUrl + "/reset-password?token=" + token;
        
        Mail mail = Mail.withText(user.email,
            "Reset Your Autowarehouse Password",
            buildPasswordResetText(user.firstName, resetUrl))
            .setFrom("noreply@autowarehouse.com");
            
        mailer.send(mail);
    }

    public void sendWelcomeEmail(User user) {
        Mail mail = Mail.withText(user.email,
            "Welcome to Autowarehouse!",
            buildWelcomeText(user.firstName))
            .setFrom("noreply@autowarehouse.com");
            
        mailer.send(mail);
    }
}
```

### Firebase Integration
```java
@ApplicationScoped
public class FirebaseService {

    private FirebaseApp firebaseApp;

    @PostConstruct
    public void init() {
        try {
            FileInputStream serviceAccount = new FileInputStream("firebase-service-account.json");
            
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("autowarehouse-app.appspot.com")
                .build();

            firebaseApp = FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Firebase", e);
        }
    }

    public void sendPushNotification(String token, String title, String body) {
        Message message = Message.builder()
            .setToken(token)
            .setNotification(Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build())
            .build();

        try {
            FirebaseMessaging.getInstance(firebaseApp).send(message);
        } catch (FirebaseMessagingException e) {
            // Handle error
        }
    }
}
```

---

## 🧪 Testing Strategy

### Unit Testing Structure
```java
@QuarkusTest
class UserServiceTest {

    @Inject
    UserService userService;

    @Test
    @Transactional
    void testRegisterCustomer() {
        // Given
        String email = "test@example.com";
        String password = "password123";
        String firstName = "John";
        String lastName = "Doe";

        // When
        User user = userService.registerCustomer(email, password, firstName, lastName);

        // Then
        assertThat(user).isNotNull();
        assertThat(user.email).isEqualTo(email);
        assertThat(user.firstName).isEqualTo(firstName);
        assertThat(user.role).isEqualTo("CUSTOMER");
        assertThat(user.isEmailVerified).isFalse();
    }

    @Test
    void testAuthenticateValidUser() {
        // Test authentication logic
    }

    @Test
    void testAuthenticateInvalidUser() {
        // Test invalid authentication
    }
}
```

### Integration Testing
```java
@QuarkusTest
class AuthResourceTest {

    @Test
    void testLoginEndpoint
