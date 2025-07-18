**User:**

<task>
1. Current Work:
The user has requested several improvements to the autowarehouse application:
1. Add address field to users table and adjust API/frontend to save it
2. Integrate saved profile data into checkout form
3. Replace alert() calls with toast notifications

I was working on adding the address field to the users table. I created a migration file V12__Add_address_to_users.sql and updated the User entity, UpdateProfileRequest DTO, UserService, and UserResource to handle the address field. However, there seems to be a conflict with a V1_1__Add_address_to_users.sql file that's showing in VSCode tabs but doesn't exist on disk.

2. Key Technical Concepts:
- Spring Boot/Quarkus backend with JAX-RS
- Vue.js 3 with TypeScript frontend
- Flyway database migrations
- PostgreSQL database
- Toast notification system using useNotifications composable
- Profile management with address field
- Checkout integration

3. Relevant Files and Code:
- autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql - Migration to add address column
- autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java - Updated with address field
- autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java - Updated with address field
- autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java - Updated to handle address
- autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java - Updated API endpoints
- autowarehouse-frontend/src/services/api.ts - Updated API calls
- autowarehouse-frontend/src/views/UserProfileView.vue - Updated profile form
- autowarehouse-frontend/src/views/CheckoutView.vue - Needs integration with profile data
- autowarehouse-frontend/src/composables/useNotifications.ts - Toast notification system

4. Problem Solving:
There's a migration file conflict that needs to be resolved. The backend is running with mvn quarkus:dev. Need to ensure the address field migration works properly and then integrate it into the checkout process.

5. Pending Tasks and Next Steps:
From the user's message: "saya tadi cek sudah mantap, namun ada beberapa improvement."

The user wants:
1. "addressnya tuh kayanya belom kesimpen deh, di table users memang saya lihat belum ada address, jadi tolong di add lalu adjust api dan frontendnya juga supaya kesimpan" - Add address to users table and adjust API/frontend
2. "ketika mreka mau checkout, tolong diintegrasi juga ke dalam form yang ada di form checkout product yak. Kalo bisa nanti form checkout productnya stylingnya juga dibenerin, biar agak semakin rapih dan smooth" - Integrate profile data into checkout form and improve styling
3. "alert-alertnya, dipakein kayak tadi dong yang kamu sempet buat tadi ada toast bar di kanan atas itu, jangan pake alert gosong lagi" - Replace alerts with toast notifications

Next steps:
1. Resolve the migration file conflict and ensure address field is properly added to database
2. Test that profile address saving works
3. Update checkout form to pre-populate with saved profile data
4. Improve checkout form styling
5. Replace remaining alert() calls with toast notifications throughout the application
</task>
<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:38:11 PM (Asia/Bangkok, UTC+7:00)

# Current Working Directory (e:/Ecomindo/Autowarehouse) Files
.gitignore
DEVELOPMENT_GUIDE.md
prd.md
Prompt-Chat GPT.txt
Prompt-Cline AI.txt
Prompt-UX Pilot.txt
README.md
start-dev.bat
start-dev.sh
test-admin-users.sql
ui-design.md
autowarehouse-backend/
autowarehouse-backend/pom.xml
autowarehouse-backend/src/
autowarehouse-backend/src/main/
autowarehouse-backend/src/main/java/
autowarehouse-backend/src/main/resources/
autowarehouse-backend/src/test/
autowarehouse-backend/src/test/java/
autowarehouse-backend/target/
autowarehouse-backend/target/autowarehouse-backend-dev.jar
autowarehouse-backend/target/build-analytics-event.json
autowarehouse-backend/target/build-metrics.json
autowarehouse-backend/target/classes/
autowarehouse-backend/target/classes/application.properties
autowarehouse-backend/target/classes/com/
autowarehouse-backend/target/classes/db/
autowarehouse-backend/target/classes/META-INF/
autowarehouse-backend/target/generated-sources/
autowarehouse-backend/target/generated-sources/annotations/
autowarehouse-backend/target/generated-test-sources/
autowarehouse-backend/target/generated-test-sources/test-annotations/
autowarehouse-backend/target/maven-status/
autowarehouse-backend/target/maven-status/maven-compiler-plugin/
autowarehouse-backend/target/quarkus/
autowarehouse-backend/target/quarkus/bootstrap/
autowarehouse-backend/target/test-classes/
autowarehouse-frontend/
autowarehouse-frontend/firebase-setup.md
autowarehouse-frontend/index.html
autowarehouse-frontend/package-lock.json
autowarehouse-frontend/package.json
autowarehouse-frontend/postcss.config.js
autowarehouse-frontend/tailwind.config.js
autowarehouse-frontend/tsconfig.json
autowarehouse-frontend/tsconfig.node.json
autowarehouse-frontend/vite.config.ts
autowarehouse-frontend/src/
autowarehouse-frontend/src/App.vue
autowarehouse-frontend/src/main.ts
autowarehouse-frontend/src/style.css
autowarehouse-frontend/src/vite-env.d.ts
autowarehouse-frontend/src/components/
autowarehouse-frontend/src/components/AdminNavbar.vue
autowarehouse-frontend/src/components/Footer.vue
autowarehouse-frontend/src/components/GuestNavbar.vue
autowarehouse-frontend/src/components/NotificationContainer.vue
autowarehouse-frontend/src/components/OrderStatusTimeline.vue
autowarehouse-frontend/src/components/ProductCard.vue
autowarehouse-frontend/src/components/ProductListItem.vue
autowarehouse-frontend/src/components/UserNavbar.vue
autowarehouse-frontend/src/components/admin/
autowarehouse-frontend/src/components/forms/
autowarehouse-frontend/src/composables/
autowarehouse-frontend/src/composables/useCartPersistence.ts
autowarehouse-frontend/src/composables/useNotifications.ts
autowarehouse-frontend/src/router/
autowarehouse-frontend/src/services/
autowarehouse-frontend/src/stores/
autowarehouse-frontend/src/utils/
autowarehouse-frontend/src/views/
chat-history/
chat-history/cline_task_jul-3-2025_11-38-35-am.md
chat-history/cline_task_jul-7-2025_3-50-19-pm.md
chat-history/cline_task_jul-7-2025_10-22-14-am.md
chat-history/cline_task_jul-7-2025_12-19-08-pm.md
chat-history/cline_task_jul-8-2025_1-11-45-pm.md
chat-history/cline_task_jul-8-2025_1-13-32-pm.md
chat-history/cline_task_jul-8-2025_1-30-18-pm.md
chat-history/cline_task_jul-8-2025_1-44-27-pm.md
chat-history/cline_task_jul-8-2025_2-46-10-pm.md
chat-history/cline_task_jul-8-2025_10-50-24-am.md
chat-history/cline_task_jul-8-2025_11-12-09-am.md
chat-history/cline_task_jul-8-2025_11-22-47-am.md
chat-history/cline_task_jul-8-2025_11-32-43-am.md
chat-history/cline_task_jul-8-2025_12-12-20-pm.md
chat-history/cline_task_jul-8-2025_12-28-33-pm.md
chat-history/cline_task_jul-8-2025_12-31-04-pm.md
chat-history/cline_task_jul-8-2025_12-51-57-pm.md
chat-history/cline_task_jul-11-2025_4-09-51-pm.md
chat-history/cline_task_jul-11-2025_4-26-57-pm.md
chat-history/cline_task_jul-11-2025_5-40-51-pm.md
chat-history/cline_task_jul-11-2025_5-48-06-pm.md
chat-history/cline_task_jul-11-2025_5-51-51-pm.md
chat-history/cline_task_jul-11-2025_6-13-21-pm.md
chat-history/cline_task_jul-11-2025_6-47-14-pm.md
chat-history/cline_task_jul-14-2025_2-34-30-pm.md
chat-history/cline_task_jul-14-2025_7-04-07-pm.md
chat-history/cline_task_jul-14-2025_7-16-37-pm.md
chat-history/cline_task_jul-14-2025_7-49-20-pm.md
memory-bank/
memory-bank/architecture.md
memory-bank/backend-implementation.md
memory-bank/current-implementation-status.md
memory-bank/frontend-backend-integration.md
memory-bank/frontend-implementation.md
memory-bank/glossary.md
memory-bank/login-credentials-summary.md
memory-bank/project-overview.md
memory-bank/project-summary.md
memory-bank/README.md
memory-bank/technical-stack.md
memory-bank/test-credentials.md
memory-bank/user-stories.md
memory-bank/user-test-data.md
memory-bank/api/
memory-bank/api/endpoints.md
memory-bank/api/rest-endpoints.md
memory-bank/database/
memory-bank/database/schema.md
memory-bank/features/
memory-bank/features/auction-system.md
memory-bank/features/notifications.md
memory-bank/features/order-management.md
memory-bank/features/product-catalog.md
memory-bank/features/shopping-cart.md
memory-bank/features/user-management.md
memory-bank/ui-design/
memory-bank/ui-design/design-system.md
tasks-readme/
tasks-readme/customer-products-module-tasks.md
tasks-readme/order-history-module-tasks.md
tasks-readme/product-management-integration.md
tasks-readme/product-management-missing-tasks.md
tasks-readme/profile-module-adjustment-tasks.md
tasks-readme/shopping-cart-module-tasks.md
tasks-readme/wishlist-module-tasks.md
ui-design/
ui-design/admin-auction (1).html
ui-design/admin-auction (1).png
ui-design/admin-auction (2).html
ui-design/admin-auction (2).png
ui-design/admin-category-management (1).html
ui-design/admin-category-management (1).png
ui-design/admin-category-management (2).html
ui-design/admin-category-management (2).png
ui-design/admin-customer-service.html
ui-design/admin-customer-service.png
ui-design/admin-dashboard.html
ui-design/admin-dashboard.png
ui-design/admin-notif.html
ui-design/admin-notif.png
ui-design/admin-order-management (1).html
ui-design/admin-order-management (1).png
ui-design/admin-order-management (2).html
ui-design/admin-order-management (2).png
ui-design/admin-product-management (1).html
ui-design/admin-product-management (1).png
ui-design/admin-product-management (2).html
ui-design/admin-product-management (2).png
ui-design/home - Reference UI.html
ui-design/home.png
ui-design/Login (1) - Reference UI.html
ui-design/Login (1).png
ui-design/Login (2) - Reference UI.html
ui-design/Login (2).png
ui-design/Login (3) - Reference UI.html
ui-design/Login (3).png
ui-design/Login (4) - Reference UI.html
ui-design/Login (4).png
ui-design/order-history (1).html
ui-design/order-history (1).png
ui-design/order-history (2).html
ui-design/order-history (2).png
ui-design/order-history (3).html
ui-design/order-history (3).png
ui-design/product-detail (1) - Reference UI.html
ui-design/product-detail (1).png
ui-design/product-detail (2) - Reference UI.html
ui-design/product-detail (2).png
ui-design/Shopping Cart (1) - Reference UI.html
ui-design/Shopping Cart (2) - Reference UI.html
ui-design/Shoppping-Cart (1).png
ui-design/Shoppping-Cart (2).png
ui-design/user-custome-service.png
ui-design/user-customer-service.html
ui-design/user-live-auction (1).html
ui-design/user-live-auction (1).png
ui-design/user-live-auction (2).html
ui-design/user-live-auction (2).png
ui-design/user-live-auction (3).html
ui-design/user-live-auction (3).png
ui-design/user-live-auction (4).html
ui-design/user-live-auction (4).png
ui-design/user-notification.html
ui-design/user-notification.png
ui-design/user-profile.html
ui-design/user-profile.png
ui-design/Wishlist - Reference UI.html
ui-design/Wishlist.png

(File list truncated. Use list_files on specific subdirectories if you need to explore further.)

# Git Remote URLs
origin: https://github.com/S1rent/Autowarehouse.git

# Context Window Usage
0 / 200K tokens used (0%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

<thinking>
Looking at the task context, I need to continue working on the improvements to the autowarehouse application. The user has requested:

1. Add address field to users table and adjust API/frontend to save it
2. Integrate saved profile data into checkout form
3. Replace alert() calls with toast notifications

From the context, it seems like I was working on adding the address field and there's a migration file conflict. I can see there are two migration files in the VSCode tabs:
- V1_1__Add_address_to_users.sql
- V12__Add_address_to_users.sql

Let me first check the current state of the migration files to understand the conflict and resolve it.
</thinking>

I'll help you continue with the improvements to the autowarehouse application. Let me first check the current state of the migration files to resolve the conflict and then proceed with the remaining tasks.

<read_file>
<path>autowarehouse-backend/src/main/resources/db/migration</path>
</read_file>

<list_files>
<path>autowarehouse-backend/src/main/resources/db/migration</path>
</list_files>

[Response interrupted by a tool use result. Only one tool may be used at a time and should be placed at the end of the message.]

---

**User:**

[read_file for 'autowarehouse-backend/src/main/resources/db/migration'] Result:
The tool execution failed with the following error:
<error>
Error reading file: {"errno":-4068,"code":"EISDIR","syscall":"read","name":"Error","message":"EISDIR: illegal operation on a directory, read","stack":"Error: EISDIR: illegal operation on a directory, read\n    at readFileHandle (node:internal/fs/promises:550:24)\n    at pee (c:\\Users\\phili\\.vscode\\extensions\\saoudrizwan.claude-dev-3.19.5\\dist\\extension.js:1082:20449)\n    at Oit.executeTool (c:\\Users\\phili\\.vscode\\extensions\\saoudrizwan.claude-dev-3.19.5\\dist\\extension.js:3860:5933)\n    at qit.presentAssistantMessage (c:\\Users\\phili\\.vscode\\extensions\\saoudrizwan.claude-dev-3.19.5\\dist\\extension.js:3920:4544)"}
</error>
Tool [list_files] was not executed because a tool has already been used in this message. Only one tool may be used per message. You must assess the first tool's result before proceeding to use the next tool.
<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:38:21 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
0 / 200K tokens used (0%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Let me list the migration files to see what's in the directory:

<list_files>
<path>autowarehouse-backend/src/main/resources/db/migration</path>
</list_files>

---

**User:**

[list_files for 'autowarehouse-backend/src/main/resources/db/migration'] Result:
V1__Create_initial_tables.sql
V2__Insert_sample_categories.sql
V3__Insert_sample_users.sql
V4__Insert_additional_test_users.sql
V5__Insert_new_admin_user.sql
V6__Insert_reliable_admin_user.sql
V7__Add_features_column_to_products.sql
V8__Add_tracking_number_to_orders.sql
V9__Add_missing_notification_columns.sql
V10__Create_order_status_history.sql
V11__Ensure_notification_columns_exist.sql
V12__Add_address_to_users.sql
<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:38:26 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
32,381 / 200K tokens used (16%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

I can see that the V12__Add_address_to_users.sql file exists on disk, but there's a V1_1__Add_address_to_users.sql file showing in VSCode tabs that doesn't exist on disk. This is likely a VSCode caching issue. Let me check the content of the V12 migration file and then verify the current state of the User entity:

<read_file>
<path>autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql</path>
</read_file>

[Response interrupted by a tool use result. Only one tool may be used at a time and should be placed at the end of the message.]

---

**User:**

[read_file for 'autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql'] Result:
-- Add address column to users table
ALTER TABLE users ADD COLUMN IF NOT EXISTS address VARCHAR(500);

<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:38:33 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
34,109 / 200K tokens used (17%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Good, the migration file looks correct. Now let me check the User entity to see if it has the address field:

<read_file>
<path>autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java</path>
</read_file>

---

**User:**

[read_file for 'autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java'] Result:
package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
// @UserDefinition // Temporarily disabled to fix registration issue
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true, nullable = false)
    // @Username // Temporarily disabled
    @Email
    @NotBlank
    public String email;

    @Column(nullable = false)
    // @Password // Temporarily disabled
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

    @Column(name = "phone_number")
    @Size(max = 20)
    public String phoneNumber;

    @Column(name = "address")
    @Size(max = 500)
    public String address;

    @Column(nullable = false)
    // @Roles // Temporarily disabled
    public String role = "CUSTOMER";

    @Column(name = "is_active")
    public Boolean isActive = true;

    @Column(name = "is_email_verified")
    public Boolean isEmailVerified = false;

    @Column(name = "google_id")
    public String googleId;

    @Column(name = "profile_image_url")
    public String profileImageUrl;

    @Column(name = "email_verification_token")
    public String emailVerificationToken;

    @Column(name = "password_reset_token")
    public String passwordResetToken;

    @Column(name = "password_reset_token_expiry")
    public LocalDateTime passwordResetTokenExpiry;

    @Column(name = "last_login_at")
    public LocalDateTime lastLoginAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Bid> bids;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<WishlistItem> wishlistItems;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<CartItem> cartItems;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Review> reviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Notification> notifications;

    // Constructors
    public User() {}

    public User(String email, String password, String firstName, String lastName, String role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    // Static finder methods
    public static User findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public static User findByGoogleId(String googleId) {
        return find("googleId", googleId).firstResult();
    }

    public static User findByEmailVerificationToken(String token) {
        return find("emailVerificationToken", token).firstResult();
    }

    public static User findByPasswordResetToken(String token) {
        return find("passwordResetToken = ?1 and passwordResetTokenExpiry > ?2", token, LocalDateTime.now()).firstResult();
    }

    public static List<User> findActiveUsers() {
        return find("isActive", true).list();
    }

    public static List<User> findByRole(String role) {
        return find("role", role).list();
    }

    public static List<User> findRecentUsers(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        return find("createdAt >= ?1", cutoffDate).list();
    }

    public static long countActiveUsers() {
        return count("isActive", true);
    }

    public static long countByRole(String role) {
        return count("role", role);
    }

    // Helper methods
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }

    public boolean isCustomer() {
        return "CUSTOMER".equals(role);
    }

    public boolean isPasswordResetTokenValid() {
        return passwordResetToken != null && 
               passwordResetTokenExpiry != null && 
               passwordResetTokenExpiry.isAfter(LocalDateTime.now());
    }

    public boolean isPasswordResetTokenExpired() {
        return passwordResetToken == null || 
               passwordResetTokenExpiry == null || 
               passwordResetTokenExpiry.isBefore(LocalDateTime.now());
    }

    public void clearPasswordResetToken() {
        this.passwordResetToken = null;
        this.passwordResetTokenExpiry = null;
    }

    public void updateLastLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
}

<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:38:38 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
35,300 / 200K tokens used (18%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Great! I can see the User entity already has the address field. Now let me check the UpdateProfileRequest DTO to ensure it includes the address field:

<read_file>
<path>autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java</path>
</read_file>

---

**User:**

[read_file for 'autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java'] Result:
package com.autowarehouse.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateProfileRequest {
    
    @NotBlank(message = "First name is required")
    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    public String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    public String lastName;
    
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    public String phoneNumber;
    
    @Size(max = 500, message = "Address must not exceed 500 characters")
    public String address;
    
    // Default constructor
    public UpdateProfileRequest() {}
    
    // Constructor with parameters
    public UpdateProfileRequest(String firstName, String lastName, String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}

<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:38:44 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
38,161 / 200K tokens used (19%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Perfect! The DTO also has the address field. Now let me check the UserService to ensure it handles the address field properly:

<read_file>
<path>autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java</path>
</read_file>

---

**User:**

[read_file for 'autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java'] Result:
package com.autowarehouse.service;

import com.autowarehouse.entity.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    EmailService emailService;

    @Inject
    JwtService jwtService;

    @Transactional
    public User createUser(@Valid User user) {
        // Check if email already exists
        if (User.findByEmail(user.email) != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Hash password
        user.password = BcryptUtil.bcryptHash(user.password);
        
        // Set default values
        user.isActive = true;
        user.isEmailVerified = false;
        user.role = user.role != null ? user.role : "CUSTOMER";

        // Generate simple verification token
        user.emailVerificationToken = "temp-token-" + System.currentTimeMillis();
        
        user.persist();
        
        // Skip email sending for now to avoid errors
        System.out.println("User registered successfully: " + user.email);
        
        return user;
    }

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

    @Transactional
    public void updateLastLogin(User user) {
        // Merge the detached entity first
        User managedUser = User.findById(user.id);
        if (managedUser != null) {
            managedUser.lastLoginAt = LocalDateTime.now();
            managedUser.persist();
        }
    }

    public User findById(Long id) {
        return User.findById(id);
    }

    public User findByEmail(String email) {
        return User.findByEmail(email);
    }

    public List<User> findAllCustomers() {
        return User.findByRole("CUSTOMER");
    }

    public List<User> findAllAdmins() {
        return User.findByRole("ADMIN");
    }

    public List<User> findActiveUsers() {
        return User.findActiveUsers();
    }

    public List<User> findRecentUsers(int days) {
        return User.findRecentUsers(days);
    }

    @Transactional
    public User updateProfile(Long userId, String firstName, String lastName, String phoneNumber, String address) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        user.firstName = firstName;
        user.lastName = lastName;
        user.phoneNumber = phoneNumber;
        user.address = address;
        user.persist();
        
        return user;
    }

    @Transactional
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (!BcryptUtil.matches(currentPassword, user.password)) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        user.password = BcryptUtil.bcryptHash(newPassword);
        user.persist();
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

        // Send password reset email
        emailService.sendPasswordResetEmail(user, resetToken);
    }

    @Transactional
    public void confirmPasswordReset(String token, String newPassword) {
        User user = User.findByPasswordResetToken(token);
        if (user == null || user.isPasswordResetTokenExpired()) {
            throw new IllegalArgumentException("Invalid or expired reset token");
        }

        user.password = BcryptUtil.bcryptHash(newPassword);
        user.passwordResetToken = null;
        user.passwordResetTokenExpiry = null;
        user.persist();

        // Send password changed notification
        emailService.sendPasswordChangedNotification(user);
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

        // Send welcome email
        emailService.sendWelcomeEmail(user);
    }

    @Transactional
    public void deactivateUser(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        user.isActive = false;
        user.persist();
    }

    @Transactional
    public void activateUser(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        user.isActive = true;
        user.persist();
    }

    @Transactional
    public void updateRole(Long userId, String role) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        user.role = role;
        user.persist();
    }

    public long getTotalUsersCount() {
        return User.count();
    }

    public long getActiveUsersCount() {
        return User.countActiveUsers();
    }

    public long getCustomersCount() {
        return User.countByRole("CUSTOMER");
    }

    public long getAdminsCount() {
        return User.countByRole("ADMIN");
    }

    public boolean emailExists(String email) {
        return User.findByEmail(email) != null;
    }

    public boolean isValidUser(Long userId) {
        User user = User.findById(userId);
        return user != null && user.isActive;
    }
}

<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:38:49 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
41,196 / 200K tokens used (21%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Excellent! The UserService already has the updateProfile method that handles the address field. Now let me check the UserResource to ensure the API endpoints are properly set up:

<read_file>
<path>autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java</path>
</read_file>

---

**User:**

[read_file for 'autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java'] Result:
package com.autowarehouse.resource;

import com.autowarehouse.dto.UpdateProfileRequest;
import com.autowarehouse.dto.ChangePasswordRequest;
import com.autowarehouse.entity.User;
import com.autowarehouse.entity.UserRole;
import com.autowarehouse.service.UserService;
import com.autowarehouse.service.JwtService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    JwtService jwtService;

    @POST
    @Path("/register")
    public Response register(@Valid RegisterRequest request) {
        try {
            User user = userService.registerCustomer(
                request.email, 
                request.password, 
                request.firstName, 
                request.lastName
            );
            return Response.status(Response.Status.CREATED)
                    .entity(new UserResponse(user))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest request) {
        try {
            User user = userService.authenticate(request.email, request.password);
            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("Invalid credentials"))
                        .build();
            }
            
            String token = jwtService.generateToken(user);
            return Response.ok(new LoginResponse(user, token))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Login failed"))
                    .build();
        }
    }

    @GET
    @Path("/profile/{id}")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response getUserProfile(@PathParam("id") Long id) {
        try {
            User user = userService.findById(id);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("User not found"))
                        .build();
            }
            return Response.ok(new UserResponse(user)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to get user profile"))
                    .build();
        }
    }

    @PUT
    @Path("/profile/{id}")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response updateProfile(@PathParam("id") Long id, @Valid UpdateProfileRequest request, @Context SecurityContext securityContext) {
        try {
            // Extract user from JWT token for security
            String userEmail = securityContext.getUserPrincipal().getName();
            User currentUser = userService.findByEmail(userEmail);
            
            if (currentUser == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("User not found"))
                        .build();
            }
            
            // Check if user is trying to update their own profile or if they're admin
            if (!currentUser.id.equals(id) && !"ADMIN".equals(currentUser.role)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity(new ErrorResponse("You can only update your own profile"))
                        .build();
            }
            
            User user = userService.updateProfile(id, request.firstName, request.lastName, request.phoneNumber, request.address);
            return Response.ok(new UserResponse(user)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/change-password")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response changePassword(@Valid ChangePasswordRequest request, @Context SecurityContext securityContext) {
        try {
            // Extract user ID from JWT token for security
            String userEmail = securityContext.getUserPrincipal().getName();
            User currentUser = userService.findByEmail(userEmail);
            
            if (currentUser == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("User not found"))
                        .build();
            }
            
            userService.changePassword(currentUser.id, request.currentPassword, request.newPassword);
            return Response.ok(new SuccessResponse("Password changed successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/forgot-password")
    public Response forgotPassword(@Valid ForgotPasswordRequest request) {
        try {
            userService.resetPassword(request.email);
            return Response.ok(new SuccessResponse("Password reset email sent")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/reset-password")
    public Response resetPassword(@Valid ResetPasswordRequest request) {
        try {
            userService.confirmPasswordReset(request.token, request.newPassword);
            return Response.ok(new SuccessResponse("Password reset successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/verify-email")
    public Response verifyEmail(@Valid EmailVerificationRequest request) {
        try {
            userService.verifyEmail(request.token);
            return Response.ok(new SuccessResponse("Email verified successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    // Admin endpoints
    @GET
    @Path("/admin/customers")
    @RolesAllowed("ADMIN")
    public Response getAllCustomers() {
        List<User> customers = userService.findAllCustomers();
        List<UserResponse> response = customers.stream()
                .map(UserResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    @GET
    @Path("/admin/stats")
    @RolesAllowed("ADMIN")
    public Response getUserStats() {
        UserStatsResponse stats = new UserStatsResponse();
        stats.totalUsers = userService.getTotalUsersCount();
        stats.activeUsers = userService.getActiveUsersCount();
        stats.totalCustomers = userService.getCustomersCount();
        stats.totalAdmins = userService.getAdminsCount();
        return Response.ok(stats).build();
    }

    @PUT
    @Path("/admin/{id}/role")
    @RolesAllowed("ADMIN")
    public Response updateUserRole(@PathParam("id") Long id, @Valid UpdateRoleRequest request) {
        try {
            userService.updateRole(id, request.role);
            return Response.ok(new SuccessResponse("User role updated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/deactivate")
    @RolesAllowed("ADMIN")
    public Response deactivateUser(@PathParam("id") Long id) {
        try {
            userService.deactivateUser(id);
            return Response.ok(new SuccessResponse("User deactivated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/activate")
    @RolesAllowed("ADMIN")
    public Response activateUser(@PathParam("id") Long id) {
        try {
            userService.activateUser(id);
            return Response.ok(new SuccessResponse("User activated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    // DTO Classes
    public static class RegisterRequest {
        public String email;
        public String password;
        public String firstName;
        public String lastName;
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }


    public static class ForgotPasswordRequest {
        public String email;
    }

    public static class ResetPasswordRequest {
        public String token;
        public String newPassword;
    }

    public static class EmailVerificationRequest {
        public String token;
    }

    public static class UpdateRoleRequest {
        public String role;
    }

    public static class UserResponse {
        public Long id;
        public String email;
        public String firstName;
        public String lastName;
        public String phoneNumber;
        public String address;
        public String role;
        public Boolean isActive;
        public Boolean isEmailVerified;

        public UserResponse(User user) {
            this.id = user.id;
            this.email = user.email;
            this.firstName = user.firstName;
            this.lastName = user.lastName;
            this.phoneNumber = user.phoneNumber;
            this.address = user.address;
            this.role = user.role;
            this.isActive = user.isActive;
            this.isEmailVerified = user.isEmailVerified;
        }
    }

    public static class LoginResponse {
        public UserResponse user;
        public String token;

        public LoginResponse(User user, String token) {
            this.user = new UserResponse(user);
            this.token = token;
        }
    }

    public static class UserStatsResponse {
        public long totalUsers;
        public long activeUsers;
        public long totalCustomers;
        public long totalAdmins;
    }

    public static class ErrorResponse {
        public String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }

    public static class SuccessResponse {
        public String message;

        public SuccessResponse(String message) {
            this.message = message;
        }
    }
}

<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:38:55 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
44,337 / 200K tokens used (22%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Perfect! The backend is already set up properly with the address field. I can see that the UserResponse class includes the address field and the updateProfile endpoint handles it. Now let me check the frontend API service to ensure it's properly configured:

<read_file>
<path>autowarehouse-frontend/src/services/api.ts</path>
</read_file>

---

**User:**

[read_file for 'autowarehouse-frontend/src/services/api.ts'] Result:
import axios, { AxiosInstance, AxiosResponse } from 'axios'

// API Configuration
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8081/api'

// Create axios instance
const api: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor to add auth token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('auth_token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response interceptor for error handling
api.interceptors.response.use(
  (response: AxiosResponse) => {
    return response
  },
  (error) => {
    if (error.response?.status === 401) {
      // Token expired or invalid
      localStorage.removeItem('auth_token')
      localStorage.removeItem('user_data')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

// API Response Types
export interface ApiResponse<T = any> {
  data: T
  message?: string
}

export interface ErrorResponse {
  message: string
}

// User Types
export interface User {
  id: number
  email: string
  firstName: string
  lastName: string
  phoneNumber?: string
  address?: string
  role: 'ADMIN' | 'CUSTOMER'
  isActive: boolean
  isEmailVerified: boolean
  createdAt?: string
}

export interface LoginRequest {
  email: string
  password: string
}

export interface AuthResponse {
  message: string
  accessToken?: string
  refreshToken?: string
  userId?: number
  email?: string
  firstName?: string
  lastName?: string
  role?: string
  isEmailVerified?: boolean
}

export interface RegisterRequest {
  email: string
  password: string
  firstName: string
  lastName: string
}

export interface PasswordResetRequest {
  email: string
}

export interface PasswordResetConfirmRequest {
  token: string
  newPassword: string
}

export interface UpdateProfileRequest {
  firstName: string
  lastName: string
  phoneNumber?: string
  address?: string
}

// Product Types
export interface Product {
  id: number
  name: string
  description: string
  price: number
  originalPrice: number
  brand: string
  model: string
  specifications?: string
  features?: string
  imageUrls: string[]
  stockQuantity: number
  isActive: boolean
  isOnSale: boolean
  rating: number
  reviewCount: number
  sku: string
  categoryName: string
  createdAt: string
}

export interface ProductFilters {
  category?: number
  brand?: string
  minPrice?: number
  maxPrice?: number
  search?: string
  onSale?: boolean
  status?: string
  sortBy?: string
  sortOrder?: string
  page?: number
  size?: number
}

// Auction Types
export interface Auction {
  id: number
  title: string
  description: string
  startingBid: number
  currentBid: number
  reservePrice?: number
  bidIncrement: number
  startTime: string
  endTime: string
  status: 'DRAFT' | 'SCHEDULED' | 'LIVE' | 'ENDED' | 'CANCELLED'
  bidCount: number
  watcherCount: number
  productName: string
  categoryName: string
  autoExtendMinutes?: number
  winnerName?: string
  winningBid?: number
  createdAt: string
}

export interface BidRequest {
  userId: number
  bidAmount: number
  isAutoBid: boolean
  maxAutoBid?: number
}

export interface Bid {
  id: number
  bidAmount: number
  isAutoBid: boolean
  isWinning: boolean
  bidderName: string
  createdAt: string
}

// Order Types
export interface Order {
  id: number
  orderNumber: string
  status: 'PENDING' | 'CONFIRMED' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED'
  paymentStatus: 'PENDING' | 'PAID' | 'FAILED' | 'REFUNDED'
  subtotal: number
  taxAmount: number
  shippingCost: number
  totalAmount: number
  customerName: string
  shippingAddress: string
  createdAt: string
}

export interface OrderDetail extends Order {
  billingAddress?: string
  paymentMethod?: string
  paymentReference?: string
  notes?: string
  shippedAt?: string
  deliveredAt?: string
  items?: OrderItem[]
  statusHistory?: any[]
}

export interface OrderItem {
  id: number
  productId: number
  productName: string
  productSku: string
  productPrice: number
  productImages?: string[]
  quantity: number
  subtotal: number
}

export interface CheckoutOrderRequest {
  userId: number
  shippingAddress: string
  billingAddress?: string
  paymentMethod: string
}

// Category Types
export interface Category {
  id: number
  name: string
  description?: string
  slug: string
  parentId?: number
  parent?: Category
  children?: Category[]
  imageUrl?: string
  isActive: boolean
  sortOrder: number
  createdAt: string
  updatedAt?: string
}

export interface CreateCategoryRequest {
  name: string
  description?: string
  slug: string
  parentId?: number
  imageUrl?: string
  isActive?: boolean
  sortOrder?: number
}

export interface UpdateCategoryRequest {
  name?: string
  description?: string
  slug?: string
  parentId?: number
  imageUrl?: string
  isActive?: boolean
  sortOrder?: number
}

export interface CreateProductRequest {
  name: string
  description: string
  price: number
  originalPrice?: number
  stockQuantity: number
  brand: string
  model: string
  sku?: string
  specifications?: string
  features?: string
  imageUrls: string[]
  categoryId: number
}

export interface UpdateProductRequest extends CreateProductRequest {
  isActive?: boolean
}

// Cart Types
export interface CartItem {
  id: number
  productId: number
  productName: string
  productSku: string
  productBrand: string
  productPrice: number
  originalPrice: number
  productImages: string[]
  quantity: number
  isSelected: boolean
  subtotal: number
  savings: number
  availableStock: number
  isProductActive: boolean
}

export interface CartSummary {
  totalItems: number
  selectedItems: number
  totalQuantity: number
  selectedQuantity: number
  totalAmount: number
  selectedAmount: number
  totalSavings: number
  selectedSavings: number
}

export interface AddToCartRequest {
  userId: number
  productId: number
  quantity: number
}

// Saved Items Types
export interface SavedItem {
  id: number
  userId: number
  productId: number
  productName: string
  productBrand: string
  productSku: string
  productPrice: number
  originalPrice?: number
  productImages: string[]
  quantity: number
  subtotal: number
  savings?: number
  savedFromCart: boolean
  isProductActive: boolean
  availableStock: number
  createdAt: string
}

export interface SaveForLaterRequest {
  userId: number
  productId: number
  quantity: number
}

// Wishlist Types
export interface WishlistItem {
  id: number
  product: Product
  createdAt: string
}

export interface AddToWishlistRequest {
  userId: number
  productId: number
}

export interface RemoveFromWishlistRequest {
  userId: number
  productId: number
}

// Statistics Types
export interface UserStats {
  totalUsers: number
  activeUsers: number
  totalCustomers: number
  totalAdmins: number
}

export interface ProductStats {
  totalProducts: number
  activeProducts: number
  lowStockProducts: number
  outOfStockProducts: number
}

export interface AuctionStats {
  totalAuctions: number
  liveAuctions: number
  upcomingAuctions: number
}

export interface OrderStats {
  totalOrders: number
  pendingOrders: number
  completedOrders: number
  totalRevenue: number
}

// API Service Class
class ApiService {
  // Authentication APIs
  async login(credentials: LoginRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/auth/login', credentials)
    return response.data
  }

  async register(userData: RegisterRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/auth/register', userData)
    return response.data
  }

  async verifyEmail(token: string): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>(`/auth/verify-email?token=${token}`)
    return response.data
  }

  async forgotPassword(request: PasswordResetRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/auth/forgot-password', request)
    return response.data
  }

  async resetPassword(request: PasswordResetConfirmRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/auth/reset-password', request)
    return response.data
  }

  async resendVerification(email: string): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>(`/auth/resend-verification?email=${email}`)
    return response.data
  }

  async getCurrentUser(): Promise<AuthResponse> {
    const response = await api.get<AuthResponse>('/auth/me')
    return response.data
  }

  // User Profile APIs
  async getUserProfile(userId: number): Promise<User> {
    const response = await api.get<User>(`/users/profile/${userId}`)
    return response.data
  }

  async updateUserProfile(userId: number, profileData: UpdateProfileRequest): Promise<User> {
    const response = await api.put<User>(`/users/profile/${userId}`, profileData)
    return response.data
  }

  async changePassword(currentPassword: string, newPassword: string): Promise<{ message: string }> {
    const response = await api.post<{ message: string }>('/users/change-password', {
      currentPassword,
      newPassword
    })
    return response.data
  }

  // Product APIs
  async getProducts(filters?: ProductFilters): Promise<Product[]> {
    const params = new URLSearchParams()
    if (filters) {
      Object.entries(filters).forEach(([key, value]) => {
        if (value !== undefined && value !== null) {
          params.append(key, value.toString())
        }
      })
    }
    const response = await api.get<Product[]>(`/products?${params.toString()}`)
    return response.data
  }

  async getProduct(productId: number): Promise<Product> {
    const response = await api.get<Product>(`/products/${productId}`)
    return response.data
  }

  async getPopularProducts(limit: number = 10): Promise<Product[]> {
    const response = await api.get<Product[]>(`/products/featured/popular?limit=${limit}`)
    return response.data
  }

  async getFeaturedProducts(limit: number = 10): Promise<Product[]> {
    const response = await api.get<Product[]>(`/products/featured/featured?limit=${limit}`)
    return response.data
  }

  async getOnSaleProducts(limit: number = 10): Promise<Product[]> {
    const response = await api.get<Product[]>(`/products/featured/on-sale?limit=${limit}`)
    return response.data
  }

  // Admin Product APIs
  async createProduct(productData: CreateProductRequest): Promise<Product> {
    const response = await api.post<Product>('/products/admin', productData)
    return response.data
  }

  async updateProduct(productId: number, productData: UpdateProductRequest): Promise<Product> {
    const response = await api.put<Product>(`/products/admin/${productId}`, productData)
    return response.data
  }

  async deleteProduct(productId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/products/admin/${productId}`)
    return response.data
  }

  async updateProductStock(productId: number, stockQuantity: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/products/admin/${productId}/stock`, { stockQuantity })
    return response.data
  }

  async activateProduct(productId: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/products/admin/${productId}/activate`)
    return response.data
  }

  async deactivateProduct(productId: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/products/admin/${productId}/deactivate`)
    return response.data
  }

  async getAdminProductStats(): Promise<ProductStats> {
    const response = await api.get<ProductStats>('/products/admin/stats')
    return response.data
  }

  async getLowStockProducts(threshold: number = 10): Promise<Product[]> {
    const response = await api.get<Product[]>(`/products/admin/low-stock?threshold=${threshold}`)
    return response.data
  }

  async getOutOfStockProducts(): Promise<Product[]> {
    const response = await api.get<Product[]>('/products/admin/out-of-stock')
    return response.data
  }

  // Auction APIs
  async getAuctions(status?: string): Promise<Auction[]> {
    const params = status ? `?status=${status}` : ''
    const response = await api.get<Auction[]>(`/auctions${params}`)
    return response.data
  }

  async getLiveAuctions(): Promise<Auction[]> {
    const response = await api.get<Auction[]>('/auctions/live')
    return response.data
  }

  async getUpcomingAuctions(): Promise<Auction[]> {
    const response = await api.get<Auction[]>('/auctions/upcoming')
    return response.data
  }

  async getEndingSoonAuctions(): Promise<Auction[]> {
    const response = await api.get<Auction[]>('/auctions/ending-soon')
    return response.data
  }

  async getAuction(auctionId: number): Promise<Auction> {
    const response = await api.get<Auction>(`/auctions/${auctionId}`)
    return response.data
  }

  async placeBid(auctionId: number, bidData: BidRequest): Promise<Bid> {
    const response = await api.post<Bid>(`/auctions/${auctionId}/bids`, bidData)
    return response.data
  }

  async watchAuction(auctionId: number, userId: number): Promise<{ message: string }> {
    const response = await api.post<{ message: string }>(`/auctions/${auctionId}/watch`, { userId })
    return response.data
  }

  async unwatchAuction(auctionId: number, userId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/auctions/${auctionId}/watch/${userId}`)
    return response.data
  }

  // Cart APIs
  async getCartItems(userId: number): Promise<CartItem[]> {
    const response = await api.get<CartItem[]>(`/cart/user/${userId}`)
    return response.data
  }

  async getSelectedCartItems(userId: number): Promise<CartItem[]> {
    const response = await api.get<CartItem[]>(`/cart/user/${userId}/selected`)
    return response.data
  }

  async getCartSummary(userId: number): Promise<CartSummary> {
    const response = await api.get<CartSummary>(`/cart/user/${userId}/summary`)
    return response.data
  }

  async getCartItemCount(userId: number): Promise<{ count: number }> {
    const response = await api.get<{ count: number }>(`/cart/user/${userId}/count`)
    return response.data
  }

  async addToCart(cartData: AddToCartRequest): Promise<CartItem> {
    const response = await api.post<CartItem>('/cart/add', cartData)
    return response.data
  }

  async updateCartItemQuantity(cartItemId: number, quantity: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/cart/${cartItemId}/quantity`, { quantity })
    return response.data
  }

  async toggleCartItemSelection(cartItemId: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/cart/${cartItemId}/toggle-selection`)
    return response.data
  }

  async selectAllCartItems(userId: number, selected: boolean): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/cart/user/${userId}/select-all`, { selected })
    return response.data
  }

  async removeFromCart(cartItemId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/cart/${cartItemId}`)
    return response.data
  }

  async clearCart(userId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/cart/user/${userId}/clear`)
    return response.data
  }

  async checkProductInCart(userId: number, productId: number): Promise<{ exists: boolean }> {
    const response = await api.get<{ exists: boolean }>(`/cart/user/${userId}/product/${productId}/exists`)
    return response.data
  }

  // Saved Items APIs
  async getSavedItems(userId: number): Promise<SavedItem[]> {
    const response = await api.get<SavedItem[]>(`/saved-items/user/${userId}`)
    return response.data
  }

  async getSavedItemCount(userId: number): Promise<{ count: number }> {
    const response = await api.get<{ count: number }>(`/saved-items/user/${userId}/count`)
    return response.data
  }

  async saveForLater(savedData: SaveForLaterRequest): Promise<SavedItem> {
    const response = await api.post<SavedItem>('/saved-items/save', savedData)
    return response.data
  }

  async moveToSaved(cartItemId: number): Promise<SavedItem> {
    const response = await api.post<SavedItem>(`/saved-items/move-from-cart/${cartItemId}`)
    return response.data
  }

  async moveToCart(savedItemId: number): Promise<CartItem> {
    const response = await api.post<CartItem>(`/saved-items/${savedItemId}/move-to-cart`)
    return response.data
  }

  async moveAllSavedToCart(userId: number): Promise<CartItem[]> {
    const response = await api.post<CartItem[]>(`/saved-items/user/${userId}/move-all-to-cart`)
    return response.data
  }

  async updateSavedItemQuantity(savedItemId: number, quantity: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/saved-items/${savedItemId}/quantity`, { quantity })
    return response.data
  }

  async removeSavedItem(savedItemId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/saved-items/${savedItemId}`)
    return response.data
  }

  async clearSavedItems(userId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/saved-items/user/${userId}/clear`)
    return response.data
  }

  async checkProductSaved(userId: number, productId: number): Promise<{ exists: boolean }> {
    const response = await api.get<{ exists: boolean }>(`/saved-items/user/${userId}/product/${productId}/exists`)
    return response.data
  }

  // Wishlist APIs
  async getWishlistItems(userId: number): Promise<WishlistItem[]> {
    const response = await api.get<WishlistItem[]>(`/wishlist/user/${userId}`)
    return response.data
  }

  async addToWishlist(wishlistData: AddToWishlistRequest): Promise<WishlistItem> {
    const response = await api.post<WishlistItem>('/wishlist/add', wishlistData)
    return response.data
  }

  async removeFromWishlist(wishlistData: RemoveFromWishlistRequest): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>('/wishlist/remove', { data: wishlistData })
    return response.data
  }

  async getWishlistCount(userId: number): Promise<{ count: number }> {
    const response = await api.get<{ count: number }>(`/wishlist/user/${userId}/count`)
    return response.data
  }

  async checkProductInWishlist(userId: number, productId: number): Promise<{ exists: boolean }> {
    const response = await api.get<{ exists: boolean }>(`/wishlist/user/${userId}/product/${productId}/exists`)
    return response.data
  }

  async clearWishlist(userId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/wishlist/user/${userId}/clear`)
    return response.data
  }

// Order APIs
  async createOrder(userId: number): Promise<Order> {
    const response = await api.post<Order>('/orders/create', { userId })
    return response.data
  }

  async createOrderWithCheckout(checkoutData: CheckoutOrderRequest): Promise<OrderDetail> {
    const response = await api.post<OrderDetail>('/orders/checkout', checkoutData)
    return response.data
  }

  async getOrder(orderId: number): Promise<Order> {
    const response = await api.get<Order>(`/orders/${orderId}`)
    return response.data
  }

  async getUserOrders(userId: number): Promise<Order[]> {
    const response = await api.get<Order[]>(`/orders/user/${userId}`)
    return response.data
  }

  async getOrderStatusHistory(orderId: number): Promise<any[]> {
    const response = await api.get<any[]>(`/orders/${orderId}/status-history`)
    return response.data
  }

  // Admin APIs
  async getCustomers(): Promise<User[]> {
    const response = await api.get<User[]>('/users/admin/customers')
    return response.data
  }

  async getUserStats(): Promise<UserStats> {
    const response = await api.get<UserStats>('/users/admin/stats')
    return response.data
  }

  async getProductStats(): Promise<ProductStats> {
    const response = await api.get<ProductStats>('/products/admin/stats')
    return response.data
  }

  async getAuctionStats(): Promise<AuctionStats> {
    const response = await api.get<AuctionStats>('/auctions/admin/stats')
    return response.data
  }

  async getOrderStats(): Promise<OrderStats> {
    const response = await api.get<OrderStats>('/orders/admin/stats')
    return response.data
  }

  async getAllOrders(status?: string): Promise<Order[]> {
    const params = status ? `?status=${status}` : ''
    const response = await api.get<Order[]>(`/orders/admin/all${params}`)
    return response.data
  }

  async updateOrderStatus(orderId: number, status: string): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/orders/admin/${orderId}/status`, { status })
    return response.data
  }

  // Category APIs
  async getCategories(): Promise<Category[]> {
    const response = await api.get<Category[]>('/categories')
    return response.data
  }

  async getCategory(categoryId: number): Promise<Category> {
    const response = await api.get<Category>(`/categories/${categoryId}`)
    return response.data
  }

  async getCategoryBySlug(slug: string): Promise<Category> {
    const response = await api.get<Category>(`/categories/slug/${slug}`)
    return response.data
  }

  async getRootCategories(): Promise<Category[]> {
    const response = await api.get<Category[]>('/categories/root')
    return response.data
  }

  async createCategory(categoryData: CreateCategoryRequest): Promise<Category> {
    const response = await api.post<Category>('/categories', categoryData)
    return response.data
  }

  async updateCategory(categoryId: number, categoryData: UpdateCategoryRequest): Promise<Category> {
    const response = await api.put<Category>(`/categories/${categoryId}`, categoryData)
    return response.data
  }

  async deleteCategory(categoryId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/categories/${categoryId}`)
    return response.data
  }
}

// Export singleton instance
export const apiService = new ApiService()
export default api

<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:39:02 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
50,046 / 200K tokens used (25%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Excellent! The API service already includes the address field in the User interface and UpdateProfileRequest. Now let me check the UserProfileView to see if it's properly handling the address field:

<read_file>
<path>autowarehouse-frontend/src/views/UserProfileView.vue</path>
</read_file>

---

**User:**

[read_file for 'autowarehouse-frontend/src/views/UserProfileView.vue'] Result:
<template>
  <div class="bg-gray-50 font-inter min-h-screen">
    <!-- Header -->
    <UserNavbar/>

    <!-- Main Content -->
    <main class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center space-x-3 mb-2">
          <button @click="$router.go(-1)" class="text-gray-500 hover:text-gray-700">
            <i class="fa-solid fa-arrow-left text-lg"></i>
          </button>
          <h1 class="text-2xl font-bold text-gray-900">User Profile</h1>
        </div>
        <p class="text-gray-600">Manage your personal information and account settings</p>
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- Profile Sidebar -->
        <div class="lg:col-span-1">
          <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
            <div class="text-center">
              <div class="w-24 h-24 rounded-full mx-auto mb-4 border-4 border-blue-600 bg-gray-100 flex items-center justify-center">
                <i class="fa-solid fa-user text-3xl text-gray-500"></i>
              </div>
              <h3 class="text-lg font-semibold text-gray-900 mb-1">{{ user.name }}</h3>
              <p class="text-sm text-gray-500 mb-4">Member</p>
              <!-- <button 
                @click="changePhoto"
                class="bg-blue-600 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-blue-700 transition-colors"
              >
                <i class="fa-solid fa-camera mr-2"></i>
                Change Photo
              </button> -->
            </div>
          </div>

          <!-- Quick Actions -->
          <!-- <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mt-6">
            <h4 class="text-lg font-semibold text-gray-900 mb-4">Quick Actions</h4>
            <div class="space-y-3">
              <button 
                @click="downloadData"
                class="w-full flex items-center space-x-3 p-3 text-left rounded-lg hover:bg-gray-50 transition-colors"
              >
                <i class="fa-solid fa-download text-blue-600"></i>
                <span class="text-sm font-medium text-gray-700">Download Data</span>
              </button>
              <button 
                @click="openSecurity"
                class="w-full flex items-center space-x-3 p-3 text-left rounded-lg hover:bg-gray-50 transition-colors"
              >
                <i class="fa-solid fa-shield-alt text-blue-600"></i>
                <span class="text-sm font-medium text-gray-700">Security</span>
              </button>
              <button 
                @click="openNotifications"
                class="w-full flex items-center space-x-3 p-3 text-left rounded-lg hover:bg-gray-50 transition-colors"
              >
                <i class="fa-solid fa-bell text-blue-600"></i>
                <span class="text-sm font-medium text-gray-700">Notifications</span>
              </button>
            </div>
          </div> -->
        </div>

        <!-- Profile Content -->
        <div class="lg:col-span-2 space-y-6">
          <!-- Navigation Tabs -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200">
            <div class="border-b border-gray-200">
              <nav class="-mb-px flex space-x-8 px-6">
                <button
                  @click="activeTab = 'profile'"
                  class="py-4 px-1 border-b-2 font-medium text-sm transition-colors"
                  :class="activeTab === 'profile' 
                    ? 'border-blue-600 text-blue-600' 
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'"
                >
                  <i class="fa-solid fa-user mr-2"></i>
                  Profile
                </button>
                <button
                  @click="activeTab = 'wishlist'"
                  class="py-4 px-1 border-b-2 font-medium text-sm transition-colors"
                  :class="activeTab === 'wishlist' 
                    ? 'border-blue-600 text-blue-600' 
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'"
                >
                  <i class="fa-solid fa-heart mr-2"></i>
                  Wishlist ({{ wishlistStore.wishlistCount }})
                </button>
                <button
                  @click="activeTab = 'orders'"
                  class="py-4 px-1 border-b-2 font-medium text-sm transition-colors"
                  :class="activeTab === 'orders' 
                    ? 'border-blue-600 text-blue-600' 
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'"
                >
                  <i class="fa-solid fa-shopping-bag mr-2"></i>
                  Orders
                </button>
              </nav>
            </div>
          </div>

          <!-- Profile Tab Content -->
          <div v-if="activeTab === 'profile'" class="space-y-6">
            <!-- Personal Information Section -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200">
            <div class="p-6 border-b border-gray-200">
              <div class="flex items-center justify-between">
                <h3 class="text-lg font-semibold text-gray-900">Personal Information</h3>
                <button 
                  @click="toggleEditPersonal"
                  :disabled="isUpdatingProfile"
                  :class="isEditingPersonal ? 'bg-green-600 hover:bg-green-700' : 'bg-blue-600 hover:bg-blue-700'"
                  class="text-white px-4 py-2 rounded-lg text-sm font-medium transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  <i 
                    :class="isUpdatingProfile ? 'fa-solid fa-spinner fa-spin' : (isEditingPersonal ? 'fa-solid fa-save' : 'fa-solid fa-edit')" 
                    class="mr-2"
                  ></i>
                  {{ isUpdatingProfile ? 'Saving...' : (isEditingPersonal ? 'Save' : 'Edit') }}
                </button>
              </div>
            </div>
            <div class="p-6">
              <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Full Name</label>
                  <input 
                    type="text" 
                    v-model="user.name"
                    :readonly="!isEditingPersonal"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent"
                    :class="{ 'bg-gray-50': !isEditingPersonal }"
                  >
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Email</label>
                  <input 
                    type="email" 
                    v-model="user.email"
                    :readonly="!isEditingPersonal"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent"
                    :class="{ 'bg-gray-50': !isEditingPersonal }"
                  >
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Phone Number</label>
                  <input 
                    type="tel" 
                    v-model="user.phone"
                    :readonly="!isEditingPersonal"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent"
                    :class="{ 'bg-gray-50': !isEditingPersonal }"
                  >
                </div>
                <!-- <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Date of Birth</label>
                  <input 
                    type="date" 
                    v-model="user.birthDate"
                    :readonly="!isEditingPersonal"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent"
                    :class="{ 'bg-gray-50': !isEditingPersonal }"
                  >
                </div> -->
                <div class="md:col-span-2">
                  <label class="block text-sm font-medium text-gray-700 mb-2">Address</label>
                  <textarea 
                    rows="3" 
                    v-model="user.address"
                    :readonly="!isEditingPersonal"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent"
                    :class="{ 'bg-gray-50': !isEditingPersonal }"
                  ></textarea>
                </div>
              </div>
            </div>
          </div>

          <!-- Password Section -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200">
            <div class="p-6 border-b border-gray-200">
              <h3 class="text-lg font-semibold text-gray-900">Change Password</h3>
              <p class="text-sm text-gray-600 mt-1">Make sure your new password is secure and memorable</p>
            </div>
            <div class="p-6">
              <div class="space-y-4">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Current Password</label>
                  <input 
                    type="password" 
                    v-model="passwordForm.currentPassword"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent" 
                    placeholder="Enter current password"
                  >
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">New Password</label>
                  <input 
                    type="password" 
                    v-model="passwordForm.newPassword"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent" 
                    placeholder="Enter new password"
                  >
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Confirm New Password</label>
                  <input 
                    type="password" 
                    v-model="passwordForm.confirmPassword"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent" 
                    placeholder="Confirm new password"
                  >
                </div>
              </div>
              <div class="mt-6">
                <button 
                  @click="updatePassword"
                  :disabled="isChangingPassword"
                  class="bg-blue-600 text-white px-6 py-2 rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  <i 
                    :class="isChangingPassword ? 'fa-solid fa-spinner fa-spin' : 'fa-solid fa-lock'" 
                    class="mr-2"
                  ></i>
                  {{ isChangingPassword ? 'Updating...' : 'Update Password' }}
                </button>
              </div>
            </div>
          </div>

          <!-- Account Actions -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200">
            <div class="p-6 border-b border-gray-200">
              <h3 class="text-lg font-semibold text-gray-900">Account Settings</h3>
            </div>
            <div class="p-6">
              <div class="space-y-4">
                <div class="flex items-center justify-between p-4 bg-red-50 rounded-lg border border-red-200">
                  <div>
                    <h4 class="text-sm font-medium text-red-900">Logout from Account</h4>
                    <p class="text-sm text-red-700 mt-1">Sign out from current session</p>
                  </div>
                  <button 
                    @click="logout"
                    class="bg-red-600 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-red-700 transition-colors"
                  >
                    <i class="fa-solid fa-sign-out-alt mr-2"></i>
                    Logout
                  </button>
                </div>
                <!-- <div class="flex items-center justify-between p-4 bg-yellow-50 rounded-lg border border-yellow-200">
                  <div>
                    <h4 class="text-sm font-medium text-yellow-900">Deactivate Account</h4>
                    <p class="text-sm text-yellow-700 mt-1">Temporarily disable your account</p>
                  </div>
                  <button 
                    @click="deactivateAccount"
                    class="bg-yellow-600 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-yellow-700 transition-colors"
                  >
                    <i class="fa-solid fa-pause mr-2"></i>
                    Deactivate
                  </button>
                </div> -->
              </div>
            </div>
            </div>
          </div>

          <!-- Wishlist Tab Content -->
          <div v-if="activeTab === 'wishlist'" class="space-y-6">
            <div class="bg-white rounded-lg shadow-sm border border-gray-200">
              <div class="p-6 border-b border-gray-200">
                <div class="flex items-center justify-between">
                  <h3 class="text-lg font-semibold text-gray-900">My Wishlist</h3>
                  <button 
                    v-if="wishlistStore.wishlistCount > 0"
                    @click="clearWishlist"
                    class="text-red-600 hover:text-red-700 text-sm font-medium"
                  >
                    <i class="fa-solid fa-trash mr-1"></i>
                    Clear All
                  </button>
                </div>
              </div>
              
              <!-- Loading State -->
              <div v-if="wishlistStore.isLoading" class="p-8 text-center">
                <i class="fa-solid fa-spinner fa-spin text-2xl text-blue-600 mb-4"></i>
                <p class="text-gray-600">Loading wishlist...</p>
              </div>
              
              <!-- Empty State -->
              <div v-else-if="wishlistStore.wishlistCount === 0" class="p-8 text-center">
                <i class="fa-regular fa-heart text-4xl text-gray-400 mb-4"></i>
                <h4 class="text-lg font-medium text-gray-900 mb-2">Your wishlist is empty</h4>
                <p class="text-gray-600 mb-4">Save items you love to your wishlist</p>
                <button 
                  @click="$router.push('/products')"
                  class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors"
                >
                  <i class="fa-solid fa-shopping-bag mr-2"></i>
                  Browse Products
                </button>
              </div>
              
              <!-- Wishlist Items -->
              <div v-else class="p-6">
                <div class="space-y-4">
                  <div 
                    v-for="item in wishlistStore.wishlistItems" 
                    :key="item.id"
                    class="border border-gray-200 rounded-lg p-6 hover:shadow-md transition-shadow cursor-pointer"
                    @click="viewProduct(item.product.id)"
                  >
                    <div class="flex space-x-6">
                      <!-- Product Image -->
                      <div class="flex-shrink-0">
                        <img 
                          :src="item.product.imageUrls?.[0] || '/placeholder-product.jpg'" 
                          :alt="item.product.name"
                          class="w-24 h-24 object-cover rounded-lg border border-gray-200"
                        >
                      </div>
                      
                      <!-- Product Info -->
                      <div class="flex-1 min-w-0">
                        <div class="flex items-start justify-between">
                          <div class="flex-1">
                            <h4 class="text-lg font-semibold text-gray-900 mb-1">{{ item.product.name }}</h4>
                            <p class="text-sm text-gray-600 mb-2">{{ item.product.brand }}</p>
                            
                            <!-- Price -->
                            <div class="flex items-center space-x-2 mb-3">
                              <span class="text-xl font-bold text-blue-600">Rp {{ formatPrice(item.product.price) }}</span>
                              <span 
                                v-if="item.product.originalPrice && item.product.originalPrice > item.product.price"
                                class="text-sm text-gray-500 line-through"
                              >
                                Rp {{ formatPrice(item.product.originalPrice) }}
                              </span>
                              <span 
                                v-if="item.product.originalPrice && item.product.originalPrice > item.product.price"
                                class="text-xs bg-red-100 text-red-800 px-2 py-1 rounded-full"
                              >
                                {{ Math.round(((item.product.originalPrice - item.product.price) / item.product.originalPrice) * 100) }}% OFF
                              </span>
                            </div>
                            
                            <!-- Stock Status -->
                            <div class="mb-4">
                              <span 
                                :class="getStockClass(item.product.stockQuantity)"
                                class="text-xs px-3 py-1 rounded-full font-medium"
                              >
                                {{ getStockText(item.product.stockQuantity) }}
                              </span>
                            </div>
                          </div>
                          
                          <!-- Remove Button -->
                          <button 
                            @click.stop="removeFromWishlist(item.product.id)"
                            class="text-gray-400 hover:text-red-600 transition-colors p-2"
                            title="Remove from wishlist"
                          >
                            <i class="fa-solid fa-heart-broken text-lg"></i>
                          </button>
                        </div>
                        
                        <!-- Action Buttons -->
                        <div class="flex space-x-3">
                          <button 
                            @click.stop="addToCart(item.product.id)"
                            :disabled="item.product.stockQuantity === 0"
                            class="flex-1 bg-blue-600 text-white py-2 px-4 rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                          >
                            <i class="fa-solid fa-shopping-cart mr-2"></i>
                            Add to Cart
                          </button>
                          <button 
                            @click.stop="viewProduct(item.product.id)"
                            class="px-6 py-2 border border-gray-300 text-gray-700 rounded-lg font-medium hover:bg-gray-50 transition-colors"
                          >
                            <i class="fa-solid fa-eye mr-2"></i>
                            View Details
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Orders Tab Content -->
          <div v-if="activeTab === 'orders'" class="space-y-6">
            <div class="bg-white rounded-lg shadow-sm border border-gray-200">
              <div class="p-6 border-b border-gray-200">
                <h3 class="text-lg font-semibold text-gray-900">Order History</h3>
              </div>
              
              <!-- Loading State -->
              <div v-if="orderStore.isLoading" class="p-8 text-center">
                <i class="fa-solid fa-spinner fa-spin text-2xl text-blue-600 mb-4"></i>
                <p class="text-gray-600">Loading orders...</p>
              </div>
              
              <!-- Empty State -->
              <div v-else-if="orderStore.orders.length === 0" class="p-8 text-center">
                <i class="fa-solid fa-shopping-bag text-4xl text-gray-400 mb-4"></i>
                <h4 class="text-lg font-medium text-gray-900 mb-2">No orders yet</h4>
                <p class="text-gray-600 mb-4">Start shopping to see your order history here</p>
                <button 
                  @click="$router.push('/products')"
                  class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors"
                >
                  <i class="fa-solid fa-shopping-bag mr-2"></i>
                  Browse Products
                </button>
              </div>
              
              <!-- Orders List -->
              <div v-else class="divide-y divide-gray-200">
                <div 
                  v-for="order in orderStore.orders" 
                  :key="order.id"
                  class="p-6 hover:bg-gray-50 transition-colors cursor-pointer"
                  @click="viewOrderDetail(order.id)"
                >
                  <div class="flex items-center justify-between mb-4">
                    <div>
                      <h4 class="text-lg font-semibold text-gray-900">Order #{{ order.orderNumber }}</h4>
                      <p class="text-sm text-gray-600">{{ formatDate(order.createdAt) }}</p>
                    </div>
                    <div class="text-right">
                      <span 
                        :class="getOrderStatusClass(order.status)"
                        class="px-3 py-1 rounded-full text-xs font-medium"
                      >
                        {{ getOrderStatusText(order.status) }}
                      </span>
                      <p class="text-lg font-bold text-gray-900 mt-1">Rp {{ formatPrice(order.totalAmount) }}</p>
                    </div>
                  </div>
                  
                  <!-- Order Summary -->
                  <div class="bg-gray-50 rounded-lg p-4 mb-4">
                    <div class="grid grid-cols-2 gap-4 text-sm">
                      <div>
                        <span class="text-gray-600">Payment Status:</span>
                        <span 
                          :class="order.paymentStatus === 'PAID' ? 'text-green-600' : 'text-yellow-600'"
                          class="ml-2 font-medium"
                        >
                          {{ order.paymentStatus }}
                        </span>
                      </div>
                      <div>
                        <span class="text-gray-600">Subtotal:</span>
                        <span class="ml-2 font-medium">Rp {{ formatPrice(order.subtotal) }}</span>
                      </div>
                      <div>
                        <span class="text-gray-600">Shipping:</span>
                        <span class="ml-2 font-medium">Rp {{ formatPrice(order.shippingCost) }}</span>
                      </div>
                      <div>
                        <span class="text-gray-600">Total:</span>
                        <span class="ml-2 font-medium text-blue-600">Rp {{ formatPrice(order.totalAmount) }}</span>
                      </div>
                    </div>
                  </div>
                  
                  <!-- Order Actions -->
                  <div class="flex items-center justify-between pt-4 border-t border-gray-100">
                    <div class="flex space-x-2">
                      <button 
                        @click.stop="viewOrderDetail(order.id)"
                        class="text-blue-600 hover:text-blue-700 text-sm font-medium"
                      >
                        <i class="fa-solid fa-eye mr-1"></i>
                        View Details
                      </button>
                      <button 
                        v-if="order.status === 'DELIVERED'"
                        @click.stop="reorderItems(order.id)"
                        class="text-green-600 hover:text-green-700 text-sm font-medium"
                      >
                        <i class="fa-solid fa-redo mr-1"></i>
                        Reorder
                      </button>
                    </div>
                    <div class="text-xs text-gray-500">
                      Order placed on {{ formatDate(order.createdAt) }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>

    <Footer/>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useWishlistStore } from '@/stores/wishlist'
import { useCartStore } from '@/stores/cart'
import { useAuthStore } from '@/stores/auth'
import { useOrderStore } from '@/stores/order'
import { useNotifications } from '@/composables/useNotifications'
import { apiService } from '@/services/api'
import UserNavbar from '../components/UserNavbar.vue'
import Footer from '../components/Footer.vue'

const router = useRouter()
const wishlistStore = useWishlistStore()
const cartStore = useCartStore()
const authStore = useAuthStore()
const orderStore = useOrderStore()
const { success, error, warning } = useNotifications()

// State
const isEditingPersonal = ref(false)
const isUpdatingProfile = ref(false)
const isChangingPassword = ref(false)
const activeTab = ref('profile')

// User data - will be populated from auth store
const user = reactive({
  name: '',
  email: '',
  phone: '',
  birthDate: '',
  address: '',
  avatar: '',
  membershipType: 'Member'
})

// Password form
const passwordForm = reactive({
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// Methods
const toggleEditPersonal = async () => {
  if (isEditingPersonal.value) {
    // Save profile changes
    try {
      isUpdatingProfile.value = true
      
      if (!authStore.user?.id) {
        error('Authentication Error', 'User not found. Please login again.')
        return
      }

      // Parse name into firstName and lastName
      const nameParts = user.name.trim().split(' ')
      const firstName = nameParts[0] || ''
      const lastName = nameParts.slice(1).join(' ') || ''

      const profileData = {
        firstName,
        lastName,
        phoneNumber: user.phone || '',
        address: user.address || ''
      }

      const updatedUser = await apiService.updateUserProfile(authStore.user.id, profileData)
      
      // Update auth store with new data
      authStore.user = {
        ...authStore.user!,
        firstName: updatedUser.firstName,
        lastName: updatedUser.lastName,
        phoneNumber: updatedUser.phoneNumber
      }

      // Reload user data
      loadUserData()
      
      success('Profile Updated', 'Personal information updated successfully!')
      isEditingPersonal.value = false
    } catch (err: any) {
      console.error('Error updating profile:', err)
      error('Update Failed', err.response?.data?.message || 'Failed to update profile. Please try again.')
    } finally {
      isUpdatingProfile.value = false
    }
  } else {
    isEditingPersonal.value = true
  }
}

const changePhoto = () => {
  // Implement photo change logic
  console.log('Change photo clicked')
  warning('Feature Coming Soon', 'Photo change functionality will be available soon')
}

const downloadData = () => {
  console.log('Download data clicked')
  warning('Feature Coming Soon', 'Data download functionality will be available soon')
}

const openSecurity = () => {
  console.log('Security clicked')
  warning('Feature Coming Soon', 'Security settings will be available soon')
}

const openNotifications = () => {
  console.log('Notifications clicked')
  warning('Feature Coming Soon', 'Notification settings will be available soon')
}

const updatePassword = async () => {
  if (!passwordForm.currentPassword || !passwordForm.newPassword || !passwordForm.confirmPassword) {
    warning('Validation Error', 'Please fill in all password fields')
    return
  }
  
  if (passwordForm.newPassword !== passwordForm.confirmPassword) {
    error('Validation Error', 'New passwords do not match')
    return
  }

  if (passwordForm.newPassword.length < 6) {
    warning('Validation Error', 'New password must be at least 6 characters long')
    return
  }
  
  try {
    isChangingPassword.value = true
    
    await apiService.changePassword(passwordForm.currentPassword, passwordForm.newPassword)
    
    success('Password Updated', 'Password updated successfully!')
    
    // Clear form
    passwordForm.currentPassword = ''
    passwordForm.newPassword = ''
    passwordForm.confirmPassword = ''
  } catch (err: any) {
    console.error('Error changing password:', err)
    error('Password Change Failed', err.response?.data?.message || 'Failed to change password. Please try again.')
  } finally {
    isChangingPassword.value = false
  }
}

const logout = () => {
  if (confirm('Are you sure you want to logout?')) {
    console.log('Logging out')
    success('Logout Successful', 'You have been logged out successfully!')
    router.push('/login')
  }
}

const deactivateAccount = () => {
  if (confirm('Are you sure you want to deactivate your account? This action can be reversed later.')) {
    console.log('Deactivating account')
    warning('Feature Coming Soon', 'Account deactivation functionality will be available soon')
  }
}

// Wishlist methods
const clearWishlist = async () => {
  if (confirm('Are you sure you want to clear your entire wishlist?')) {
    try {
      await wishlistStore.clearWishlist()
      success('Wishlist Cleared', 'Your wishlist has been cleared successfully!')
    } catch (err) {
      console.error('Error clearing wishlist:', err)
      error('Clear Failed', 'Failed to clear wishlist. Please try again.')
    }
  }
}

const addToCart = async (productId: number) => {
  try {
    if (!authStore.isAuthenticated) {
      router.push('/login')
      return
    }
    
    await cartStore.addToCart(productId, 1)
    success('Added to Cart', 'Product has been added to your cart!')
  } catch (err) {
    console.error('Error adding to cart:', err)
    error('Add to Cart Failed', 'Failed to add product to cart. Please try again.')
  }
}

const removeFromWishlist = async (productId: number) => {
  try {
    await wishlistStore.removeFromWishlist(productId)
  } catch (err) {
    console.error('Error removing from wishlist:', err)
    error('Remove Failed', 'Failed to remove product from wishlist. Please try again.')
  }
}

const viewProduct = (productId: number) => {
  router.push(`/product/${productId}`)
}

// Utility methods
const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const getStockClass = (stock: number) => {
  if (stock > 10) return 'bg-green-100 text-green-800'
  if (stock > 5) return 'bg-yellow-100 text-yellow-800'
  return 'bg-red-100 text-red-800'
}

const getStockText = (stock: number) => {
  if (stock > 10) return `Stock: ${stock}`
  if (stock > 5) return `Low Stock: ${stock}`
  if (stock > 0) return `Only ${stock} left`
  return 'Out of Stock'
}

// Order methods
const viewOrderDetail = (orderId: number) => {
  router.push(`/order/${orderId}`)
}

const reorderItems = async (orderId: number) => {
  try {
    // Implementation for reordering items
    console.log('Reordering items from order:', orderId)
    warning('Feature Coming Soon', 'Reorder functionality will be available soon')
  } catch (err) {
    console.error('Error reordering items:', err)
    error('Reorder Failed', 'Failed to reorder items. Please try again.')
  }
}

const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleDateString('id-ID', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

const getOrderStatusClass = (status: string) => {
  switch (status) {
    case 'PENDING':
      return 'bg-yellow-100 text-yellow-800'
    case 'CONFIRMED':
      return 'bg-blue-100 text-blue-800'
    case 'SHIPPED':
      return 'bg-purple-100 text-purple-800'
    case 'DELIVERED':
      return 'bg-green-100 text-green-800'
    case 'CANCELLED':
      return 'bg-red-100 text-red-800'
    default:
      return 'bg-gray-100 text-gray-800'
  }
}

const getOrderStatusText = (status: string) => {
  switch (status) {
    case 'PENDING':
      return 'Pending'
    case 'CONFIRMED':
      return 'Confirmed'
    case 'SHIPPED':
      return 'Shipped'
    case 'DELIVERED':
      return 'Delivered'
    case 'CANCELLED':
      return 'Cancelled'
    default:
      return status
  }
}

// Load user data from auth store
const loadUserData = async () => {
  if (authStore.user) {
    user.name = `${authStore.user.firstName} ${authStore.user.lastName}`.trim() || ''
    user.email = authStore.user.email || ''
    user.phone = authStore.user.phoneNumber || ''
    
    // Load full user profile to get address
    try {
      const fullProfile = await apiService.getUserProfile(authStore.user.id)
      user.address = fullProfile.address || ''
    } catch (err) {
      console.error('Error loading user profile:', err)
      user.address = ''
    }
  }
}

// Initialize on mount
onMounted(async () => {
  // Load user data from auth store
  loadUserData()
  
  // Load wishlist and orders
  await Promise.all([
    wishlistStore.loadWishlist(),
    orderStore.fetchUserOrders()
  ])
})
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}
</style>

<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:39:09 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
60,531 / 200K tokens used (30%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Perfect! I can see that the UserProfileView already has the address field implemented and it's properly integrated with the API. Now let me check the current state of the backend to ensure the migration has been applied. Let me run the backend to see if there are any migration issues:

<execute_command>
<command>cd autowarehouse-backend && mvn quarkus:dev</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'cd autowarehouse-backend && mvn quarkus:dev'] Result:
Command executed.
Output:
At line:1 char:26
+ cd autowarehouse-backend && mvn quarkus:dev
+                          ~~
The token '&&' is not a valid statement separator in this version.
    + CategoryInfo          : ParserError: (:) [], ParentContainsErrorRecordException
    + FullyQualifiedErrorId : InvalidEndOfLine
<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:39:21 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
77,829 / 200K tokens used (39%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

I need to use the correct Windows command syntax. Let me navigate to the backend directory first:

<execute_command>
<command>cd autowarehouse-backend</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'cd autowarehouse-backend'] Result:
Command executed.
Output:
The command's output could not be captured due to some technical issue, however it has been executed successfully. Here's the current terminal's content to help you get the command's output:

PS E:\Ecomindo\Autowarehouse\autowarehouse-backend> cd "e:\Ecomindo\Autowarehouse"
PS E:\Ecomindo\Autowarehouse> ^C
PS E:\Ecomindo\Autowarehouse> del autowarehouse-backend\src\main\resources\db\migration\V1_1__Add_address_to_users.sql
del : Cannot find path 'E:\Ecomindo\Autowarehouse\autowarehouse-backend\src\main\resources\db\ 
migration\V1_1__Add_address_to_users.sql' because it does not exist.
At line:1 char:1
+ del autowarehouse-backend\src\main\resources\db\migration\V1_1__Add_a ...
+ ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    + CategoryInfo          : ObjectNotFound: (E:\Ecomindo\Aut...ss_to_users.sql:String) [Rem  
   ove-Item], ItemNotFoundException
    + FullyQualifiedErrorId : PathNotFound,Microsoft.PowerShell.Commands.RemoveItemCommand     

PS E:\Ecomindo\Autowarehouse> dir autowarehouse-backend\src\main\resources\db\migration\V1_1*  
PS E:\Ecomindo\Autowarehouse> cd autowarehouse-backend && mvn quarkus:dev
At line:1 char:26
+ cd autowarehouse-backend && mvn quarkus:dev
+                          ~~
The token '&&' is not a valid statement separator in this version.
    + CategoryInfo          : ParserError: (:) [], ParentContainsErrorRecordException
    + FullyQualifiedErrorId : InvalidEndOfLine

PS E:\Ecomindo\Autowarehouse> cd autowarehouse-backend
<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:39:27 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
88,572 / 200K tokens used (44%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

I can see from the terminal output that the V1_1__Add_address_to_users.sql file doesn't exist on disk (which is good), and we're now in the backend directory. Let me start the backend:

<execute_command>
<command>mvn quarkus:dev</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'mvn quarkus:dev'] Result:
Command executed.
Output:
[INFO] Scanning for projects...
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-m
metadata.xml
Downloading from central: https://repo.maven.apache.org/maven2/org/codehaus/mojo/maven-metadata
a.xml
Progress (1): 1.4/14 kBProgress (2): 14 kB | 21 kB        Downloaded from central: https://repo.maven.apache.org/maven2/org/codehaus/mojo/maven-metadata.
.xml (21 kB at 81 kB/s)
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-me
etadata.xml (14 kB at 56 kB/s)
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.792 s
[INFO] Finished at: 2025-07-16T19:39:34+07:00
[INFO] ------------------------------------------------------------------------
[ERROR] No plugin found for prefix 'quarkus' in the current project and in the plugin groups [o
org.apache.maven.plugins, org.codehaus.mojo] available from the repositories [local (C:\Users\ph
hili\.m2\repository), central (https://repo.maven.apache.org/maven2)] -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following
g articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/NoPluginFoundForPrefixExcepti
ion
<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:39:38 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
90,192 / 200K tokens used (45%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Let me try using the correct Maven command for Quarkus:

<execute_command>
<command>mvn compile quarkus:dev</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'mvn compile quarkus:dev'] Result:
Command executed.
Output:
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.068 s
[INFO] Finished at: 2025-07-16T19:39:44+07:00
[INFO] ------------------------------------------------------------------------
[ERROR] The goal you specified requires a project to execute but there is no POM in this direct
tory (E:\Ecomindo\Autowarehouse). Please verify you invoked Maven from the correct directory. ->
>
 [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following
g articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MissingProjectException
<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:39:48 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
92,101 / 200K tokens used (46%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

I see the issue - I'm not in the correct directory. Let me navigate to the backend directory properly:

<execute_command>
<command>cd autowarehouse-backend</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'cd autowarehouse-backend'] Result:
Command executed.
Output:
The command's output could not be captured due to some technical issue, however it has been executed successfully. Here's the current terminal's content to help you get the command's output:

PS E:\Ecomindo\Autowarehouse\autowarehouse-backend> cd "e:\Ecomindo\Autowarehouse"
PS E:\Ecomindo\Autowarehouse> ^C
PS E:\Ecomindo\Autowarehouse> mvn quarkus:dev
[INFO] Scanning for projects...
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-metadata.xml
Downloading from central: https://repo.maven.apache.org/maven2/org/codehaus/mojo/maven-metadata.xml
Downloaded from central: https://repo.maven.apache.org/maven2/org/codehaus/mojo/maven-metadata.xml (21 kB at 81 kB/s)
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-metadata.xml (14 kB at 56 kB/s)
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.792 s
[INFO] Finished at: 2025-07-16T19:39:34+07:00
[INFO] ------------------------------------------------------------------------
[ERROR] No plugin found for prefix 'quarkus' in the current project and in the plugin groups [org.apache.maven.plugins, org.codehaus.mojo] available from the repositories [local (C:\Users\phili\.m2\repository), central (https://repo.maven.apache.org/maven2)] -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/NoPluginFoundForPrefixException
PS E:\Ecomindo\Autowarehouse> mvn compile quarkus:dev
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.068 s
[INFO] Finished at: 2025-07-16T19:39:44+07:00
[INFO] ------------------------------------------------------------------------
[ERROR] The goal you specified requires a project to execute but there is no POM in this directory (E:\Ecomindo\Autowarehouse). Please verify you invoked Maven from the correct directory. ->
 [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MissingProjectException      
PS E:\Ecomindo\Autowarehouse> cd autowarehouse-backend
<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:39:55 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
93,799 / 200K tokens used (47%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Now let me try to start the Quarkus application:

<execute_command>
<command>mvn compile quarkus:dev</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'mvn compile quarkus:dev'] Result:
Command executed.
Output:
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.057 s
[INFO] Finished at: 2025-07-16T19:40:01+07:00
[INFO] ------------------------------------------------------------------------
[ERROR] The goal you specified requires a project to execute but there is no POM in this direct
tory (E:\Ecomindo\Autowarehouse). Please verify you invoked Maven from the correct directory. ->
>
 [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following
g articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MissingProjectException
<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:40:04 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
95,786 / 200K tokens used (48%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Let me check the current directory and navigate properly:

<execute_command>
<command>pwd</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'pwd'] Result:
Command executed.
Output:
PathX
----
E:\Ecomindo\Autowarehouse
<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:40:11 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
97,769 / 200K tokens used (49%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

I'm in the root directory. Let me navigate to the backend directory:

<execute_command>
<command>cd autowarehouse-backend</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'cd autowarehouse-backend'] Result:
Command executed.
Output:
The command's output could not be captured due to some technical issue, however it has been executed successfully. Here's the current terminal's content to help you get the command's output:

PS E:\Ecomindo\Autowarehouse\autowarehouse-backend> cd "e:\Ecomindo\Autowarehouse"
PS E:\Ecomindo\Autowarehouse> ^C
PS E:\Ecomindo\Autowarehouse> mvn compile quarkus:dev
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.057 s
[INFO] Finished at: 2025-07-16T19:40:01+07:00
[INFO] ------------------------------------------------------------------------
[ERROR] The goal you specified requires a project to execute but there is no POM in this directory (E:\Ecomindo\Autowarehouse). Please verify you invoked Maven from the correct directory. ->
 [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MissingProjectException      
PS E:\Ecomindo\Autowarehouse> pwd

Path
----
E:\Ecomindo\Autowarehouse


PS E:\Ecomindo\Autowarehouse> cd autowarehouse-backend
<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:40:16 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
98,973 / 200K tokens used (49%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Now let me try to start the Quarkus application:

<execute_command>
<command>mvn compile quarkus:dev</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'mvn compile quarkus:dev'] Result:
Command executed.
Output:
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.058 s
[INFO] Finished at: 2025-07-16T19:40:22+07:00
[INFO] ------------------------------------------------------------------------
[ERROR] The goal you specified requires a project to execute but there is no POM in this direct
tory (E:\Ecomindo\Autowarehouse). Please verify you invoked Maven from the correct directory. ->
>
 [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following
g articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MissingProjectException
<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql

# Current Time
7/16/2025, 7:40:25 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
100,320 / 200K tokens used (50%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

It seems the terminal is still in the root directory. Let me check if there's a start script available:

<execute_command>
<command>dir</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[TASK RESUMPTION] This task was interrupted 1 minute ago. It may or may not be complete, so please reassess the task context. Be aware that the project state may have changed since then. The current working directory is now 'e:/Ecomindo/Autowarehouse'. If the task has not been completed, retry the last step before interruption and proceed with completing the task.

Note: If you previously attempted a tool use that the user did not provide a result for, you should assume the tool use was not successful and assess whether you should retry. If the last tool was a browser_action, the browser has been closed and you must launch a new browser if needed.
<environment_details>
# VSCode Visible Files
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# Current Time
7/16/2025, 7:42:14 PM (Asia/Bangkok, UTC+7:00)

# Current Working Directory (e:/Ecomindo/Autowarehouse) Files
.gitignore
DEVELOPMENT_GUIDE.md
prd.md
Prompt-Chat GPT.txt
Prompt-Cline AI.txt
Prompt-UX Pilot.txt
README.md
start-dev.bat
start-dev.sh
test-admin-users.sql
ui-design.md
autowarehouse-backend/
autowarehouse-backend/pom.xml
autowarehouse-backend/src/
autowarehouse-backend/src/main/
autowarehouse-backend/src/main/java/
autowarehouse-backend/src/main/resources/
autowarehouse-backend/src/test/
autowarehouse-backend/src/test/java/
autowarehouse-backend/target/
autowarehouse-backend/target/autowarehouse-backend-dev.jar
autowarehouse-backend/target/build-analytics-event.json
autowarehouse-backend/target/build-metrics.json
autowarehouse-backend/target/classes/
autowarehouse-backend/target/classes/application.properties
autowarehouse-backend/target/classes/com/
autowarehouse-backend/target/classes/db/
autowarehouse-backend/target/classes/META-INF/
autowarehouse-backend/target/generated-sources/
autowarehouse-backend/target/generated-sources/annotations/
autowarehouse-backend/target/generated-test-sources/
autowarehouse-backend/target/generated-test-sources/test-annotations/
autowarehouse-backend/target/maven-status/
autowarehouse-backend/target/maven-status/maven-compiler-plugin/
autowarehouse-backend/target/quarkus/
autowarehouse-backend/target/quarkus/bootstrap/
autowarehouse-backend/target/test-classes/
autowarehouse-frontend/
autowarehouse-frontend/firebase-setup.md
autowarehouse-frontend/index.html
autowarehouse-frontend/package-lock.json
autowarehouse-frontend/package.json
autowarehouse-frontend/postcss.config.js
autowarehouse-frontend/tailwind.config.js
autowarehouse-frontend/tsconfig.json
autowarehouse-frontend/tsconfig.node.json
autowarehouse-frontend/vite.config.ts
autowarehouse-frontend/src/
autowarehouse-frontend/src/App.vue
autowarehouse-frontend/src/main.ts
autowarehouse-frontend/src/style.css
autowarehouse-frontend/src/vite-env.d.ts
autowarehouse-frontend/src/components/
autowarehouse-frontend/src/components/AdminNavbar.vue
autowarehouse-frontend/src/components/Footer.vue
autowarehouse-frontend/src/components/GuestNavbar.vue
autowarehouse-frontend/src/components/NotificationContainer.vue
autowarehouse-frontend/src/components/OrderStatusTimeline.vue
autowarehouse-frontend/src/components/ProductCard.vue
autowarehouse-frontend/src/components/ProductListItem.vue
autowarehouse-frontend/src/components/UserNavbar.vue
autowarehouse-frontend/src/components/admin/
autowarehouse-frontend/src/components/forms/
autowarehouse-frontend/src/composables/
autowarehouse-frontend/src/composables/useCartPersistence.ts
autowarehouse-frontend/src/composables/useNotifications.ts
autowarehouse-frontend/src/router/
autowarehouse-frontend/src/services/
autowarehouse-frontend/src/stores/
autowarehouse-frontend/src/utils/
autowarehouse-frontend/src/views/
chat-history/
chat-history/cline_task_jul-3-2025_11-38-35-am.md
chat-history/cline_task_jul-7-2025_3-50-19-pm.md
chat-history/cline_task_jul-7-2025_10-22-14-am.md
chat-history/cline_task_jul-7-2025_12-19-08-pm.md
chat-history/cline_task_jul-8-2025_1-11-45-pm.md
chat-history/cline_task_jul-8-2025_1-13-32-pm.md
chat-history/cline_task_jul-8-2025_1-30-18-pm.md
chat-history/cline_task_jul-8-2025_1-44-27-pm.md
chat-history/cline_task_jul-8-2025_2-46-10-pm.md
chat-history/cline_task_jul-8-2025_10-50-24-am.md
chat-history/cline_task_jul-8-2025_11-12-09-am.md
chat-history/cline_task_jul-8-2025_11-22-47-am.md
chat-history/cline_task_jul-8-2025_11-32-43-am.md
chat-history/cline_task_jul-8-2025_12-12-20-pm.md
chat-history/cline_task_jul-8-2025_12-28-33-pm.md
chat-history/cline_task_jul-8-2025_12-31-04-pm.md
chat-history/cline_task_jul-8-2025_12-51-57-pm.md
chat-history/cline_task_jul-11-2025_4-09-51-pm.md
chat-history/cline_task_jul-11-2025_4-26-57-pm.md
chat-history/cline_task_jul-11-2025_5-40-51-pm.md
chat-history/cline_task_jul-11-2025_5-48-06-pm.md
chat-history/cline_task_jul-11-2025_5-51-51-pm.md
chat-history/cline_task_jul-11-2025_6-13-21-pm.md
chat-history/cline_task_jul-11-2025_6-47-14-pm.md
chat-history/cline_task_jul-14-2025_2-34-30-pm.md
chat-history/cline_task_jul-14-2025_7-04-07-pm.md
chat-history/cline_task_jul-14-2025_7-16-37-pm.md
chat-history/cline_task_jul-14-2025_7-49-20-pm.md
memory-bank/
memory-bank/architecture.md
memory-bank/backend-implementation.md
memory-bank/current-implementation-status.md
memory-bank/frontend-backend-integration.md
memory-bank/frontend-implementation.md
memory-bank/glossary.md
memory-bank/login-credentials-summary.md
memory-bank/project-overview.md
memory-bank/project-summary.md
memory-bank/README.md
memory-bank/technical-stack.md
memory-bank/test-credentials.md
memory-bank/user-stories.md
memory-bank/user-test-data.md
memory-bank/api/
memory-bank/api/endpoints.md
memory-bank/api/rest-endpoints.md
memory-bank/database/
memory-bank/database/schema.md
memory-bank/features/
memory-bank/features/auction-system.md
memory-bank/features/notifications.md
memory-bank/features/order-management.md
memory-bank/features/product-catalog.md
memory-bank/features/shopping-cart.md
memory-bank/features/user-management.md
memory-bank/ui-design/
memory-bank/ui-design/design-system.md
tasks-readme/
tasks-readme/customer-products-module-tasks.md
tasks-readme/order-history-module-tasks.md
tasks-readme/product-management-integration.md
tasks-readme/product-management-missing-tasks.md
tasks-readme/profile-module-adjustment-tasks.md
tasks-readme/shopping-cart-module-tasks.md
tasks-readme/wishlist-module-tasks.md
ui-design/
ui-design/admin-auction (1).html
ui-design/admin-auction (1).png
ui-design/admin-auction (2).html
ui-design/admin-auction (2).png
ui-design/admin-category-management (1).html
ui-design/admin-category-management (1).png
ui-design/admin-category-management (2).html
ui-design/admin-category-management (2).png
ui-design/admin-customer-service.html
ui-design/admin-customer-service.png
ui-design/admin-dashboard.html
ui-design/admin-dashboard.png
ui-design/admin-notif.html
ui-design/admin-notif.png
ui-design/admin-order-management (1).html
ui-design/admin-order-management (1).png
ui-design/admin-order-management (2).html
ui-design/admin-order-management (2).png
ui-design/admin-product-management (1).html
ui-design/admin-product-management (1).png
ui-design/admin-product-management (2).html
ui-design/admin-product-management (2).png
ui-design/home - Reference UI.html
ui-design/home.png
ui-design/Login (1) - Reference UI.html
ui-design/Login (1).png
ui-design/Login (2) - Reference UI.html
ui-design/Login (2).png
ui-design/Login (3) - Reference UI.html
ui-design/Login (3).png
ui-design/Login (4) - Reference UI.html
ui-design/Login (4).png
ui-design/order-history (1).html
ui-design/order-history (1).png
ui-design/order-history (2).html
ui-design/order-history (2).png
ui-design/order-history (3).html
ui-design/order-history (3).png
ui-design/product-detail (1) - Reference UI.html
ui-design/product-detail (1).png
ui-design/product-detail (2) - Reference UI.html
ui-design/product-detail (2).png
ui-design/Shopping Cart (1) - Reference UI.html
ui-design/Shopping Cart (2) - Reference UI.html
ui-design/Shoppping-Cart (1).png
ui-design/Shoppping-Cart (2).png
ui-design/user-custome-service.png
ui-design/user-customer-service.html
ui-design/user-live-auction (1).html
ui-design/user-live-auction (1).png
ui-design/user-live-auction (2).html
ui-design/user-live-auction (2).png
ui-design/user-live-auction (3).html
ui-design/user-live-auction (3).png
ui-design/user-live-auction (4).html
ui-design/user-live-auction (4).png
ui-design/user-notification.html
ui-design/user-notification.png
ui-design/user-profile.html
ui-design/user-profile.png
ui-design/Wishlist - Reference UI.html
ui-design/Wishlist.png

(File list truncated. Use list_files on specific subdirectories if you need to explore further.)

# Git Remote URLs
origin: https://github.com/S1rent/Autowarehouse.git

# Context Window Usage
101,903 / 200K tokens used (51%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

I can see there are start scripts available. Let me use the start-dev.bat script to start the backend:

<execute_command>
<command>start-dev.bat</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'start-dev.bat'] Result:
Command executed.
Output:
start-dev.bat : The term 'start-dev.bat' is not recognized as the name of a cmdlet, function,
script file, or operable program. Check the spelling of the name, or if a path was included,
verify that the path is correct and try again.
At line:1 char:1
+ start-dev.bat
+ ~~~~~~~~~~~~~
    + CategoryInfo          : ObjectNotFound: (start-dev.bat:String) [], CommandNotFoundExcep
   tion
    + FullyQualifiedErrorId : CommandNotFoundException
 Suggestion [3,General]: The command start-dev.bat was not found, but does exist in the current location. Windows PowerShell does not load commands from the current location by default. If you trust this command, instead type: ".\start-dev.bat". See "get-help about_Command_Precedence" for more details.
<environment_details>
# VSCode Visible Files
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# Current Time
7/16/2025, 7:42:24 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
106,109 / 200K tokens used (53%)

# Current Mode
ACT MODE
</environment_details>
[TASK RESUMPTION] This task was interrupted just now. It may or may not be complete, so please reassess the task context. Be aware that the project state may have changed since then. The current working directory is now 'e:/Ecomindo/Autowarehouse'. If the task has not been completed, retry the last step before interruption and proceed with completing the task.

Note: If you previously attempted a tool use that the user did not provide a result for, you should assume the tool use was not successful and assess whether you should retry. If the last tool was a browser_action, the browser has been closed and you must launch a new browser if needed.

IMPORTANT: If the last tool use was a replace_in_file or write_to_file that was interrupted, the file was reverted back to its original state before the interrupted edit, and you do NOT need to re-read the file as you already have its up-to-date contents.
New instructions for task continuation:
<user_message>
dont use star dev its not working, do manual
</user_message>
<environment_details>
# VSCode Visible Files
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# Current Time
7/16/2025, 7:42:50 PM (Asia/Bangkok, UTC+7:00)

# Current Working Directory (e:/Ecomindo/Autowarehouse) Files
.gitignore
DEVELOPMENT_GUIDE.md
prd.md
Prompt-Chat GPT.txt
Prompt-Cline AI.txt
Prompt-UX Pilot.txt
README.md
start-dev.bat
start-dev.sh
test-admin-users.sql
ui-design.md
autowarehouse-backend/
autowarehouse-backend/pom.xml
autowarehouse-backend/src/
autowarehouse-backend/src/main/
autowarehouse-backend/src/main/java/
autowarehouse-backend/src/main/resources/
autowarehouse-backend/src/test/
autowarehouse-backend/src/test/java/
autowarehouse-backend/target/
autowarehouse-backend/target/autowarehouse-backend-dev.jar
autowarehouse-backend/target/build-analytics-event.json
autowarehouse-backend/target/build-metrics.json
autowarehouse-backend/target/classes/
autowarehouse-backend/target/classes/application.properties
autowarehouse-backend/target/classes/com/
autowarehouse-backend/target/classes/db/
autowarehouse-backend/target/classes/META-INF/
autowarehouse-backend/target/generated-sources/
autowarehouse-backend/target/generated-sources/annotations/
autowarehouse-backend/target/generated-test-sources/
autowarehouse-backend/target/generated-test-sources/test-annotations/
autowarehouse-backend/target/maven-status/
autowarehouse-backend/target/maven-status/maven-compiler-plugin/
autowarehouse-backend/target/quarkus/
autowarehouse-backend/target/quarkus/bootstrap/
autowarehouse-backend/target/test-classes/
autowarehouse-frontend/
autowarehouse-frontend/firebase-setup.md
autowarehouse-frontend/index.html
autowarehouse-frontend/package-lock.json
autowarehouse-frontend/package.json
autowarehouse-frontend/postcss.config.js
autowarehouse-frontend/tailwind.config.js
autowarehouse-frontend/tsconfig.json
autowarehouse-frontend/tsconfig.node.json
autowarehouse-frontend/vite.config.ts
autowarehouse-frontend/src/
autowarehouse-frontend/src/App.vue
autowarehouse-frontend/src/main.ts
autowarehouse-frontend/src/style.css
autowarehouse-frontend/src/vite-env.d.ts
autowarehouse-frontend/src/components/
autowarehouse-frontend/src/components/AdminNavbar.vue
autowarehouse-frontend/src/components/Footer.vue
autowarehouse-frontend/src/components/GuestNavbar.vue
autowarehouse-frontend/src/components/NotificationContainer.vue
autowarehouse-frontend/src/components/OrderStatusTimeline.vue
autowarehouse-frontend/src/components/ProductCard.vue
autowarehouse-frontend/src/components/ProductListItem.vue
autowarehouse-frontend/src/components/UserNavbar.vue
autowarehouse-frontend/src/components/admin/
autowarehouse-frontend/src/components/forms/
autowarehouse-frontend/src/composables/
autowarehouse-frontend/src/composables/useCartPersistence.ts
autowarehouse-frontend/src/composables/useNotifications.ts
autowarehouse-frontend/src/router/
autowarehouse-frontend/src/services/
autowarehouse-frontend/src/stores/
autowarehouse-frontend/src/utils/
autowarehouse-frontend/src/views/
chat-history/
chat-history/cline_task_jul-3-2025_11-38-35-am.md
chat-history/cline_task_jul-7-2025_3-50-19-pm.md
chat-history/cline_task_jul-7-2025_10-22-14-am.md
chat-history/cline_task_jul-7-2025_12-19-08-pm.md
chat-history/cline_task_jul-8-2025_1-11-45-pm.md
chat-history/cline_task_jul-8-2025_1-13-32-pm.md
chat-history/cline_task_jul-8-2025_1-30-18-pm.md
chat-history/cline_task_jul-8-2025_1-44-27-pm.md
chat-history/cline_task_jul-8-2025_2-46-10-pm.md
chat-history/cline_task_jul-8-2025_10-50-24-am.md
chat-history/cline_task_jul-8-2025_11-12-09-am.md
chat-history/cline_task_jul-8-2025_11-22-47-am.md
chat-history/cline_task_jul-8-2025_11-32-43-am.md
chat-history/cline_task_jul-8-2025_12-12-20-pm.md
chat-history/cline_task_jul-8-2025_12-28-33-pm.md
chat-history/cline_task_jul-8-2025_12-31-04-pm.md
chat-history/cline_task_jul-8-2025_12-51-57-pm.md
chat-history/cline_task_jul-11-2025_4-09-51-pm.md
chat-history/cline_task_jul-11-2025_4-26-57-pm.md
chat-history/cline_task_jul-11-2025_5-40-51-pm.md
chat-history/cline_task_jul-11-2025_5-48-06-pm.md
chat-history/cline_task_jul-11-2025_5-51-51-pm.md
chat-history/cline_task_jul-11-2025_6-13-21-pm.md
chat-history/cline_task_jul-11-2025_6-47-14-pm.md
chat-history/cline_task_jul-14-2025_2-34-30-pm.md
chat-history/cline_task_jul-14-2025_7-04-07-pm.md
chat-history/cline_task_jul-14-2025_7-16-37-pm.md
chat-history/cline_task_jul-14-2025_7-49-20-pm.md
memory-bank/
memory-bank/architecture.md
memory-bank/backend-implementation.md
memory-bank/current-implementation-status.md
memory-bank/frontend-backend-integration.md
memory-bank/frontend-implementation.md
memory-bank/glossary.md
memory-bank/login-credentials-summary.md
memory-bank/project-overview.md
memory-bank/project-summary.md
memory-bank/README.md
memory-bank/technical-stack.md
memory-bank/test-credentials.md
memory-bank/user-stories.md
memory-bank/user-test-data.md
memory-bank/api/
memory-bank/api/endpoints.md
memory-bank/api/rest-endpoints.md
memory-bank/database/
memory-bank/database/schema.md
memory-bank/features/
memory-bank/features/auction-system.md
memory-bank/features/notifications.md
memory-bank/features/order-management.md
memory-bank/features/product-catalog.md
memory-bank/features/shopping-cart.md
memory-bank/features/user-management.md
memory-bank/ui-design/
memory-bank/ui-design/design-system.md
tasks-readme/
tasks-readme/customer-products-module-tasks.md
tasks-readme/order-history-module-tasks.md
tasks-readme/product-management-integration.md
tasks-readme/product-management-missing-tasks.md
tasks-readme/profile-module-adjustment-tasks.md
tasks-readme/shopping-cart-module-tasks.md
tasks-readme/wishlist-module-tasks.md
ui-design/
ui-design/admin-auction (1).html
ui-design/admin-auction (1).png
ui-design/admin-auction (2).html
ui-design/admin-auction (2).png
ui-design/admin-category-management (1).html
ui-design/admin-category-management (1).png
ui-design/admin-category-management (2).html
ui-design/admin-category-management (2).png
ui-design/admin-customer-service.html
ui-design/admin-customer-service.png
ui-design/admin-dashboard.html
ui-design/admin-dashboard.png
ui-design/admin-notif.html
ui-design/admin-notif.png
ui-design/admin-order-management (1).html
ui-design/admin-order-management (1).png
ui-design/admin-order-management (2).html
ui-design/admin-order-management (2).png
ui-design/admin-product-management (1).html
ui-design/admin-product-management (1).png
ui-design/admin-product-management (2).html
ui-design/admin-product-management (2).png
ui-design/home - Reference UI.html
ui-design/home.png
ui-design/Login (1) - Reference UI.html
ui-design/Login (1).png
ui-design/Login (2) - Reference UI.html
ui-design/Login (2).png
ui-design/Login (3) - Reference UI.html
ui-design/Login (3).png
ui-design/Login (4) - Reference UI.html
ui-design/Login (4).png
ui-design/order-history (1).html
ui-design/order-history (1).png
ui-design/order-history (2).html
ui-design/order-history (2).png
ui-design/order-history (3).html
ui-design/order-history (3).png
ui-design/product-detail (1) - Reference UI.html
ui-design/product-detail (1).png
ui-design/product-detail (2) - Reference UI.html
ui-design/product-detail (2).png
ui-design/Shopping Cart (1) - Reference UI.html
ui-design/Shopping Cart (2) - Reference UI.html
ui-design/Shoppping-Cart (1).png
ui-design/Shoppping-Cart (2).png
ui-design/user-custome-service.png
ui-design/user-customer-service.html
ui-design/user-live-auction (1).html
ui-design/user-live-auction (1).png
ui-design/user-live-auction (2).html
ui-design/user-live-auction (2).png
ui-design/user-live-auction (3).html
ui-design/user-live-auction (3).png
ui-design/user-live-auction (4).html
ui-design/user-live-auction (4).png
ui-design/user-notification.html
ui-design/user-notification.png
ui-design/user-profile.html
ui-design/user-profile.png
ui-design/Wishlist - Reference UI.html
ui-design/Wishlist.png

(File list truncated. Use list_files on specific subdirectories if you need to explore further.)

# Git Remote URLs
origin: https://github.com/S1rent/Autowarehouse.git

# Context Window Usage
106,109 / 200K tokens used (53%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

I understand. Let me start the backend manually. First, let me navigate to the backend directory and start it with Maven:

<execute_command>
<command>cd autowarehouse-backend</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'cd autowarehouse-backend'] Result:
Command executed.
Output:
The command's output could not be captured due to some technical issue, however it has been executed successfully. Here's the current terminal's content to help you get the command's output:

PS E:\Ecomindo\Autowarehouse> start-dev.bat
start-dev.bat : The term 'start-dev.bat' is not recognized as the name of a cmdlet, function, 
script file, or operable program. Check the spelling of the name, or if a path was included, 
verify that the path is correct and try again.
At line:1 char:1
+ start-dev.bat
+ ~~~~~~~~~~~~~
    + CategoryInfo          : ObjectNotFound: (start-dev.bat:String) [], CommandNotFoundExcep 
   tion
    + FullyQualifiedErrorId : CommandNotFoundException
 

Suggestion [3,General]: The command start-dev.bat was not found, but does exist in the current location. Windows PowerShell does not load commands from the current location by default. If you trust this command, instead type: ".\start-dev.bat". See "get-help about_Command_Precedence" for more details.
PS E:\Ecomindo\Autowarehouse> cd autowarehouse-backend
<environment_details>
# VSCode Visible Files
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# Current Time
7/16/2025, 7:42:57 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
114,558 / 200K tokens used (57%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Now let me start the Quarkus backend manually:

<execute_command>
<command>mvn compile quarkus:dev</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'mvn compile quarkus:dev'] Result:
Command executed.
Output:
[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.064 s
[INFO] Finished at: 2025-07-16T19:43:02+07:00
[INFO] ------------------------------------------------------------------------
[ERROR] The goal you specified requires a project to execute but there is no POM in this direct
tory (E:\Ecomindo\Autowarehouse). Please verify you invoked Maven from the correct directory. ->
>
 [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following
g articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MissingProjectException
<environment_details>
# VSCode Visible Files
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# Current Time
7/16/2025, 7:43:06 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
118,871 / 200K tokens used (59%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Let me check the current directory and navigate properly:

<execute_command>
<command>pwd</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'pwd'] Result:
Command executed.
Output:
Path
----
E:\Ecomindo\Autowarehouse
<environment_details>
# VSCode Visible Files
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# Current Time
7/16/2025, 7:43:11 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
120,416 / 200K tokens used (60%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

I'm still in the root directory. Let me navigate to the backend directory and start it:

<execute_command>
<command>cd autowarehouse-backend && mvn compile quarkus:dev</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'cd autowarehouse-backend && mvn compile quarkus:dev'] Result:
Command executed.
Output:
At line:1 char:26
+ cd autowarehouse-backend && mvn compile quarkus:dev
+                          ~~
The token '&&' is not a valid statement separator in this version.
    + CategoryInfo          : ParserError: (:) [], ParentContainsErrorRecordException
    + FullyQualifiedErrorId : InvalidEndOfLine
<environment_details>
# VSCode Visible Files
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# Current Time
7/16/2025, 7:43:19 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
121,697 / 200K tokens used (61%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Let me use PowerShell syntax to navigate and run the command:

<execute_command>
<command>cd autowarehouse-backend; mvn compile quarkus:dev</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'cd autowarehouse-backend; mvn compile quarkus:dev'] Result:
Command is still running in the user's terminal.
Here's the output so far:
[INFO] Scanning for projects...
[INFO]
[INFO] --------------< com.autowarehouse:autowarehouse-backend >---------------
[INFO] Building autowarehouse-backend 1.0.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- resources:3.3.1:resources (default-resources) @ autowarehouse-backend ---
[INFO] Copying 15 resources from src\main\resources to target\classes
[INFO]
[INFO] --- quarkus:3.6.4:generate-code (default) @ autowarehouse-backend ---
The token '&&' is not a valid statement separator in this version.
    + CategoryInfo          : ParserError: (:) [], ParentContainsErrorRecordException
    + FullyQualifiedErrorId : InvalidEndOfLine

PS E:\Ecomindo\Autowarehouse> cd autowarehouse-backend; mvn compile quarkus:dev
[INFO] Scanning for projects...
[INFO]
[INFO] --------------< com.autowarehouse:autowarehouse-backend >---------------
[INFO] Building autowarehouse-backend 1.0.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- resources:3.3.1:resources (default-resources) @ autowarehouse-backend ---
[INFO] Copying 15 resources from src\main\resources to target\classes
[INFO]
[INFO] --- quarkus:3.6.4:generate-code (default) @ autowarehouse-backend ---
[INFO]
[INFO] --- compiler:3.11.0:compile (default-compile) @ autowarehouse-backend ---
[INFO] Nothing to compile - all classes are up to date
[INFO]
[INFO] --- quarkus:3.6.4:dev (default-cli) @ autowarehouse-backend ---
[INFO] Invoking resources:3.3.1:testResources (default-testResources) @ autowarehouse-backend
[INFO] skip non existing resourceDirectory E:\Ecomindo\Autowarehouse\autowarehouse-backend\src\test\resources
[INFO] Invoking quarkus:3.6.4:generate-code-tests (default) @ autowarehouse-backend
[INFO] Invoking compiler:3.11.0:testCompile (default-testCompile) @ autowarehouse-backend
[INFO] Nothing to compile - all classes are up to date
Listening for transport dt_socket at address: 5005


--
Press [e] to edit command line args (currently ''), [:] for the terminal, [h] for more options>
--
Tests paused
Press [e] to edit command line args (currently ''), [:] for the terminal, [h] for more options>
--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:44 DEBUG [or.jb.logging] (main) Logging Provider: org.jboss.logging.JBossLogManagerProvider




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-arc / runtime=true resources=96] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus.arc:arc / runtime=true resources=200] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.enterprise:jakarta.enterprise.cdi-api / runtime=true resources=214] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.enterprise:jakarta.enterprise.lang-model / runtime=true resources=38] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.transaction:jakarta.transaction-api / runtime=true resources=32] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:mutiny / runtime=true resources=552] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.jboss.logging:jboss-logging / runtime=true resources=46] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.jboss.logging:jboss-logging / runtime=true resources=46] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-core / runtime=true resources=306] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.inject:jakarta.inject-api / runtime=true resources=18] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-os / runtime=true resources=20] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-os / runtime=true resources=20] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-development-mode-spi / runtime=true resources=56] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-development-mode-spi / runtime=true resources=56] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.config:smallrye-config / runtime=true resources=30] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.config:smallrye-config-core / runtime=true resources=205] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-classloader / runtime=true resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.config:smallrye-config-common / runtime=true resources=20] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.jboss.logmanager:jboss-logmanager / runtime=true resources=239] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.jboss.logmanager:jboss-logmanager / runtime=true resources=239] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-constraint / runtime=true resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-constraint / runtime=true resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-cpu / runtime=true resources=18] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-cpu / runtime=true resources=18] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-expression / runtime=true resources=26] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-expression / runtime=true resources=26] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-function / runtime=true resources=60] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-function / runtime=true resources=60] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-net / runtime=true resources=31] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-net / runtime=true resources=31] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-ref / runtime=true resources=27] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-ref / runtime=true resources=27] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.jboss.logging:jboss-logging-annotations / runtime=true resources=38] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.jboss.threads:jboss-threads / runtime=true resources=102] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.common:wildfly-common / runtime=true resources=259] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-bootstrap-runner / runtime=true resources=41] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-bootstrap-runner / runtime=true resources=41] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-io / runtime=true resources=16] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-io / runtime=true resources=16] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-fs-util / runtime=true resources=33] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.eclipse.microprofile.context-propagation:microprofile-context-propagation-api / runtime=true resources=25] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-resteasy-reactive / runtime=true resources=109] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-resteasy-reactive-common / runtime=true resources=26] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus.resteasy.reactive:resteasy-reactive-common / runtime=true resources=221] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus.resteasy.reactive:resteasy-reactive-common-types / runtime=true resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.reactivestreams:reactive-streams / runtime=true resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus.resteasy.reactive:resteasy-reactive-vertx / runtime=true resources=48] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.vertx:vertx-web / runtime=true resources=197] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.vertx:vertx-web-common / runtime=true resources=39] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.vertx:vertx-auth-common / runtime=true resources=107] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.vertx:vertx-bridge-common / runtime=true resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-mutiny-vertx-core / runtime=true resources=108] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-mutiny-vertx-runtime / runtime=true resources=30] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:vertx-mutiny-generator / runtime=true resources=60] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.vertx:vertx-codegen / runtime=true resources=122] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus.resteasy.reactive:resteasy-reactive / runtime=true resources=385] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.jboss.logging:commons-logging-jboss-logging / runtime=true resources=24] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.xml.bind:jakarta.xml.bind-api / runtime=true resources=138] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.activation:jakarta.activation-api / runtime=true resources=59] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-vertx-http / runtime=true resources=446] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-security-runtime-spi / runtime=true resources=24] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-credentials / runtime=true resources=14] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-vertx-context / runtime=true resources=16] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus.security:quarkus-security / runtime=true resources=43] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-mutiny-vertx-web / runtime=true resources=79] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-mutiny-vertx-web-common / runtime=true resources=24] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-mutiny-vertx-auth-common / runtime=true resources=33] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-mutiny-vertx-bridge-common / runtime=true resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-mutiny-vertx-uri-template / runtime=true resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.vertx:vertx-uri-template / runtime=true resources=40] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.github.crac:org-crac / runtime=true resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-jsonp / runtime=true resources=10] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.eclipse.parsson:parsson / runtime=true resources=84] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.eclipse.parsson:parsson / runtime=true resources=84] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-virtual-threads / runtime=true resources=29] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.vertx:vertx-core / runtime=true resources=877] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.netty:netty-handler-proxy / runtime=true resources=31] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.netty:netty-codec-socks / runtime=true resources=130] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.netty:netty-codec-http2 / runtime=true resources=302] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.netty:netty-resolver-dns / runtime=true resources=130] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.netty:netty-codec-dns / runtime=true resources=65] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-resteasy-reactive-jackson / runtime=true resources=52] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-resteasy-reactive-jackson-common / runtime=true resources=22] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus.resteasy.reactive:resteasy-reactive-jackson / runtime=true resources=16] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-hibernate-orm-panache / runtime=true resources=31] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-hibernate-orm / runtime=true resources=203] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.hibernate.orm:hibernate-core / runtime=true resources=6918] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.antlr:antlr4-runtime / runtime=true resources=230] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[net.bytebuddy:byte-buddy / runtime=true resources=2934] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[net.bytebuddy:byte-buddy / runtime=true resources=2934] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.hibernate.common:hibernate-commons-annotations / runtime=true resources=69] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.hibernate.orm:hibernate-graalvm / runtime=true resources=8] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.glassfish.jaxb:jaxb-runtime / runtime=true resources=693] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.glassfish.jaxb:jaxb-core / runtime=true resources=158] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.eclipse.angus:angus-activation / runtime=true resources=33] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.glassfish.jaxb:txw2 / runtime=true resources=72] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.sun.istack:istack-commons-runtime / runtime=true resources=35] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.hibernate:quarkus-local-cache / runtime=true resources=51] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-caffeine / runtime=true resources=16] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.github.ben-manes.caffeine:caffeine / runtime=true resources=712] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-hibernate-orm-panache-common / runtime=true resources=26] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-panache-hibernate-common / runtime=true resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-panache-common / runtime=true resources=27] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-jdbc-postgresql / runtime=true resources=29] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.postgresql:postgresql / runtime=true resources=545] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.postgresql:postgresql / runtime=true resources=545] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-jdbc-h2 / runtime=true resources=26] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.h2database:h2 / runtime=true resources=1132] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.h2database:h2 / runtime=true resources=1132] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.locationtech.jts:jts-core / runtime=true resources=621] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-flyway / runtime=true resources=57] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.flywaydb:flyway-core / runtime=true resources=552] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.fasterxml.jackson.dataformat:jackson-dataformat-toml / runtime=true resources=41] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.code.gson:gson / runtime=true resources=239] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-agroal / runtime=true resources=54] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-datasource / runtime=true resources=27] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.agroal:agroal-api / runtime=true resources=73] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.agroal:agroal-narayana / runtime=true resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.jboss:jboss-transaction-spi / runtime=true resources=51] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.agroal:agroal-pool / runtime=true resources=66] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-narayana-jta / runtime=true resources=70] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-transaction-annotations / runtime=true resources=13] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:smallrye-context-propagation-jta / runtime=true resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-reactive-converter-api / runtime=true resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-reactive-converter-mutiny / runtime=true resources=18] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:mutiny-zero-flow-adapters / runtime=true resources=28] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.jboss.narayana.jta:narayana-jta / runtime=true resources=640] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.resource:jakarta.resource-api / runtime=true resources=106] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.jboss.invocation:jboss-invocation / runtime=true resources=71] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.eclipse.microprofile.reactive-streams-operators:microprofile-reactive-streams-operators-api / runtime=true resources=68] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.jboss.narayana.jts:narayana-jts-integration / runtime=true resources=50] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-datasource-common / runtime=true resources=16] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-jwt / runtime=true resources=31] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:smallrye-jwt / runtime=true resources=84] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.eclipse.microprofile.config:microprofile-config-api / runtime=true resources=29] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.eclipse.microprofile.jwt:microprofile-jwt-auth-api / runtime=true resources=24] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.bitbucket.b_c:jose4j / runtime=true resources=244] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:smallrye-jwt-common / runtime=true resources=25] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.annotation:jakarta.annotation-api / runtime=true resources=31] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-security / runtime=true resources=95] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.interceptor:jakarta.interceptor-api / runtime=true resources=23] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-jwt-build / runtime=true resources=20] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:smallrye-jwt-build / runtime=true resources=42] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-security-jpa / runtime=true resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-security-jpa-common / runtime=true resources=24] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.persistence:jakarta.persistence-api / runtime=true resources=226] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-elytron-security-common / runtime=true resources=30] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-credential / runtime=true resources=119] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-credential / runtime=true resources=119] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-asn1 / runtime=true resources=30] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-asn1 / runtime=true resources=30] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-keystore / runtime=true resources=43] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-keystore / runtime=true resources=43] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-util / runtime=true resources=34] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-util / runtime=true resources=34] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-base / runtime=true resources=38] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-base / runtime=true resources=38] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-x500-cert / runtime=true resources=58] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-x500-cert-util / runtime=true resources=18] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-provider-util / runtime=true resources=22] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-provider-util / runtime=true resources=22] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-x500 / runtime=true resources=39] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-x500 / runtime=true resources=39] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-password-impl / runtime=true resources=41] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.wildfly.security:wildfly-elytron-password-impl / runtime=true resources=41] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-websockets / runtime=true resources=21] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-websockets-client / runtime=true resources=30] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus.http:quarkus-http-websocket-core / runtime=true resources=140] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.websocket:jakarta.websocket-client-api / runtime=true resources=57] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus.http:quarkus-http-websocket-vertx / runtime=true resources=23] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.websocket:jakarta.websocket-api / runtime=true resources=22] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-reactive-messaging-kafka / runtime=true resources=28] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-kafka-client / runtime=true resources=85] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-reactive-messaging-kafka / runtime=true resources=154] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-reactive-messaging-kafka-api / runtime=true resources=21] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-reactive-messaging-otel / runtime=true resources=14] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.opentelemetry:opentelemetry-api / runtime=true resources=163] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.opentelemetry:opentelemetry-context / runtime=true resources=49] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.opentelemetry:opentelemetry-semconv / runtime=true resources=45] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.opentelemetry.instrumentation:opentelemetry-instrumentation-api / runtime=true resources=137] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.opentelemetry:opentelemetry-extension-incubator / runtime=true resources=30] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.opentelemetry.instrumentation:opentelemetry-instrumentation-api-semconv / runtime=true resources=159] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.opentelemetry.semconv:opentelemetry-semconv / runtime=true resources=45] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:mutiny-zero / runtime=true resources=56] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-jackson / runtime=true resources=27] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.fasterxml.jackson.core:jackson-databind / runtime=true resources=817] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.fasterxml.jackson.core:jackson-annotations / runtime=true resources=86] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.fasterxml.jackson.datatype:jackson-datatype-jsr310 / runtime=true resources=83] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.fasterxml.jackson.datatype:jackson-datatype-jdk8 / runtime=true resources=42] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.fasterxml.jackson.module:jackson-module-parameter-names / runtime=true resources=24] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.apache.kafka:kafka-clients / runtime=true resources=2575] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.github.luben:zstd-jni / runtime=true resources=74] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.lz4:lz4-java / runtime=true resources=106] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.xerial.snappy:snappy-java / runtime=true resources=106] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-reactive-messaging / runtime=true resources=51] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-mutiny-reactive-streams-operators / runtime=true resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.eclipse.microprofile.reactive-streams-operators:microprofile-reactive-streams-operators-core / runtime=true resources=63] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:mutiny-reactive-streams-operators / runtime=true resources=80] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-reactive-messaging-kotlin / runtime=true resources=20] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.common:smallrye-common-annotation / runtime=true resources=21] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-reactive-messaging-health / runtime=true resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-reactive-messaging-provider / runtime=true resources=143] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-reactive-messaging-api / runtime=true resources=111] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-redis-client / runtime=true resources=438] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-vertx / runtime=true resources=113] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-netty / runtime=true resources=74] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.aayushatharva.brotli4j:brotli4j / runtime=true resources=51] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.aayushatharva.brotli4j:service / runtime=true resources=16] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.aayushatharva.brotli4j:native-windows-x86_64 / runtime=true resources=20] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.netty:netty-codec-haproxy / runtime=true resources=39] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-vertx-latebound-mdc-provider / runtime=true resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-vertx-latebound-mdc-provider / runtime=true resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:smallrye-fault-tolerance-vertx / runtime=true resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-mutiny-vertx-redis-client / runtime=true resources=20] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.vertx:vertx-redis-client / runtime=true resources=100] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-mailer / runtime=true resources=62] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-qute / runtime=true resources=80] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus.qute:qute-core / runtime=true resources=238] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-mutiny / runtime=true resources=20] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-context-propagation / runtime=true resources=23] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:smallrye-context-propagation / runtime=true resources=85] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:smallrye-context-propagation-api / runtime=true resources=21] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:smallrye-context-propagation-storage / runtime=true resources=21] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:mutiny-smallrye-context-propagation / runtime=true resources=31] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.reactive:smallrye-mutiny-vertx-mail-client / runtime=true resources=16] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.vertx:vertx-mail-client / runtime=true resources=80] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-hibernate-validator / runtime=true resources=61] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.hibernate.validator:hibernate-validator / runtime=true resources=1037] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.validation:jakarta.validation-api / runtime=true resources=161] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.fasterxml:classmate / runtime=true resources=55] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.glassfish.expressly:expressly / runtime=true resources=129] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.el:jakarta.el-api / runtime=true resources=65] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye.config:smallrye-config-validator / runtime=true resources=14] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.ws.rs:jakarta.ws.rs-api / runtime=true resources=167] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-openapi / runtime=true resources=44] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:smallrye-open-api-core / runtime=true resources=321] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.eclipse.microprofile.openapi:microprofile-openapi-api / runtime=true resources=163] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.fasterxml.jackson.core:jackson-core / runtime=true resources=251] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.fasterxml.jackson.dataformat:jackson-dataformat-yaml / runtime=true resources=44] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.yaml:snakeyaml / runtime=true resources=279] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:jandex / runtime=true resources=177] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-swagger-ui / runtime=true resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-health / runtime=true resources=33] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:smallrye-health / runtime=true resources=31] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.eclipse.microprofile.health:microprofile-health-api / runtime=true resources=27] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:smallrye-health-api / runtime=true resources=20] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.json:jakarta.json-api / runtime=true resources=50] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[jakarta.json:jakarta.json-api / runtime=true resources=50] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:smallrye-health-provided-checks / runtime=true resources=24] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-micrometer / runtime=true resources=169] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.micrometer:micrometer-core / runtime=true resources=614] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.micrometer:micrometer-commons / runtime=true resources=50] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.micrometer:micrometer-observation / runtime=true resources=71] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.hdrhistogram:HdrHistogram / runtime=true resources=106] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.latencyutils:LatencyUtils / runtime=true resources=31] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.jboss.slf4j:slf4j-jboss-logmanager / runtime=true resources=20] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.jboss.slf4j:slf4j-jboss-logmanager / runtime=true resources=20] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.firebase:firebase-admin / runtime=true resources=849] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.api-client:google-api-client-gson / runtime=true resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.http-client:google-http-client / runtime=true resources=248] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.code.findbugs:jsr305 / runtime=true resources=46] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.errorprone:error_prone_annotations / runtime=true resources=39] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.j2objc:j2objc-annotations / runtime=true resources=24] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.opencensus:opencensus-api / runtime=true resources=401] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.opencensus:opencensus-contrib-http-util / runtime=true resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.api:api-common / runtime=true resources=53] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.auto.value:auto-value-annotations / runtime=true resources=24] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.auth:google-auth-library-oauth2-http / runtime=true resources=164] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.auth:google-auth-library-credentials / runtime=true resources=16] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.cloud:google-cloud-storage / runtime=true resources=428] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.guava:failureaccess / runtime=true resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.guava:listenablefuture / runtime=true resources=7] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.http-client:google-http-client-jackson2 / runtime=true resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.apis:google-api-services-storage / runtime=true resources=124] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.cloud:google-cloud-core / runtime=true resources=117] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.cloud:google-cloud-core-http / runtime=true resources=21] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.http-client:google-http-client-appengine / runtime=true resources=28] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.api:gax-httpjson / runtime=true resources=139] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.cloud:google-cloud-core-grpc / runtime=true resources=25] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.api:gax / runtime=true resources=360] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.api:gax-grpc / runtime=true resources=127] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.grpc:grpc-alts / runtime=true resources=180] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.grpc:grpc-grpclb / runtime=true resources=112] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.conscrypt:conscrypt-openjdk-uber / runtime=true resources=330] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.grpc:grpc-auth / runtime=true resources=12] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.grpc:grpc-protobuf / runtime=true resources=10] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.grpc:grpc-protobuf-lite / runtime=true resources=10] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.grpc:grpc-context / runtime=true resources=2] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.api.grpc:proto-google-iam-v1 / runtime=true resources=83] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.protobuf:protobuf-java / runtime=true resources=739] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.protobuf:protobuf-java-util / runtime=true resources=50] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.api.grpc:proto-google-common-protos / runtime=true resources=796] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.threeten:threetenbp / runtime=true resources=229] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:44 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.api.grpc:proto-google-cloud-storage-v2 / runtime=true resources=325] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.api.grpc:grpc-google-cloud-storage-v2 / runtime=true resources=25] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.api.grpc:gapic-google-cloud-storage-v2 / runtime=true resources=43] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.grpc:grpc-api / runtime=true resources=287] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.grpc:grpc-netty-shaded / runtime=true resources=2856] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.perfmark:perfmark-api / runtime=true resources=11] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.grpc:grpc-core / runtime=true resources=492] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.grpc:grpc-util / runtime=true resources=79] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.android:annotations / runtime=true resources=11] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.codehaus.mojo:animal-sniffer-annotations / runtime=true resources=12] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.grpc:grpc-stub / runtime=true resources=52] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.grpc:grpc-googleapis / runtime=true resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.checkerframework:checker-qual / runtime=true resources=426] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.grpc:grpc-xds / runtime=true resources=4833] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.opencensus:opencensus-proto / runtime=true resources=353] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.grpc:grpc-services / runtime=true resources=382] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.re2j:re2j / runtime=true resources=52] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.grpc:grpc-rls / runtime=true resources=150] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.cloud:google-cloud-firestore / runtime=true resources=223] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[commons-logging:commons-logging / runtime=true resources=42] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.api.grpc:proto-google-cloud-firestore-v1 / runtime=true resources=364] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.cloud:proto-google-cloud-firestore-bundle-v1 / runtime=true resources=43] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.opencensus:opencensus-contrib-grpc-util / runtime=true resources=9] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.guava:guava / runtime=true resources=2060] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.slf4j:slf4j-api / runtime=true resources=67] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.slf4j:slf4j-api / runtime=true resources=67] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.netty:netty-codec-http / runtime=true resources=416] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.netty:netty-common / runtime=true resources=524] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.netty:netty-buffer / runtime=true resources=155] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.netty:netty-codec / runtime=true resources=243] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.netty:netty-handler / runtime=true resources=390] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.netty:netty-resolver / runtime=true resources=41] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.netty:netty-transport-native-unix-common / runtime=true resources=45] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.netty:netty-transport / runtime=true resources=398] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.api-client:google-api-client / runtime=true resources=172] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[commons-codec:commons-codec / runtime=true resources=256] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.oauth-client:google-oauth-client / runtime=true resources=73] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.http-client:google-http-client-gson / runtime=true resources=22] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.http-client:google-http-client-apache-v2 / runtime=true resources=20] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.apache.httpcomponents:httpcore / runtime=true resources=283] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.apache.httpcomponents:httpclient / runtime=true resources=511] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.oauth-client:google-oauth-client-jetty / runtime=true resources=18] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.google.oauth-client:google-oauth-client-java6 / runtime=true resources=23] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mindrot:jbcrypt / runtime=true resources=11] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.fusesource.jansi:jansi / runtime=false resources=98] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.aesh:readline / runtime=false resources=233] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.aesh:aesh / runtime=false resources=318] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.apache.commons:commons-lang3 / runtime=false resources=420] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.ow2.asm:asm-util / runtime=false resources=33] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus.gizmo:gizmo / runtime=false resources=134] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.ow2.asm:asm-commons / runtime=false resources=34] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-class-change-agent / runtime=false resources=12] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-class-change-agent / runtime=false resources=12] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-bootstrap-app-model / runtime=false resources=116] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-bootstrap-app-model / runtime=false resources=116] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-bootstrap-core / runtime=false resources=78] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-bootstrap-core / runtime=false resources=78] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-devtools-utilities / runtime=false resources=13] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.eclipse.sisu:org.eclipse.sisu.inject / runtime=false resources=318] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-builder / runtime=false resources=53] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.graalvm.sdk:graal-sdk / runtime=false resources=418] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.graalvm.sdk:graal-sdk / runtime=false resources=418] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.opentest4j:opentest4j / runtime=false resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.opentest4j:opentest4j / runtime=false resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.junit.platform:junit-platform-commons / runtime=false resources=66] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.junit.platform:junit-platform-commons / runtime=false resources=66] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.junit.platform:junit-platform-engine / runtime=false resources=153] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.junit.platform:junit-platform-engine / runtime=false resources=153] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.apiguardian:apiguardian-api / runtime=false resources=9] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.apiguardian:apiguardian-api / runtime=false resources=9] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.junit.platform:junit-platform-launcher / runtime=false resources=131] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.junit.platform:junit-platform-launcher / runtime=false resources=131] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.junit.jupiter:junit-jupiter-api / runtime=false resources=197] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.junit.jupiter:junit-jupiter-api / runtime=false resources=197] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.junit.jupiter:junit-jupiter-params / runtime=false resources=381] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.junit.jupiter:junit-jupiter-params / runtime=false resources=381] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding parent first element io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.junit.jupiter:junit-jupiter-engine / runtime=false resources=147] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.junit.jupiter:junit-jupiter-engine / runtime=false resources=147] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.junit.jupiter:junit-jupiter / runtime=false resources=5] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-core-deployment / runtime=false resources=878] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-context-propagation-spi / runtime=false resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-vertx-http-dev-ui-spi / runtime=false resources=34] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus.arc:arc-processor / runtime=false resources=286] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-arc-deployment / runtime=false resources=185] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus.resteasy.reactive:resteasy-reactive-common-processor / runtime=false resources=75] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus.resteasy.reactive:resteasy-reactive-processor / runtime=false resources=85] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-vertx-http-deployment-spi / runtime=false resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-kubernetes-spi / runtime=false resources=46] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm.at.vaadin:router / runtime=false resources=45] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm:path-to-regexp / runtime=false resources=12] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm.at.mvnpm:vaadin-webcomponents / runtime=false resources=2410] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm.at.vaadin:vaadin-usage-statistics / runtime=false resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm.at.vaadin:vaadin-development-mode-detector / runtime=false resources=11] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm.at.polymer:polymer / runtime=false resources=73] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm.at.webcomponents:shadycss / runtime=false resources=45] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm.at.open-wc:dedupe-mixin / runtime=false resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm:lit / runtime=false resources=175] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm.at.lit:reactive-element / runtime=false resources=135] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm:lit-element / runtime=false resources=75] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm:lit-html / runtime=false resources=259] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm.at.lit-labs:ssr-dom-shim / runtime=false resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm.at.types:trusted-types / runtime=false resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm:lit-element-state / runtime=false resources=12] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm:tslib / runtime=false resources=18] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm:zrender / runtime=false resources=161] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm:echarts / runtime=false resources=783] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm.at.vanillawc:wc-codemirror / runtime=false resources=328] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.mvnpm:es-module-shims / runtime=false resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-vertx-http-dev-ui-resources / runtime=false resources=69] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.mvnpm:importmap / runtime=false resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-vertx-http-deployment / runtime=false resources=122] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-resteasy-reactive-server-spi-deployment / runtime=false resources=23] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-jaxrs-spi-deployment / runtime=false resources=14] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-security-spi / runtime=false resources=16] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-jsonp-deployment / runtime=false resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-resteasy-reactive-common-deployment / runtime=false resources=36] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-virtual-threads-deployment / runtime=false resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-resteasy-reactive-deployment / runtime=false resources=76] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-resteasy-reactive-jackson-common-deployment / runtime=false resources=16] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-resteasy-reactive-jackson-deployment / runtime=false resources=22] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-hibernate-orm-panache-common-deployment / runtime=false resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-panache-hibernate-common-deployment / runtime=false resources=21] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.ow2.asm:asm / runtime=false resources=45] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.ow2.asm:asm-tree / runtime=false resources=45] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.ow2.asm:asm-analysis / runtime=false resources=22] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-hibernate-orm-panache-deployment / runtime=false resources=22] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-agroal-spi / runtime=false resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.testcontainers:database-commons / runtime=false resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.testcontainers:jdbc / runtime=false resources=21] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.testcontainers:postgresql / runtime=false resources=14] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-devservices-common / runtime=false resources=18] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-devservices-postgresql / runtime=false resources=21] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-datasource-deployment-spi / runtime=false resources=26] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-jdbc-postgresql-deployment / runtime=false resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-devservices-h2 / runtime=false resources=18] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-jdbc-h2-deployment / runtime=false resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-kubernetes-service-binding-spi / runtime=false resources=16] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-datasource-deployment / runtime=false resources=23] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-narayana-jta-deployment / runtime=false resources=18] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-credentials-deployment / runtime=false resources=14] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-agroal-deployment / runtime=false resources=18] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-flyway-deployment / runtime=false resources=24] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-security-deployment / runtime=false resources=45] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-jwt-build-deployment / runtime=false resources=18] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-jwt-deployment / runtime=false resources=20] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-security-jpa-common-deployment / runtime=false resources=25] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-panache-common-deployment / runtime=false resources=45] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-caffeine-deployment / runtime=false resources=22] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-hibernate-orm-deployment / runtime=false resources=94] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-security-jpa-deployment / runtime=false resources=21] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-elytron-security-common-deployment / runtime=false resources=18] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-websockets-client-deployment / runtime=false resources=22] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-websockets-deployment / runtime=false resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.commonmark:commonmark / runtime=false resources=179] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-reactive-messaging-deployment / runtime=false resources=51] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-mutiny-reactive-streams-operators-deployment / runtime=false resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.strimzi:strimzi-test-container / runtime=false resources=23] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-kafka-client-deployment / runtime=false resources=41] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-netty-deployment / runtime=false resources=24] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-vertx-deployment / runtime=false resources=45] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-jackson-spi / runtime=false resources=16] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-jackson-deployment / runtime=false resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-hibernate-orm-deployment-spi / runtime=false resources=16] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-reactive-messaging-kafka-deployment / runtime=false resources=24] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-devservices-deployment / runtime=false resources=20] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-health-spi / runtime=false resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.apache.commons:commons-compress / runtime=false resources=618] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.jetbrains:annotations / runtime=false resources=71] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.rnorth.duct-tape:duct-tape / runtime=false resources=37] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.github.docker-java:docker-java-api / runtime=false resources=401] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.github.docker-java:docker-java-transport / runtime=false resources=45] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[net.java.dev.jna:jna / runtime=false resources=178] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[com.github.docker-java:docker-java-transport-zerodep / runtime=false resources=1412] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[org.testcontainers:testcontainers / runtime=false resources=12344] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-junit4-mock / runtime=false resources=18] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-redis-client-deployment / runtime=false resources=26] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-cache-runtime-spi / runtime=false resources=16] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-cache-deployment-spi / runtime=false resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus.qute:qute-generator / runtime=false resources=31] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-qute-deployment / runtime=false resources=105] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-context-propagation-deployment / runtime=false resources=22] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-mutiny-deployment / runtime=false resources=15] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-mailer-deployment / runtime=false resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-resteasy-common-spi / runtime=false resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-resteasy-server-common-spi / runtime=false resources=18] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-hibernate-validator-spi / runtime=false resources=14] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-resteasy-reactive-spi-deployment / runtime=false resources=44] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-hibernate-validator-deployment / runtime=false resources=23] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-openapi-spi / runtime=false resources=17] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-smallrye-openapi-common-deployment / runtime=false resources=19] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:smallrye-open-api-ui / runtime=false resources=42] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.quarkus:quarkus-swagger-ui-deployment / runtime=false resources=20] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:smallrye-open-api-jaxrs / runtime=false resources=24] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT




19:43:45 DEBUG [io.qu.bo.cl.QuarkusClassLoader] (main) Adding elements io.quarkus.bootstrap.classloading.PathTreeClassPathElement[io.smallrye:smallrye-open-api-spring / runtime=false resources=21] to QuarkusClassLoader Augmentation Class Loader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT


... (output truncated) ...
19:43:49 DEBUG [or.hi.bo.mo.in.BasicValueBinder] (Quarkus Main Thread) Starting `BasicValueBinder#fillSimpleValue` for actualDelivery




19:43:49 DEBUG [or.hi.bo.mo.in.BasicValueBinder] (Quarkus Main Thread) Starting `BasicValueBinder#fillSimpleValue` for changedAt




19:43:49 DEBUG [or.hi.bo.mo.in.BasicValueBinder] (Quarkus Main Thread) Starting `BasicValueBinder#fillSimpleValue` for changedBy




19:43:49 DEBUG [or.hi.bo.mo.in.BasicValueBinder] (Quarkus Main Thread) Starting `BasicValueBinder#fillSimpleValue` for estimatedDelivery




19:43:49 DEBUG [or.hi.bo.mo.in.BasicValueBinder] (Quarkus Main Thread) Starting `BasicValueBinder#fillSimpleValue` for notes




19:43:49 DEBUG [or.hi.bo.mo.in.BasicValueBinder] (Quarkus Main Thread) Starting `BasicValueBinder#fillSimpleValue` for previousStatus




19:43:49 DEBUG [or.hi.bo.mo.in.BasicValueBinder] (Quarkus Main Thread) Starting `BasicValueBinder#fillSimpleValue` for status




19:43:49 DEBUG [or.hi.bo.mo.in.BasicValueBinder] (Quarkus Main Thread) Starting `BasicValueBinder#fillSimpleValue` for trackingNumber




19:43:49 DEBUG [or.hi.bo.mo.in.BasicValueBinder] (Quarkus Main Thread) Starting `BasicValueBinder#fillSimpleValue` for id




19:43:49 DEBUG [or.hi.bo.mo.in.BasicValueBinder] (Quarkus Main Thread) Starting `BasicValueBinder#fillSimpleValue` for createdAt




19:43:49 DEBUG [or.hi.ma.PrimaryKey] (Quarkus Main Thread) Forcing column [id] to be non-null as it is part of the primary key for table [saved_items]




19:43:49 DEBUG [or.hi.ma.PrimaryKey] (Quarkus Main Thread) Forcing column [id] to be non-null as it is part of the primary key for table [bids]




19:43:49 DEBUG [or.hi.ma.PrimaryKey] (Quarkus Main Thread) Forcing column [id] to be non-null as it is part of the primary key for table [wishlist_items]




19:43:49 DEBUG [or.hi.ma.PrimaryKey] (Quarkus Main Thread) Forcing column [id] to be non-null as it is part of the primary key for table [categories]




19:43:49 DEBUG [or.hi.ma.PrimaryKey] (Quarkus Main Thread) Forcing column [id] to be non-null as it is part of the primary key for table [users]




19:43:49 DEBUG [or.hi.ma.PrimaryKey] (Quarkus Main Thread) Forcing column [id] to be non-null as it is part of the primary key for table [orders]




19:43:49 DEBUG [or.hi.ma.PrimaryKey] (Quarkus Main Thread) Forcing column [id] to be non-null as it is part of the primary key for table [notifications]




19:43:49 DEBUG [or.hi.ma.PrimaryKey] (Quarkus Main Thread) Forcing column [id] to be non-null as it is part of the primary key for table [products]




19:43:49 DEBUG [or.hi.ma.PrimaryKey] (Quarkus Main Thread) Forcing column [id] to be non-null as it is part of the primary key for table [cart_items]




19:43:49 DEBUG [or.hi.ma.PrimaryKey] (Quarkus Main Thread) Forcing column [id] to be non-null as it is part of the primary key for table [auctions]




19:43:49 DEBUG [or.hi.ma.PrimaryKey] (Quarkus Main Thread) Forcing column [id] to be non-null as it is part of the primary key for table [order_items]




19:43:49 DEBUG [or.hi.ma.PrimaryKey] (Quarkus Main Thread) Forcing column [id] to be non-null as it is part of the primary key for table [reviews]




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:49 DEBUG [or.hi.ma.PrimaryKey] (Quarkus Main Thread) Forcing column [id] to be non-null as it is part of the primary key for table [order_status_history]




19:43:49 DEBUG [or.hi.ma.PrimaryKey] (Quarkus Main Thread) Forcing column [id] to be non-null as it is part of the primary key for table [auction_watchers]




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.Product.tags




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a collection of element: com.autowarehouse.entity.Product.tags




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionPropertyHolder] (Quarkus Main Thread) Attempting to locate auto-apply AttributeConverter for collection element [com.autowarehouse.entity.Product.tags]




19:43:49 DEBUG [or.hi.bo.mo.in.BasicValueBinder] (Quarkus Main Thread) building BasicValue for null




19:43:49 DEBUG [or.hi.ma.BasicValue] (Quarkus Main Thread) Skipping column re-registration: product_tags.tag




19:43:49 DEBUG [or.hi.bo.mo.in.BasicValueBinder] (Quarkus Main Thread) Starting `BasicValueBinder#fillSimpleValue` for null




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: product_id, element: tag




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.Product.imageUrls




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a collection of element: com.autowarehouse.entity.Product.imageUrls




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionPropertyHolder] (Quarkus Main Thread) Attempting to locate auto-apply AttributeConverter for collection element [com.autowarehouse.entity.Product.imageUrls]




19:43:49 DEBUG [or.hi.bo.mo.in.BasicValueBinder] (Quarkus Main Thread) building BasicValue for null




19:43:49 DEBUG [or.hi.ma.BasicValue] (Quarkus Main Thread) Skipping column re-registration: product_images.image_url




19:43:49 DEBUG [or.hi.bo.mo.in.BasicValueBinder] (Quarkus Main Thread) Starting `BasicValueBinder#fillSimpleValue` for null




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: product_id, element: image_url




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.Category.children




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.Category.children through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.Category.children -> categories




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.Category.parent




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: parent_id, one-to-many: com.autowarehouse.entity.Category




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.Category.products




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.Category.products through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.Category.products -> products




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.Product.category




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: category_id, one-to-many: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.User.bids




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.User.bids through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.User.bids -> bids




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.Bid.user




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: user_id, one-to-many: com.autowarehouse.entity.Bid




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.User.cartItems




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.User.cartItems through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.User.cartItems -> cart_items




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.CartItem.user




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: user_id, one-to-many: com.autowarehouse.entity.CartItem




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.User.notifications




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.User.notifications through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.User.notifications -> notifications




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.Notification.user




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: user_id, one-to-many: com.autowarehouse.entity.Notification




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.User.orders




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.User.orders through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.User.orders -> orders




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.Order.user




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: user_id, one-to-many: com.autowarehouse.entity.Order




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.User.reviews




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.User.reviews through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.User.reviews -> reviews




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.Review.user




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: user_id, one-to-many: com.autowarehouse.entity.Review




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.User.wishlistItems




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.User.wishlistItems through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.User.wishlistItems -> wishlist_items




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.WishlistItem.user




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: user_id, one-to-many: com.autowarehouse.entity.WishlistItem




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.Order.items




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.Order.items through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.Order.items -> order_items




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.OrderItem.order




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: order_id, one-to-many: com.autowarehouse.entity.OrderItem




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.Order.statusHistory




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.Order.statusHistory through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.Order.statusHistory -> order_status_history




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.OrderStatusHistory.order




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: order_id, one-to-many: com.autowarehouse.entity.OrderStatusHistory




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.Product.auctions




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.Product.auctions through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.Product.auctions -> auctions




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.Auction.product




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: product_id, one-to-many: com.autowarehouse.entity.Auction




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.Product.cartItems




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.Product.cartItems through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.Product.cartItems -> cart_items




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.CartItem.product




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: product_id, one-to-many: com.autowarehouse.entity.CartItem




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.Product.orderItems




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.Product.orderItems through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.Product.orderItems -> order_items




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.OrderItem.product




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: product_id, one-to-many: com.autowarehouse.entity.OrderItem




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.Product.reviews




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.Product.reviews through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.Product.reviews -> reviews




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.Review.product




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: product_id, one-to-many: com.autowarehouse.entity.Review




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.Product.wishlistItems




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.Product.wishlistItems through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.Product.wishlistItems -> wishlist_items




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.WishlistItem.product




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: product_id, one-to-many: com.autowarehouse.entity.WishlistItem




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.Auction.bids




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.Auction.bids through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.Auction.bids -> bids




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.Bid.auction




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: auction_id, one-to-many: com.autowarehouse.entity.Bid




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Second pass for collection: com.autowarehouse.entity.Auction.watchers




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Binding a OneToMany: com.autowarehouse.entity.Auction.watchers through a foreign key




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionBinder] (Quarkus Main Thread) Mapping collection: com.autowarehouse.entity.Auction.watchers -> auction_watchers




19:43:49 DEBUG [or.hi.bo.mo.in.TableBinder] (Quarkus Main Thread) Retrieving property com.autowarehouse.entity.AuctionWatcher.auction




19:43:49 DEBUG [or.hi.bo.mo.in.CollectionSecondPass] (Quarkus Main Thread) Mapped collection key: auction_id, one-to-many: com.autowarehouse.entity.AuctionWatcher




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Auction




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Auction




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Category




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Order




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Order




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Category




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Auction




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Auction




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Category




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Order




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Order




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Category




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.InFlightMetadataCollectorImpl] (Quarkus Main Thread) Resolving reference to class: com.autowarehouse.entity.User




19:43:49 DEBUG [or.in.qu.hi.ca.QuarkusInfinispanRegionFactory] (Quarkus Main Thread) Stop region factory




19:43:49 DEBUG [or.in.qu.hi.ca.QuarkusInfinispanRegionFactory] (Quarkus Main Thread) Clear region references




19:43:49 DEBUG [or.hi.bo.re.in.BootstrapServiceRegistryImpl] (Quarkus Main Thread) Implicitly destroying Boot-strap registry on de-registration of all child ServiceRegistries




19:43:49 DEBUG [or.hi.bo.re.in.BootstrapServiceRegistryImpl] (Quarkus Main Thread) Implicitly destroying Boot-strap registry on de-registration of all child ServiceRegistries




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) Not known whether passed class name [com.autowarehouse.entity.Bid] is safe




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) No temp ClassLoader provided; using live ClassLoader for loading potentially unsafe class : com.autowarehouse.entity.Bid




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) Not known whether passed class name [com.autowarehouse.entity.Review] is safe




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) No temp ClassLoader provided; using live ClassLoader for loading potentially unsafe class : com.autowarehouse.entity.Review




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) Not known whether passed class name [com.autowarehouse.entity.AuctionWatcher] is safe




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) No temp ClassLoader provided; using live ClassLoader for loading potentially unsafe class : com.autowarehouse.entity.AuctionWatcher




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) Not known whether passed class name [com.autowarehouse.entity.OrderStatusHistory] is safe




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) No temp ClassLoader provided; using live ClassLoader for loading potentially unsafe class : com.autowarehouse.entity.OrderStatusHistory




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) Not known whether passed class name [com.autowarehouse.entity.Auction] is safe




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) No temp ClassLoader provided; using live ClassLoader for loading potentially unsafe class : com.autowarehouse.entity.Auction




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) Not known whether passed class name [com.autowarehouse.entity.Notification] is safe




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) No temp ClassLoader provided; using live ClassLoader for loading potentially unsafe class : com.autowarehouse.entity.Notification




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) Not known whether passed class name [com.autowarehouse.entity.Category] is safe




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) No temp ClassLoader provided; using live ClassLoader for loading potentially unsafe class : com.autowarehouse.entity.Category




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) Not known whether passed class name [com.autowarehouse.entity.User] is safe




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) No temp ClassLoader provided; using live ClassLoader for loading potentially unsafe class : com.autowarehouse.entity.User




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) Not known whether passed class name [com.autowarehouse.entity.Order] is safe




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) No temp ClassLoader provided; using live ClassLoader for loading potentially unsafe class : com.autowarehouse.entity.Order




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) Not known whether passed class name [com.autowarehouse.entity.CartItem] is safe




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) No temp ClassLoader provided; using live ClassLoader for loading potentially unsafe class : com.autowarehouse.entity.CartItem




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) Not known whether passed class name [com.autowarehouse.entity.WishlistItem] is safe




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) No temp ClassLoader provided; using live ClassLoader for loading potentially unsafe class : com.autowarehouse.entity.WishlistItem




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) Not known whether passed class name [com.autowarehouse.entity.Product] is safe




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) No temp ClassLoader provided; using live ClassLoader for loading potentially unsafe class : com.autowarehouse.entity.Product




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) Not known whether passed class name [com.autowarehouse.entity.SavedItem] is safe




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) No temp ClassLoader provided; using live ClassLoader for loading potentially unsafe class : com.autowarehouse.entity.SavedItem




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) Not known whether passed class name [com.autowarehouse.entity.OrderItem] is safe




19:43:49 DEBUG [or.hi.bo.in.ClassLoaderAccessImpl] (Quarkus Main Thread) No temp ClassLoader provided; using live ClassLoader for loading potentially unsafe class : com.autowarehouse.entity.OrderItem




19:43:49 DEBUG [io.qu.ar.ru.ArcRecorder] (Quarkus Main Thread) Bean container listener io.quarkus.hibernate.orm.runtime.HibernateOrmRecorder$1 finished in 606 ms




19:43:49 DEBUG [or.hi.va.in.ut.Version] (Quarkus Main Thread) HV000001: Hibernate Validator 8.0.1.Final




19:43:49 DEBUG [or.hi.va.in.en.AbstractConfigurationImpl] (Quarkus Main Thread) Setting custom LocaleResolver of type io.quarkus.hibernate.validator.runtime.locale.LocaleResolversWrapper




19:43:49 DEBUG [or.hi.va.in.en.AbstractConfigurationImpl] (Quarkus Main Thread) Setting custom BeanMetaDataClassNormalizer of type io.quarkus.hibernate.validator.runtime.ArcProxyBeanMetaDataClassNormalizer




19:43:49 DEBUG [io.qu.ar.impl] (Quarkus Main Thread) Loaded 90 removed beans lazily




19:43:49 DEBUG [or.hi.va.in.en.AbstractConfigurationImpl] (Quarkus Main Thread) Setting custom ConstraintValidatorFactory of type io.quarkus.hibernate.validator.runtime.ArcConstraintValidatorFactoryImpl




19:43:49 DEBUG [or.hi.va.in.en.AbstractConfigurationImpl] (Quarkus Main Thread) Setting custom TraversableResolver of type org.hibernate.validator.internal.engine.resolver.JPATraversableResolver




19:43:49 DEBUG [or.hi.va.in.en.AbstractConfigurationImpl] (Quarkus Main Thread) HV000002: Ignoring XML configuration.




19:43:49 DEBUG [or.hi.va.re.PlatformResourceBundleLocator] (Quarkus Main Thread) ValidationMessages not found.




19:43:49 DEBUG [or.hi.va.in.en.ValidatorFactoryConfigurationHelper] (Quarkus Main Thread) HV000255: Using io.quarkus.hibernate.validator.runtime.locale.LocaleResolversWrapper as locale resolver.




19:43:49 DEBUG [or.hi.va.re.PlatformResourceBundleLocator] (Quarkus Main Thread) ContributorValidationMessages not found.




19:43:49 DEBUG [or.hi.va.re.PlatformResourceBundleLocator] (Quarkus Main Thread) org.hibernate.validator.ValidationMessages found.




19:43:49 DEBUG [or.hi.va.me.ResourceBundleMessageInterpolator] (Quarkus Main Thread) Loaded expression factory via original TCCL




19:43:49 DEBUG [or.hi.va.in.en.ValidatorFactoryConfigurationHelper] (Quarkus Main Thread) HV000252: Using org.hibernate.validator.internal.engine.DefaultPropertyNodeNameProvider as property node name provider.




19:43:49 DEBUG [or.hi.va.in.en.ValidatorFactoryConfigurationHelper] (Quarkus Main Thread) HV000234: Using org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator as ValidatorFactory-scoped message interpolator.




19:43:49 DEBUG [or.hi.va.in.en.ValidatorFactoryConfigurationHelper] (Quarkus Main Thread) HV000234: Using org.hibernate.validator.internal.engine.resolver.JPATraversableResolver as ValidatorFactory-scoped traversable resolver.




19:43:49 DEBUG [or.hi.va.in.en.ValidatorFactoryConfigurationHelper] (Quarkus Main Thread) HV000234: Using org.hibernate.validator.internal.util.ExecutableParameterNameProvider as ValidatorFactory-scoped parameter name provider.




19:43:49 DEBUG [or.hi.va.in.en.ValidatorFactoryConfigurationHelper] (Quarkus Main Thread) HV000234: Using org.hibernate.validator.internal.engine.DefaultClockProvider as ValidatorFactory-scoped clock provider.




19:43:49 DEBUG [or.hi.va.in.en.ValidatorFactoryConfigurationHelper] (Quarkus Main Thread) HV000234: Using org.hibernate.validator.internal.engine.scripting.DefaultScriptEvaluatorFactory as ValidatorFactory-scoped script evaluator factory.




19:43:49 DEBUG [io.qu.ar.ru.ArcRecorder] (Quarkus Main Thread) Bean container listener io.quarkus.hibernate.validator.runtime.HibernateValidatorRecorder$2 finished in 167 ms




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.vertx.serializers.ServerVertxBufferMessageBodyWriter and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.vertx.serializers.ServerMutinyBufferMessageBodyWriter and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.vertx.serializers.ServerVertxAsyncFileMessageBodyWriter and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.vertx.serializers.ServerMutinyAsyncFileMessageBodyWriter and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class io.quarkus.resteasy.reactive.jackson.runtime.serialisers.vertx.VertxJsonArrayMessageBodyWriter and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class io.quarkus.resteasy.reactive.jackson.runtime.serialisers.vertx.VertxJsonObjectMessageBodyWriter and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class io.quarkus.resteasy.reactive.jackson.runtime.serialisers.vertx.VertxJsonArrayMessageBodyReader and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class io.quarkus.resteasy.reactive.jackson.runtime.serialisers.vertx.VertxJsonObjectMessageBodyReader and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerStringMessageBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerStringMessageBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerStringMessageBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerStringMessageBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerStringMessageBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerCharArrayMessageBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerByteArrayMessageBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerFormUrlEncodedProvider and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerInputStreamMessageBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.StreamingOutputMessageBodyWriter and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerReaderBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerFileBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerFilePartBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.core.multipart.MultipartMessageBodyWriter and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerPathBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerPathPartBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerStringMessageBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerBooleanMessageBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerCharacterMessageBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerNumberMessageBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerInputStreamMessageBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerReaderBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerFileBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerByteArrayMessageBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:49 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class org.jboss.resteasy.reactive.server.providers.serialisers.ServerDefaultTextPlainBodyHandler and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:50 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class io.quarkus.resteasy.reactive.server.runtime.exceptionmappers.ForbiddenExceptionMapper and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:50 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class io.quarkus.resteasy.reactive.jackson.runtime.mappers.DefaultMismatchedInputException and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:50 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class io.quarkus.resteasy.reactive.server.runtime.exceptionmappers.AuthenticationRedirectExceptionMapper and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:50 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class io.quarkus.resteasy.reactive.server.runtime.exceptionmappers.AuthenticationCompletionExceptionMapper and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:50 DEBUG [io.qu.ar.ru.BeanContainerImpl] (Quarkus Main Thread) No matching bean found for type class io.quarkus.rest.runtime.__QuarkusInit and qualifiers []. The bean might have been marked as unused and removed during build.




19:43:50 DEBUG [io.ne.ch.DefaultChannelId] (Thread-204) -Dio.netty.processId: 329988 (auto-detected)




19:43:50 DEBUG [io.ne.ch.DefaultChannelId] (Thread-204) -Dio.netty.machineId: 30:27:ac:81:18:e2:16:92 (user-set)





19:43:50 DEBUG [io.ve.co.lo.LoggerFactory] (Quarkus Main Thread) Using io.vertx.core.logging.SLF4JLogDelegateFactory




19:43:50 DEBUG [io.qu.ve.co.ru.VertxCoreRecorder] (Quarkus Main Thread) Vert.x Cache configured to: C:\Users\phili\AppData\Local\Temp\vertx-cache\5935344219470974788




19:43:50 DEBUG [io.ne.ut.ResourceLeakDetector] (Quarkus Main Thread) -Dio.netty.leakDetection.level: simple




19:43:50 DEBUG [io.ne.ut.ResourceLeakDetector] (Quarkus Main Thread) -Dio.netty.leakDetection.targetRecords: 4




19:43:50 DEBUG [io.ne.ch.MultithreadEventLoopGroup] (Quarkus Main Thread) -Dio.netty.eventLoopThreads: 24




19:43:50 DEBUG [io.ne.ut.co.GlobalEventExecutor] (Quarkus Main Thread) -Dio.netty.globalEventExecutor.quietPeriodSeconds: 1




19:43:50 DEBUG [io.ne.ch.ni.NioEventLoop] (Quarkus Main Thread) -Dio.netty.noKeySetOptimization: false




19:43:50 DEBUG [io.ne.ch.ni.NioEventLoop] (Quarkus Main Thread) -Dio.netty.selectorAutoRebuildThreshold: 512




19:43:50 DEBUG [io.ne.ut.in.PlatformDependent] (Quarkus Main Thread) org.jctools-core.MpscChunkedArrayQueue: available




19:43:50 DEBUG [io.qu.ve.co.ru.VertxCoreRecorder] (Quarkus Main Thread) Vertx has Native Transport Enabled: false




19:43:50 DEBUG [co.ar.at.jta] (Quarkus Main Thread) Setting up node identifiers '[quarkus]' for which recovery will be performed




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:50 FINE  [ja.js.sp.JsonProvider] (Quarkus Main Thread) Checking system property jakarta.json.provider




19:43:50 FINE  [ja.js.sp.JsonProvider] (Quarkus Main Thread) Checking ServiceLoader




19:43:50 FINE  [ja.js.sp.JsonProvider] (Quarkus Main Thread) ServiceLoader loading Facility used; returning object [org.eclipse.parsson.JsonProviderImpl]




19:43:50 FINE  [ja.js.sp.JsonProvider] (Quarkus Main Thread) Checking system property jakarta.json.provider




19:43:50 FINE  [ja.js.sp.JsonProvider] (Quarkus Main Thread) Checking ServiceLoader




19:43:50 FINE  [ja.js.sp.JsonProvider] (Quarkus Main Thread) ServiceLoader loading Facility used; returning object [org.eclipse.parsson.JsonProviderImpl]




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:50 INFO  [io.ag.pool] (Quarkus Main Thread) Datasource '<default>': Initial size smaller than min. Connections will be created when necessary




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:50 DEBUG [io.qu.ag.ru.DataSources] (Quarkus Main Thread) Started datasource <default> connected to jdbc:postgresql://localhost:5434/autowarehouse




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:50 FINE  [or.po.Driver] (agroal-11) Loading driver configuration via classloader jdk.internal.loader.ClassLoaders$AppClassLoader@5e2de80c




19:43:50 FINE  [or.po.Driver] (agroal-11) Connecting with URL: jdbc:postgresql://localhost:5434/autowarehouse




19:43:50 FINE  [or.po.jd.PgConnection] (agroal-11) PostgreSQL JDBC Driver 42.6.0




19:43:50 FINE  [or.po.jd.PgConnection] (agroal-11)   setDefaultFetchSize = 0




19:43:50 FINE  [or.po.jd.PgConnection] (agroal-11)   setPrepareThreshold = 5




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:50 FINE  [or.po.co.v3.ConnectionFactoryImpl] (agroal-11) Trying to establish a protocol version 3 connection to localhost:5434




19:43:50 FINE  [or.po.co.v3.ConnectionFactoryImpl] (agroal-11) Receive Buffer Size is 65,536




19:43:50 FINE  [or.po.co.v3.ConnectionFactoryImpl] (agroal-11) Send Buffer Size is 65,536




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:50 DEBUG [io.qu.fl.ru.QuarkusPathLocationScanner] (Quarkus Main Thread) Locations: [classpath:db/migration]




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:50 DEBUG [io.qu.fl.ru.QuarkusPathLocationScanner] (Quarkus Main Thread) Loading db/migration/V1__Create_initial_tables.sql




19:43:50 DEBUG [io.qu.fl.ru.QuarkusPathLocationScanner] (Quarkus Main Thread) Loading db/migration/V11__Ensure_notification_columns_exist.sql




19:43:50 DEBUG [io.qu.fl.ru.QuarkusPathLocationScanner] (Quarkus Main Thread) Loading db/migration/V12__Add_address_to_users.sql




19:43:50 DEBUG [io.qu.fl.ru.QuarkusPathLocationScanner] (Quarkus Main Thread) Loading db/migration/V3__Insert_sample_users.sql




19:43:50 DEBUG [io.qu.fl.ru.QuarkusPathLocationScanner] (Quarkus Main Thread) Loading db/migration/V1_1__Add_address_to_users.sql




19:43:50 DEBUG [io.qu.fl.ru.QuarkusPathLocationScanner] (Quarkus Main Thread) Loading db/migration/V9__Add_missing_notification_columns.sql




19:43:50 DEBUG [io.qu.fl.ru.QuarkusPathLocationScanner] (Quarkus Main Thread) Loading db/migration/V6__Insert_reliable_admin_user.sql




19:43:50 DEBUG [io.qu.fl.ru.QuarkusPathLocationScanner] (Quarkus Main Thread) Loading db/migration/V7__Add_features_column_to_products.sql




19:43:50 DEBUG [io.qu.fl.ru.QuarkusPathLocationScanner] (Quarkus Main Thread) Loading db/migration/V10__Create_order_status_history.sql




19:43:50 DEBUG [io.qu.fl.ru.QuarkusPathLocationScanner] (Quarkus Main Thread) Loading db/migration/V4__Insert_additional_test_users.sql




19:43:50 DEBUG [io.qu.fl.ru.QuarkusPathLocationScanner] (Quarkus Main Thread) Loading db/migration/V8__Add_tracking_number_to_orders.sql




19:43:50 DEBUG [io.qu.fl.ru.QuarkusPathLocationScanner] (Quarkus Main Thread) Loading db/migration/V2__Insert_sample_categories.sql




19:43:50 DEBUG [io.qu.fl.ru.QuarkusPathLocationScanner] (Quarkus Main Thread) Loading db/migration/V5__Insert_new_admin_user.sql




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:50 INFO  [or.fl.co.in.li.VersionPrinter] (Quarkus Main Thread) Flyway Community Edition 9.22.3 by Redgate




19:43:50 INFO  [or.fl.co.in.li.VersionPrinter] (Quarkus Main Thread) See release notes here: https://rd.gt/416ObMi




19:43:50 INFO  [or.fl.co.in.li.VersionPrinter] (Quarkus Main Thread)




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Scanning for classpath resources at 'classpath:db/callback' ...




19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Determining location urls for classpath:db/callback using ClassLoader QuarkusClassLoader:Quarkus Runtime ClassLoader: DEV for autowarehouse-backend-1.0.0-SNAPSHOT restart no:0@203d1d93 ...




19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Unable to resolve location classpath:db/callback.




19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V1__Create_initial_tables.sql




19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V11__Ensure_notification_columns_exist.sql




19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V12__Add_address_to_users.sql




19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V3__Insert_sample_users.sql




19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V1_1__Add_address_to_users.sql




19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V9__Add_missing_notification_columns.sql




19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V6__Insert_reliable_admin_user.sql




19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V7__Add_features_column_to_products.sql




19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V10__Create_order_status_history.sql




19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V4__Insert_additional_test_users.sql




19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V8__Add_tracking_number_to_orders.sql




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V2__Insert_sample_categories.sql




19:43:50 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V5__Insert_new_admin_user.sql




19:43:50 FINE  [or.po.Driver] (agroal-11) Connecting with URL: jdbc:postgresql://localhost:5434/autowarehouse




19:43:50 FINE  [or.po.jd.PgConnection] (agroal-11) PostgreSQL JDBC Driver 42.6.0




19:43:50 FINE  [or.po.jd.PgConnection] (agroal-11)   setDefaultFetchSize = 0




19:43:50 FINE  [or.po.jd.PgConnection] (agroal-11)   setPrepareThreshold = 5




19:43:50 FINE  [or.po.co.v3.ConnectionFactoryImpl] (agroal-11) Trying to establish a protocol version 3 connection to localhost:5434




19:43:50 FINE  [or.po.co.v3.ConnectionFactoryImpl] (agroal-11) Receive Buffer Size is 65,536




19:43:50 FINE  [or.po.co.v3.ConnectionFactoryImpl] (agroal-11) Send Buffer Size is 65,536




19:43:50 INFO  [or.fl.co.FlywayExecutor] (Quarkus Main Thread) Database: jdbc:postgresql://localhost:5434/autowarehouse (PostgreSQL 17.5)




19:43:50 DEBUG [or.fl.co.FlywayExecutor] (Quarkus Main Thread) Driver: PostgreSQL JDBC Driver 42.6.0




19:43:50 DEBUG [or.fl.co.FlywayExecutor] (Quarkus Main Thread) DDL Transactions Supported: true




19:43:50 DEBUG [or.fl.co.in.sc.SchemaHistoryFactory] (Quarkus Main Thread) Schemas:




19:43:50 DEBUG [or.fl.co.in.sc.SchemaHistoryFactory] (Quarkus Main Thread) Default schema: null




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:50 WARN  [or.fl.co.in.da.ba.Database] (Quarkus Main Thread) Flyway upgrade recommended: PostgreSQL 17.5 is newer than this version of Flyway and support has not been tested. The latest supported version of PostgreSQL is 15.




19:43:50 DEBUG [or.fl.co.in.ca.SqlScriptCallbackFactory] (Quarkus Main Thread) Scanning for SQL callbacks ...




19:43:50 FINE  [or.po.jd.PgConnection] (Quarkus Main Thread)   setAutoCommit = false




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:50 INFO  [or.fl.co.in.sc.JdbcTableSchemaHistory] (Quarkus Main Thread) Repair of failed migration in Schema History table "public"."flyway_schema_history" not necessary. No failed migration detected.




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V1__Create_initial_tables.sql (filename: V1__Create_initial_tables.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V11__Ensure_notification_columns_exist.sql (filename: V11__Ensure_notification_columns_exist.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V12__Add_address_to_users.sql (filename: V12__Add_address_to_users.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V3__Insert_sample_users.sql (filename: V3__Insert_sample_users.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V1_1__Add_address_to_users.sql (filename: V1_1__Add_address_to_users.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V9__Add_missing_notification_columns.sql (filename: V9__Add_missing_notification_columns.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V6__Insert_reliable_admin_user.sql (filename: V6__Insert_reliable_admin_user.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V7__Add_features_column_to_products.sql (filename: V7__Add_features_column_to_products.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V10__Create_order_status_history.sql (filename: V10__Create_order_status_history.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V4__Insert_additional_test_users.sql (filename: V4__Insert_additional_test_users.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V8__Add_tracking_number_to_orders.sql (filename: V8__Add_tracking_number_to_orders.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V2__Insert_sample_categories.sql (filename: V2__Insert_sample_categories.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V5__Insert_new_admin_user.sql (filename: V5__Insert_new_admin_user.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V1__Create_initial_tables.sql (filename: V1__Create_initial_tables.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V11__Ensure_notification_columns_exist.sql (filename: V11__Ensure_notification_columns_exist.sql)




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V12__Add_address_to_users.sql (filename: V12__Add_address_to_users.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V3__Insert_sample_users.sql (filename: V3__Insert_sample_users.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V1_1__Add_address_to_users.sql (filename: V1_1__Add_address_to_users.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V9__Add_missing_notification_columns.sql (filename: V9__Add_missing_notification_columns.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V6__Insert_reliable_admin_user.sql (filename: V6__Insert_reliable_admin_user.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V7__Add_features_column_to_products.sql (filename: V7__Add_features_column_to_products.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V10__Create_order_status_history.sql (filename: V10__Create_order_status_history.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V4__Insert_additional_test_users.sql (filename: V4__Insert_additional_test_users.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V8__Add_tracking_number_to_orders.sql (filename: V8__Add_tracking_number_to_orders.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V2__Insert_sample_categories.sql (filename: V2__Insert_sample_categories.sql)




19:43:50 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V5__Insert_new_admin_user.sql (filename: V5__Insert_new_admin_user.sql)




19:43:50 FINE  [or.po.jd.PgConnection] (Quarkus Main Thread)   setAutoCommit = true




19:43:50 INFO  [or.fl.co.in.co.DbRepair] (Quarkus Main Thread) Successfully repaired schema history table "public"."flyway_schema_history" (execution time 00:00.048s).




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:50 FINE  [or.po.Driver] (agroal-11) Connecting with URL: jdbc:postgresql://localhost:5434/autowarehouse




19:43:50 FINE  [or.po.jd.PgConnection] (agroal-11) PostgreSQL JDBC Driver 42.6.0




19:43:50 FINE  [or.po.jd.PgConnection] (agroal-11)   setDefaultFetchSize = 0




19:43:50 FINE  [or.po.jd.PgConnection] (agroal-11)   setPrepareThreshold = 5




19:43:50 FINE  [or.po.co.v3.ConnectionFactoryImpl] (agroal-11) Trying to establish a protocol version 3 connection to localhost:5434




19:43:50 FINE  [or.po.co.v3.ConnectionFactoryImpl] (agroal-11) Receive Buffer Size is 65,536




19:43:50 FINE  [or.po.co.v3.ConnectionFactoryImpl] (agroal-11) Send Buffer Size is 65,536




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:51 FINE  [or.po.jd.PgConnection] (Quarkus Main Thread)   setAutoCommit = false




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:51 FINE  [or.po.jd.PgConnection] (Quarkus Main Thread)   setAutoCommit = true




19:43:51 FINE  [or.po.jd.PgConnection] (Quarkus Main Thread)   setAutoCommit = false




19:43:51 FINE  [or.po.jd.PgConnection] (Quarkus Main Thread)   setAutoCommit = true




19:43:51 DEBUG [or.fl.co.FlywayExecutor] (Quarkus Main Thread) Memory usage: 273 of 420M




19:43:51 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V1__Create_initial_tables.sql




19:43:51 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V11__Ensure_notification_columns_exist.sql




19:43:51 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V12__Add_address_to_users.sql




19:43:51 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V3__Insert_sample_users.sql




19:43:51 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V1_1__Add_address_to_users.sql




19:43:51 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V9__Add_missing_notification_columns.sql




19:43:51 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V6__Insert_reliable_admin_user.sql




19:43:51 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V7__Add_features_column_to_products.sql




19:43:51 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V10__Create_order_status_history.sql




19:43:51 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V4__Insert_additional_test_users.sql




19:43:51 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V8__Add_tracking_number_to_orders.sql




19:43:51 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V2__Insert_sample_categories.sql




19:43:51 DEBUG [or.fl.co.in.re.ResourceNameValidator] (Quarkus Main Thread) Validating V5__Insert_new_admin_user.sql




19:43:51 FINE  [or.po.Driver] (agroal-11) Connecting with URL: jdbc:postgresql://localhost:5434/autowarehouse




19:43:51 FINE  [or.po.jd.PgConnection] (agroal-11) PostgreSQL JDBC Driver 42.6.0




19:43:51 DEBUG [or.fl.co.FlywayExecutor] (Quarkus Main Thread) DDL Transactions Supported: true




19:43:51 FINE  [or.po.jd.PgConnection] (agroal-11)   setDefaultFetchSize = 0




19:43:51 DEBUG [or.fl.co.in.sc.SchemaHistoryFactory] (Quarkus Main Thread) Schemas:




19:43:51 FINE  [or.po.jd.PgConnection] (agroal-11)   setPrepareThreshold = 5




19:43:51 DEBUG [or.fl.co.in.sc.SchemaHistoryFactory] (Quarkus Main Thread) Default schema: null




19:43:51 FINE  [or.po.co.v3.ConnectionFactoryImpl] (agroal-11) Trying to establish a protocol version 3 connection to localhost:5434




19:43:51 FINE  [or.po.co.v3.ConnectionFactoryImpl] (agroal-11) Receive Buffer Size is 65,536




19:43:51 FINE  [or.po.co.v3.ConnectionFactoryImpl] (agroal-11) Send Buffer Size is 65,536




19:43:51 WARN  [or.fl.co.in.da.ba.Database] (Quarkus Main Thread) Flyway upgrade recommended: PostgreSQL 17.5 is newer than this version of Flyway and support has not been tested. The latest supported version of PostgreSQL is 15.




19:43:51 DEBUG [or.fl.co.in.ca.SqlScriptCallbackFactory] (Quarkus Main Thread) Scanning for SQL callbacks ...




19:43:51 DEBUG [or.fl.co.in.co.DbValidate] (Quarkus Main Thread) Validating migrations ...




19:43:51 FINE  [or.po.jd.PgConnection] (Quarkus Main Thread)   setAutoCommit = false




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V1__Create_initial_tables.sql (filename: V1__Create_initial_tables.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V11__Ensure_notification_columns_exist.sql (filename: V11__Ensure_notification_columns_exist.sql)




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V12__Add_address_to_users.sql (filename: V12__Add_address_to_users.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V3__Insert_sample_users.sql (filename: V3__Insert_sample_users.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V1_1__Add_address_to_users.sql (filename: V1_1__Add_address_to_users.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V9__Add_missing_notification_columns.sql (filename: V9__Add_missing_notification_columns.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V6__Insert_reliable_admin_user.sql (filename: V6__Insert_reliable_admin_user.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V7__Add_features_column_to_products.sql (filename: V7__Add_features_column_to_products.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V10__Create_order_status_history.sql (filename: V10__Create_order_status_history.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V4__Insert_additional_test_users.sql (filename: V4__Insert_additional_test_users.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V8__Add_tracking_number_to_orders.sql (filename: V8__Add_tracking_number_to_orders.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V2__Insert_sample_categories.sql (filename: V2__Insert_sample_categories.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V5__Insert_new_admin_user.sql (filename: V5__Insert_new_admin_user.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V1__Create_initial_tables.sql (filename: V1__Create_initial_tables.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V11__Ensure_notification_columns_exist.sql (filename: V11__Ensure_notification_columns_exist.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V12__Add_address_to_users.sql (filename: V12__Add_address_to_users.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V3__Insert_sample_users.sql (filename: V3__Insert_sample_users.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V1_1__Add_address_to_users.sql (filename: V1_1__Add_address_to_users.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V9__Add_missing_notification_columns.sql (filename: V9__Add_missing_notification_columns.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V6__Insert_reliable_admin_user.sql (filename: V6__Insert_reliable_admin_user.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V7__Add_features_column_to_products.sql (filename: V7__Add_features_column_to_products.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V10__Create_order_status_history.sql (filename: V10__Create_order_status_history.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V4__Insert_additional_test_users.sql (filename: V4__Insert_additional_test_users.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V8__Add_tracking_number_to_orders.sql (filename: V8__Add_tracking_number_to_orders.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V2__Insert_sample_categories.sql (filename: V2__Insert_sample_categories.sql)




19:43:51 DEBUG [io.qu.fl.ru.FlywayRecorder] (Quarkus Main Thread) Filtering out resource: db/migration/V5__Insert_new_admin_user.sql (filename: V5__Insert_new_admin_user.sql)




19:43:51 FINE  [or.po.jd.PgConnection] (Quarkus Main Thread)   setAutoCommit = true




19:43:51 FINE  [or.po.jd.PgConnection] (Quarkus Main Thread)   setAutoCommit = false




19:43:51 FINE  [or.po.jd.PgConnection] (Quarkus Main Thread)   setAutoCommit = true




19:43:51 DEBUG [or.fl.co.FlywayExecutor] (Quarkus Main Thread) Memory usage: 276 of 420M




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:51 DEBUG [or.hi.or.beans] (Quarkus Main Thread) HHH10005004: Stopping BeanContainer : io.quarkus.hibernate.orm.runtime.cdi.QuarkusArcBeanContainer@2f101b64




19:43:51 DEBUG [io.qu.ar.impl] (Quarkus Main Thread) ArC DI container shut down




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:51 ERROR [io.qu.ru.Application] (Quarkus Main Thread) Failed to start application (with profile [dev]): java.lang.RuntimeException: Failed to start quarkus
        at io.quarkus.runner.ApplicationImpl.doStart(Unknown Source)
        at io.quarkus.runtime.Application.start(Application.java:101)
        at io.quarkus.runtime.ApplicationLifecycleManager.run(ApplicationLifecycleManager.java:111)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:71)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:44)
        at io.quarkus.runtime.Quarkus.run(Quarkus.java:124)
        at io.quarkus.runner.GeneratedMain.main(Unknown Source)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:
:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at io.quarkus.runner.bootstrap.StartupActionImpl$1.run(StartupActionImpl.java:113)
        at java.base/java.lang.Thread.run(Thread.java:840)
Caused by: org.flywaydb.core.api.exception.FlywayValidateException: Validate failed: Migrations have failed vali
idation
Detected resolved migration not applied to database: 1.1.
To ignore this migration, set -ignoreMigrationPatterns='*:ignored'. To allow executing this migration, set -outO
OfOrder=true.
Need more flexibility with validation rules? Learn more: https://rd.gt/3AbJUZE
        at org.flywaydb.core.Flyway.lambda$migrate$0(Flyway.java:146)
        at org.flywaydb.core.FlywayExecutor.execute(FlywayExecutor.java:213)
        at org.flywaydb.core.Flyway.migrate(Flyway.java:140)
        at io.quarkus.flyway.runtime.FlywayRecorder.doStartActions(FlywayRecorder.java:126)
        at io.quarkus.deployment.steps.FlywayProcessor$startActions2099152139.deploy_0(Unknown Source)
        at io.quarkus.deployment.steps.FlywayProcessor$startActions2099152139.deploy(Unknown Source)
        ... 13 more






19:43:51 DEBUG [io.qu.ru.Application] (Quarkus Main Thread) Stopping application




--
Tests paused
Press [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>--
Tests paused
Press [space] to restart, [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:51 INFO  [io.qu.de.de.IsolatedDevModeMain] (main) Attempting to start live reload endpoint to recover from previous Quarkus startup failure




2025-07-16 19:43:51,153 INFO  [io.qua.dep.dev.IsolatedDevModeMain] (main) Attempting to start live reload endpoint to recover from previous Quarkus startup failure




--
Tests paused
Press [space] to restart, [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:51 DEBUG [io.ne.ut.NetUtil] (vert.x-eventloop-thread-0) -Djava.net.preferIPv4Stack: false




--
Tests paused
Press [space] to restart, [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>2025-07-16 19:43:51,192 DEBUG [io.net.uti.NetUtil] (vert.x-eventloop-thread-0) -Djava.net.preferIPv4Stack: false





19:43:51 DEBUG [io.ne.ut.NetUtil] (vert.x-eventloop-thread-0) -Djava.net.preferIPv6Addresses: false




2025-07-16 19:43:51,193 DEBUG [io.net.uti.NetUtil] (vert.x-eventloop-thread-0) -Djava.net.preferIPv6Addresses: false




--
Tests paused
Press [space] to restart, [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:51 DEBUG [io.ne.ut.NetUtilInitializations] (vert.x-eventloop-thread-0) Loopback interface: lo (Software Loopback Interface 1, 127.0.0.1)




2025-07-16 19:43:51,209 DEBUG [io.net.uti.NetUtilInitializations] (vert.x-eventloop-thread-0) Loopback interface: lo (Software Loopback Interface 1, 127.0.0.1)




19:43:51 DEBUG [io.ne.ut.NetUtil] (vert.x-eventloop-thread-0) Failed to get SOMAXCONN from sysctl and file \proc\sys\net\core\somaxconn. Default: 200




2025-07-16 19:43:51,210 DEBUG [io.net.uti.NetUtil] (vert.x-eventloop-thread-0) Failed to get SOMAXCONN from sysctl and file \proc\sys\net\core\somaxconn. Default: 200




--
Tests paused
Press [space] to restart, [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:51 DEBUG [io.ne.bu.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.numHeapArenas: 24




2025-07-16 19:43:51,236 DEBUG [io.net.buf.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.numHeapArenas: 24




--
Tests paused
Press [space] to restart, [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:51 DEBUG [io.ne.bu.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.numDirectArenas: 24




2025-07-16 19:43:51,237 DEBUG [io.net.buf.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.numDirectArenas: 24




19:43:51 DEBUG [io.ne.bu.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.pageSize: 8192





2025-07-16 19:43:51,237 DEBUG [io.net.buf.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.pageSize: 8192




19:43:51 DEBUG [io.ne.bu.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.maxOrder: 3




2025-07-16 19:43:51,238 DEBUG [io.net.buf.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.maxOrder: 3




19:43:51 DEBUG [io.ne.bu.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.chunkSize: 65536




2025-07-16 19:43:51,238 DEBUG [io.net.buf.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.chunkSize: 65536




19:43:51 DEBUG [io.ne.bu.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.smallCacheSize: 256




2025-07-16 19:43:51,238 DEBUG [io.net.buf.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.smallCacheSize: 256




19:43:51 DEBUG [io.ne.bu.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.normalCacheSize: 64




2025-07-16 19:43:51,239 DEBUG [io.net.buf.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.normalCacheSize: 64




19:43:51 DEBUG [io.ne.bu.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.maxCachedBufferCapacity: 32768




2025-07-16 19:43:51,239 DEBUG [io.net.buf.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.maxCachedBufferCapacity: 32768




19:43:51 DEBUG [io.ne.bu.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.cacheTrimInterval: 8192




2025-07-16 19:43:51,240 DEBUG [io.net.buf.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.cacheTrimInterval: 8192




19:43:51 DEBUG [io.ne.bu.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.cacheTrimIntervalMillis: 0




2025-07-16 19:43:51,240 DEBUG [io.net.buf.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.cacheTrimIntervalMillis: 0




19:43:51 DEBUG [io.ne.bu.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.useCacheForAllThreads: false




2025-07-16 19:43:51,241 DEBUG [io.net.buf.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.useCacheForAllThreads: false




19:43:51 DEBUG [io.ne.bu.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.maxCachedByteBuffersPerChunk: 1023




2025-07-16 19:43:51,241 DEBUG [io.net.buf.PooledByteBufAllocator] (vert.x-eventloop-thread-1) -Dio.netty.allocator.maxCachedByteBuffersPerChunk: 1023




--
Tests paused
Press [space] to restart, [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>19:43:51 DEBUG [io.ne.bu.ByteBufUtil] (vert.x-eventloop-thread-1) -Dio.netty.allocator.type: pooled




--
Tests paused
Press [space] to restart, [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>2025-07-16 19:43:51,272 DEBUG [io.net.buf.ByteBufUtil] (vert.x-eventloop-thread-1) -Dio.netty.allocator.type: pooled




19:43:51 DEBUG [io.ne.bu.ByteBufUtil] (vert.x-eventloop-thread-1) -Dio.netty.threadLocalDirectBufferSize: 0




2025-07-16 19:43:51,273 DEBUG [io.net.buf.ByteBufUtil] (vert.x-eventloop-thread-1) -Dio.netty.threadLocalDirectBufferSize: 0




19:43:51 DEBUG [io.ne.bu.ByteBufUtil] (vert.x-eventloop-thread-1) -Dio.netty.maxThreadLocalCharBufferSize: 16384





2025-07-16 19:43:51,273 DEBUG [io.net.buf.ByteBufUtil] (vert.x-eventloop-thread-1) -Dio.netty.maxThreadLocalCharBufferSize: 16384




19:43:51 DEBUG [io.qu.mi.ru.bi.ve.VertxMeterBinderAdapter] (vert.x-acceptor-thread-0) Create HttpServerMetrics with options io.vertx.core.http.HttpServerOptions@689dc74e and address localhost:8081




--
Tests paused
Press [space] to restart, [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options>2025-07-16 19:43:51,281 DEBUG [io.qua.mic.run.bin.ver.VertxMeterBinderAdapter] (vert.x-acceptor-thread-0) Create HttpServerMetrics with options io.vertx.core.http.HttpServerOptions@689dc74e and address localhost:8081




--
Tests paused
Press [space] to restart, [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options

You will be updated on the terminal status and new output in the future.
<environment_details>
# VSCode Visible Files
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# Actively Running Terminals
## Original command: `cd autowarehouse-backend; mvn compile quarkus:dev`

# Current Time
7/16/2025, 7:43:57 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
122,825 / 200K tokens used (61%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

I can see the issue! The backend is failing to start because there's a Flyway migration validation error. The problem is that there's a V1_1__Add_address_to_users.sql file that Flyway is detecting but it's not applied to the database. This is causing a conflict with the V12__Add_address_to_users.sql file.

Let me check if the V1_1 file exists and remove it since we should use V12:

<execute_command>
<command>del autowarehouse-backend\src\main\resources\db\migration\V1_1__Add_address_to_users.sql</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'del autowarehouse-backend\src\main\resources\db\migration\V1_1__Add_address_to_users.sql'] Result:
Command executed.
Output:
del : Cannot find path 'E:\Ecomindo\Autowarehouse\autowarehouse-backend\src\main\resources\db\
migration\V1_1__Add_address_to_users.sql' because it does not exist.
At line:1 char:1
+ del autowarehouse-backend\src\main\resources\db\migration\V1_1__Add_a ...
+ ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    + CategoryInfo          : ObjectNotFound: (E:\Ecomindo\Aut...ss_to_users.sql:String) [Rem
   ove-Item], ItemNotFoundException
    + FullyQualifiedErrorId : PathNotFound,Microsoft.PowerShell.Commands.RemoveItemCommand
<environment_details>
# VSCode Visible Files
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# Actively Running Terminals
## Original command: `cd autowarehouse-backend; mvn compile quarkus:dev`
### New Output
hreadLocalDirectBufferSize: 0
19:43:51 DEBUG [io.ne.bu.ByteBufUtil] (vert.x-eventloop-thread-1) -Dio.netty.maxThreadLocalCharBufferSize: 16384

2025-07-16 19:43:51,273 DEBUG [io.net.buf.ByteBufUtil] (vert.x-eventloop-thread-1) -Dio.netty.maxThreadLocalCharBufferSize: 16384
19:43:51 DEBUG [io.qu.mi.ru.bi.ve.VertxMeterBinderAdapter] (vert.x-acceptor-thread-0) Create HttpServerMetrics with options io.vertx.core.http.HttpServerOptions@689dc74e and address localhost:8081
2025-07-16 19:43:51,281 DEBUG [io.qua.mic.run.bin.ver.VertxMeterBinderAdapter] (vert.x-acceptor-thread-0) Create HttpServerMetrics with options io.vertx.core.http.HttpServerOptions@689dc74e and address localhost:8081

--
Tests paused
Press [space] to restart, [e] to edit command line args (currently ''), [r] to resume testing, [o] Toggle test output, [:] for the terminal, [h] for more options

# Current Time
7/16/2025, 7:44:15 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
201,711 / 200K tokens used (101%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

The file doesn't exist on disk, but Flyway is still detecting it. This might be a caching issue or the file exists in the compiled resources. Let me check the migration directory and then restart the application:

<list_files>
<path>autowarehouse-backend/src/main/resources/db/migration</path>
</list_files>

[Response interrupted by a tool use result. Only one tool may be used at a time and should be placed at the end of the message.]

---

**User:**

[list_files for 'autowarehouse-backend/src/main/resources/db/migration'] Result:
V1__Create_initial_tables.sql
V2__Insert_sample_categories.sql
V3__Insert_sample_users.sql
V4__Insert_additional_test_users.sql
V5__Insert_new_admin_user.sql
V6__Insert_reliable_admin_user.sql
V7__Add_features_column_to_products.sql
V8__Add_tracking_number_to_orders.sql
V9__Add_missing_notification_columns.sql
V10__Create_order_status_history.sql
V11__Ensure_notification_columns_exist.sql
V12__Add_address_to_users.sql
<environment_details>
# VSCode Visible Files
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# Actively Running Terminals
## Original command: `cd autowarehouse-backend; mvn compile quarkus:dev`

# Current Time
7/16/2025, 7:44:28 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
121,850 / 200K tokens used (61%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Good! The V1_1 file doesn't exist in the source directory, but it might be cached in the compiled resources. Let me clean the target directory and restart:

<execute_command>
<command>cd autowarehouse-backend && mvn clean</command>
<requires_approval>false</requires_approval>
</execute_command>

[Response interrupted by a tool use result. Only one tool may be used at a time and should be placed at the end of the message.]

---

**User:**

[execute_command for 'cd autowarehouse-backend && mvn clean'] Result:
Command executed.
Output:
At line:1 char:26
+ cd autowarehouse-backend && mvn clean
+                          ~~
The token '&&' is not a valid statement separator in this version.
    + CategoryInfo          : ParserError: (:) [], ParentContainsErrorRecordException
    + FullyQualifiedErrorId : InvalidEndOfLine
<environment_details>
# VSCode Visible Files
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# Actively Running Terminals
## Original command: `cd autowarehouse-backend; mvn compile quarkus:dev`

# Current Time
7/16/2025, 7:44:36 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
221,216 / 200K tokens used (111%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Let me use PowerShell syntax to clean the project:

<execute_command>
<command>cd autowarehouse-backend; mvn clean</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'cd autowarehouse-backend; mvn clean'] Result:
Command executed.
Output:
[INFO] Scanning for projects...
[INFO]
[INFO] --------------< com.autowarehouse:autowarehouse-backend >---------------
[INFO] Building autowarehouse-backend 1.0.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- clean:3.2.0:clean (default-clean) @ autowarehouse-backend ---
[INFO] Deleting E:\Ecomindo\Autowarehouse\autowarehouse-backend\target
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.918 s
[INFO] Finished at: 2025-07-16T19:44:54+07:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-clean-plugin:3.2.0:clean (default
t-clean) on project autowarehouse-backend: Failed to clean project: Failed to delete E:\Ecomindo
o\Autowarehouse\autowarehouse-backend\target\autowarehouse-backend-dev.jar -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following
g articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoExecutionException
<environment_details>
# VSCode Visible Files
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# Actively Running Terminals
## Original command: `cd autowarehouse-backend; mvn compile quarkus:dev`

# Current Time
7/16/2025, 7:44:55 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
114,998 / 200K tokens used (57%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

The clean failed because the JAR file is still in use by the running Quarkus process. Let me stop the current process first and then clean. Let me press Ctrl+C to stop the current process:

<execute_command>
<command>taskkill /F /IM java.exe</command>
<requires_approval>true</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'taskkill /F /IM java.exe'] Result:
Command executed.
Output:
SUCCESS: The process "java.exe" with PID 331528 has been terminated.
SUCCESS: The process "java.exe" with PID 329988 has been terminated.
<environment_details>
# VSCode Visible Files
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# Current Time
7/16/2025, 7:45:07 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
207,749 / 200K tokens used (104%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Now let me clean the project:

<execute_command>
<command>cd autowarehouse-backend; mvn clean</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'cd autowarehouse-backend; mvn clean'] Result:
Command executed.
Output:
[INFO] Scanning for projects...
[INFO]
[INFO] --------------< com.autowarehouse:autowarehouse-backend >---------------
[INFO] Building autowarehouse-backend 1.0.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- clean:3.2.0:clean (default-clean) @ autowarehouse-backend ---
[INFO] Deleting E:\Ecomindo\Autowarehouse\autowarehouse-backend\target
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.934 s
[INFO] Finished at: 2025-07-16T19:45:19+07:00
[INFO] ------------------------------------------------------------------------
<environment_details>
# VSCode Visible Files
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# Current Time
7/16/2025, 7:45:20 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
109,589 / 200K tokens used (55%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Great! Now let me restart the backend:

<execute_command>
<command>cd autowarehouse-backend; mvn compile quarkus:dev</command>
<requires_approval>false</requires_approval>
</execute_command>

---

**User:**

[execute_command for 'cd autowarehouse-backend; mvn compile quarkus:dev'] Result:
Command is still running in the user's terminal.
Here's the output so far:
[INFO] Scanning for projects...
[INFO]
[INFO] --------------< com.autowarehouse:autowarehouse-backend >---------------
[INFO] Building autowarehouse-backend 1.0.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- resources:3.3.1:resources (default-resources) @ autowarehouse-backend ---
[INFO] Copying 15 resources from src\main\resources to target\classes
[INFO]
[INFO] --- quarkus:3.6.4:generate-code (default) @ autowarehouse-backend ---

You will be updated on the terminal status and new output in the future.
<environment_details>
# VSCode Visible Files
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# VSCode Open Tabs
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-frontend/src/components/forms/ValidationInput.vue
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue
tasks-readme/profile-module-adjustment-tasks.md
autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-backend/src/main/resources/db/migration/V1_1__Add_address_to_users.sql
autowarehouse-frontend/src/views/UserProfileView.vue
autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql
c:/Users/phili/Downloads/cline_task_jul-16-2025_7-31-55-pm.md

# Actively Running Terminals
## Original command: `cd autowarehouse-backend; mvn compile quarkus:dev`

# Current Time
7/16/2025, 7:45:36 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
196,681 / 200K tokens used (98%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

<new_task>
<context>
1. Current Work:
The user has requested several improvements to the autowarehouse application:
1. Add address field to users table and adjust API/frontend to save it
2. Integrate saved profile data into checkout form
3. Replace alert() calls with toast notifications

I was working on adding the address field to the users table. I created a migration file V12__Add_address_to_users.sql and updated the User entity, UpdateProfileRequest DTO, UserService, and UserResource to handle the address field. However, there seems to be a conflict with a V1_1__Add_address_to_users.sql file that's showing in VSCode tabs but doesn't exist on disk.

I just stopped the running Quarkus backend, cleaned the project with mvn clean, and restarted it with mvn compile quarkus:dev. The backend is currently starting up.

2. Key Technical Concepts:
- Spring Boot/Quarkus backend with JAX-RS
- Vue.js 3 with TypeScript frontend
- Flyway database migrations
- PostgreSQL database
- Toast notification system using useNotifications composable
- Profile management with address field
- Checkout integration

3. Relevant Files and Code:
- autowarehouse-backend/src/main/resources/db/migration/V12__Add_address_to_users.sql - Migration to add address column
- autowarehouse-backend/src/main/java/com/autowarehouse/entity/User.java - Updated with address field
- autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java - Updated with address field
- autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java - Updated to handle address
- autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java - Updated API endpoints
- autowarehouse-frontend/src/services/api.ts - Updated API calls
- autowarehouse-frontend/src/views/UserProfileView.vue - Updated profile form
- autowarehouse-frontend/src/views/CheckoutView.vue - Needs integration with profile data
- autowarehouse-frontend/src/composables/useNotifications.ts - Toast notification system

4. Problem Solving:
There was a migration file conflict that needed to be resolved. The backend was running with mvn quarkus:dev. I stopped the backend, cleaned the project, and restarted it to ensure the address field migration works properly.

5. Pending Tasks and Next Steps:
From the user's message: "saya tadi cek sudah mantap, namun ada beberapa improvement."

The user wants:
1. "addressnya tuh kayanya belom kesimpen deh, di table users memang saya lihat belum ada address, jadi tolong di add lalu adjust api dan frontendnya juga supaya kesimpan" - Add address to users table and adjust API/frontend
2. "ketika mreka mau checkout, tolong diintegrasi juga ke dalam form yang ada di form checkout product yak. Kalo bisa nanti form checkout productnya stylingnya juga dibenerin, biar agak semakin rapih dan smooth" - Integrate profile data into checkout form and improve styling
3. "alert-alertnya, dipakein kayak tadi dong yang kamu sempet buat tadi ada toast bar di kanan atas itu, jangan pake alert gosong lagi" - Replace alerts with toast notifications

Next steps:
1. Wait for backend to finish starting up and check if migration runs successfully
2. Test that profile address saving works
3. Update checkout form to pre-populate with saved profile data
4. Improve checkout form styling
5. Replace remaining alert() calls with toast notifications throughout the application

The backend is currently starting up with the command: cd autowarehouse-backend; mvn compile quarkus:dev
</context>
</new_task>

