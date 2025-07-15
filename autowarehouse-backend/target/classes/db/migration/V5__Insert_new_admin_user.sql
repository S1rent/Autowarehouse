-- Insert new admin user for login testing
-- Password for user is 'admin123' (hashed with BCrypt)
-- BCrypt hash for 'admin123': $2a$12$jJvDhWXa4fDOTKYN62R78ebYxyjtn8P.I08dRBHEw3gTHxtDQmdFu

INSERT INTO users (email, password, first_name, last_name, phone_number, role, is_active, is_email_verified, created_at, updated_at)
VALUES (
    'newadmin@autowarehouse.com',
    '$2a$12$jJvDhWXa4fDOTKYN62R78ebYxyjtn8P.I08dRBHEw3gTHxtDQmdFu',
    'New',
    'Admin',
    '+62812345686',
    'ADMIN',
    TRUE,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
