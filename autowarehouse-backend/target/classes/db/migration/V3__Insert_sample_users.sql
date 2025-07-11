-- Insert sample users for testing
-- Password for all users is 'password123' (hashed with BCrypt)
-- BCrypt hash for 'password123': $2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi

-- Insert Admin User
INSERT INTO users (email, password, first_name, last_name, phone_number, role, is_active, is_email_verified, created_at, updated_at)
VALUES (
    'admin@autowarehouse.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'Admin',
    'AutoWarehouse',
    '+62812345678',
    'ADMIN',
    TRUE,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Insert Customer User 1
INSERT INTO users (email, password, first_name, last_name, phone_number, role, is_active, is_email_verified, created_at, updated_at)
VALUES (
    'customer@autowarehouse.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'John',
    'Doe',
    '+62812345679',
    'CUSTOMER',
    TRUE,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Insert Customer User 2
INSERT INTO users (email, password, first_name, last_name, phone_number, role, is_active, is_email_verified, created_at, updated_at)
VALUES (
    'jane.smith@gmail.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'Jane',
    'Smith',
    '+62812345680',
    'CUSTOMER',
    TRUE,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Insert Customer User 3
INSERT INTO users (email, password, first_name, last_name, phone_number, role, is_active, is_email_verified, created_at, updated_at)
VALUES (
    'budi.santoso@yahoo.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'Budi',
    'Santoso',
    '+62812345681',
    'CUSTOMER',
    TRUE,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
