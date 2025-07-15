-- Insert additional test users for comprehensive testing
-- Password for all users is 'password123' (hashed with BCrypt)
-- BCrypt hash for 'password123': $2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi

-- Insert Additional Admin User (Super Admin)
INSERT INTO users (email, password, first_name, last_name, phone_number, role, is_active, is_email_verified, created_at, updated_at)
VALUES (
    'superadmin@autowarehouse.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'Super',
    'Admin',
    '+62812345677',
    'ADMIN',
    TRUE,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Insert Customer User 4 (Premium Customer)
INSERT INTO users (email, password, first_name, last_name, phone_number, role, is_active, is_email_verified, created_at, updated_at)
VALUES (
    'premium.customer@autowarehouse.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'Sarah',
    'Johnson',
    '+62812345682',
    'CUSTOMER',
    TRUE,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Insert Customer User 5 (International Customer)
INSERT INTO users (email, password, first_name, last_name, phone_number, role, is_active, is_email_verified, created_at, updated_at)
VALUES (
    'international@gmail.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'Michael',
    'Chen',
    '+65987654321',
    'CUSTOMER',
    TRUE,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Insert Customer User 6 (New Customer - Unverified Email)
INSERT INTO users (email, password, first_name, last_name, phone_number, role, is_active, is_email_verified, created_at, updated_at)
VALUES (
    'newcustomer@outlook.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'Emma',
    'Wilson',
    '+62812345683',
    'CUSTOMER',
    TRUE,
    FALSE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Insert Customer User 7 (Inactive Customer for testing)
INSERT INTO users (email, password, first_name, last_name, phone_number, role, is_active, is_email_verified, created_at, updated_at)
VALUES (
    'inactive.customer@test.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'David',
    'Brown',
    '+62812345684',
    'CUSTOMER',
    FALSE,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Insert Customer User 8 (Auction Enthusiast)
INSERT INTO users (email, password, first_name, last_name, phone_number, role, is_active, is_email_verified, created_at, updated_at)
VALUES (
    'auction.lover@autowarehouse.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'Lisa',
    'Rodriguez',
    '+62812345685',
    'CUSTOMER',
    TRUE,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
