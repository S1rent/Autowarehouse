# 👤 User Management & Authentication Feature

## Overview
Sistem manajemen user yang menangani registrasi, login, profil user, dan kontrol akses berbasis role untuk customer dan admin.

## User Stories
- Sebagai user, saya ingin mendaftar akun baru
- Sebagai user, saya ingin login dengan email/password atau Google
- Sebagai user, saya ingin reset password jika lupa
- Sebagai user, saya ingin mengedit profil saya
- Sebagai admin, saya ingin mengelola akun user
- Sebagai sistem, saya ingin mengontrol akses berdasarkan role

## Technical Requirements

### Frontend Components
- **LoginPage**: Halaman login dengan form dan Google OAuth
- **RegisterPage**: Halaman registrasi user baru
- **ForgotPasswordPage**: Form untuk reset password
- **ResetPasswordPage**: Form untuk set password baru
- **ProfilePage**: Halaman profil user dengan edit form
- **UserManagementPage**: Admin panel untuk kelola user
- **AuthGuard**: Route guard untuk proteksi halaman

### Backend Endpoints
```
// Authentication
POST /api/auth/login - User login
POST /api/auth/register - User registration
POST /api/auth/logout - User logout
POST /api/auth/refresh - Refresh JWT token
POST /api/auth/forgot-password - Request password reset
POST /api/auth/reset-password - Reset password with token
POST /api/auth/google - Google OAuth login

// User profile
GET /api/users/profile - Get current user profile
PUT /api/users/profile - Update user profile
PUT /api/users/change-password - Change password

// Admin user management
GET /api/admin/users - Get all users with pagination
PUT /api/admin/users/{id}/status - Update user status
PUT /api/admin/users/{id}/role - Update user role
```

### Database Schema
```sql
-- Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255), -- NULL for Google OAuth users
    full_name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER', -- CUSTOMER, ADMIN
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE', -- ACTIVE, INACTIVE, BLOCKED
    
    -- Profile information
    date_of_birth DATE,
    gender VARCHAR(10),
    profile_image_url VARCHAR(500),
    
    -- OAuth information
    google_id VARCHAR(100),
    oauth_provider VARCHAR(20), -- GOOGLE, FACEBOOK (future)
    
    -- Address information
    address TEXT,
    city VARCHAR(100),
    postal_code VARCHAR(10),
    
    -- Timestamps
    email_verified_at TIMESTAMP,
    last_login_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Password reset tokens table
CREATE TABLE password_reset_tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    token VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    used_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User sessions table (optional, for session management)
CREATE TABLE user_sessions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    session_token VARCHAR(255) NOT NULL,
    device_info TEXT,
    ip_address INET,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## UI Design Reference
Based on ui-design folder:
- **login-register (1).pdf**: Login page layout
- **login-register (2).pdf**: Registration form
- **login-register (3).pdf**: Forgot password flow
- **login-register (4).pdf**: Social login integration
- **profile.pdf**: User profile page

## Key Features

### 1. User Registration
- **Email Registration**: Form dengan validasi email, password, nama
- **Google OAuth**: One-click registration dengan Google account
- **Email Verification**: Konfirmasi email setelah registrasi (optional)
- **Terms & Conditions**: Checkbox persetujuan syarat dan ketentuan

### 2. User Authentication
- **Email/Password Login**: Traditional login form
- **Google OAuth Login**: Social login integration
- **Remember Me**: Persistent login session
- **JWT Token**: Secure token-based authentication
- **Auto Logout**: Session timeout untuk security

### 3. Password Management
- **Forgot Password**: Email reset link ke user
- **Reset Password**: Form untuk set password baru dengan token
- **Change Password**: Update password dari profile page
- **Password Validation**: Strong password requirements

### 4. User Profile
- **Profile Information**: Nama, email, phone, alamat
- **Profile Picture**: Upload dan crop gambar profil
- **Address Management**: Multiple shipping addresses
- **Account Settings**: Notification preferences, privacy settings

### 5. Role-Based Access Control
- **Customer Role**: Access ke shopping, orders, profile
- **Admin Role**: Access ke admin panel dan management features
- **Route Guards**: Proteksi halaman berdasarkan role
- **API Authorization**: Endpoint protection dengan role check

### 6. Admin User Management
- **User List**: Daftar semua user dengan filter dan search
- **User Status**: Active, inactive, blocked status management
- **Role Management**: Assign/change user roles
- **User Analytics**: Registration trends, active users

## State Management (Pinia)

### Auth Store
```typescript
interface AuthState {
  user: User | null
  token: string | null
  isAuthenticated: boolean
  loading: boolean
  loginAttempts: number
}

interface User {
  id: number
  email: string
  fullName: string
  phone?: string
  role: UserRole
  status: UserStatus
  profileImageUrl?: string
  address?: string
  city?: string
  postalCode?: string
  lastLoginAt?: Date
  createdAt: Date
}

enum UserRole {
  CUSTOMER = 'CUSTOMER',
  ADMIN = 'ADMIN'
}

enum UserStatus {
  ACTIVE = 'ACTIVE',
  INACTIVE = 'INACTIVE',
  BLOCKED = 'BLOCKED'
}

// Actions
const authStore = {
  login(email: string, password: string)
  loginWithGoogle(googleToken: string)
  register(userData: RegisterData)
  logout()
  refreshToken()
  updateProfile(profileData: ProfileData)
  changePassword(oldPassword: string, newPassword: string)
}
```

## Security Features

### Authentication Security
- **Password Hashing**: BCrypt dengan salt untuk password storage
- **JWT Security**: Signed tokens dengan expiration
- **Rate Limiting**: Limit login attempts per IP/user
- **Account Lockout**: Temporary lockout setelah failed attempts
- **Session Management**: Secure session handling

### Input Validation
- **Email Validation**: Format dan domain validation
- **Password Strength**: Minimum requirements (length, complexity)
- **XSS Prevention**: Input sanitization
- **SQL Injection**: Parameterized queries

### OAuth Security
- **Google OAuth**: Secure integration dengan Google APIs
- **State Parameter**: CSRF protection untuk OAuth flow
- **Token Validation**: Verify Google tokens server-side

## Integration Points
- **Google OAuth API**: Social login integration
- **Email Service**: Password reset dan verification emails
- **Firebase Storage**: Profile image upload
- **Notification Service**: Account-related notifications

## Business Rules

### Registration Rules
- Unique email addresses
- Minimum age requirement (optional)
- Terms acceptance required
- Email verification (optional)

### Password Policy
- Minimum 8 characters
- At least 1 uppercase, 1 lowercase, 1 number
- No common passwords
- Password history (prevent reuse)

### Account Security
- Maximum 5 failed login attempts
- Account lockout for 15 minutes
- Password reset token expires in 1 hour
- Session timeout after 24 hours inactivity

## Error Handling
- **Invalid Credentials**: Clear error message tanpa info leak
- **Account Locked**: Informative message dengan unlock time
- **Email Not Found**: Generic message untuk security
- **Token Expired**: Redirect ke login dengan clear message

## Performance Considerations
- **Password Hashing**: Async bcrypt untuk avoid blocking
- **Token Caching**: Cache JWT validation results
- **Session Storage**: Efficient session data storage
- **Database Indexing**: Index pada email, role, status

## Analytics & Monitoring
- User registration trends
- Login success/failure rates
- Password reset frequency
- Active user metrics
- Role distribution
- Geographic user distribution

## Compliance & Privacy
- **GDPR Compliance**: User data protection dan right to deletion
- **Data Minimization**: Collect only necessary user data
- **Audit Logging**: Log authentication events
- **Privacy Settings**: User control over data sharing
