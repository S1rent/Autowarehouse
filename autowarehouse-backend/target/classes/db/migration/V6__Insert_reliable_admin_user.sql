-- Insert reliable admin user for system access
-- This migration ensures we have a working admin account
-- Password: 'admin123' (BCrypt hashed)
-- BCrypt hash: $2a$12$jJvDhWXa4fDOTKYN62R78ebYxyjtn8P.I08dRBHEw3gTHxtDQmdFu

-- First, ensure no duplicate admin exists with this email
DELETE FROM users WHERE email = 'admin@autowarehouse.com';

-- Insert the main admin user
INSERT INTO users (
    email, 
    password, 
    first_name, 
    last_name, 
    phone_number, 
    role, 
    is_active, 
    is_email_verified, 
    created_at, 
    updated_at
) VALUES (
    'admin@autowarehouse.com',
    '$2a$12$jJvDhWXa4fDOTKYN62R78ebYxyjtn8P.I08dRBHEw3gTHxtDQmdFu',
    'System',
    'Administrator',
    '+62812345678',
    'ADMIN',
    TRUE,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Insert a backup admin user
INSERT INTO users (
    email, 
    password, 
    first_name, 
    last_name, 
    phone_number, 
    role, 
    is_active, 
    is_email_verified, 
    created_at, 
    updated_at
) VALUES (
    'superadmin@autowarehouse.com',
    '$2a$12$jJvDhWXa4fDOTKYN62R78ebYxyjtn8P.I08dRBHEw3gTHxtDQmdFu',
    'Super',
    'Administrator',
    '+62812345677',
    'ADMIN',
    TRUE,
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
) ON CONFLICT (email) DO UPDATE SET
    password = EXCLUDED.password,
    first_name = EXCLUDED.first_name,
    last_name = EXCLUDED.last_name,
    phone_number = EXCLUDED.phone_number,
    role = EXCLUDED.role,
    is_active = EXCLUDED.is_active,
    is_email_verified = EXCLUDED.is_email_verified,
    updated_at = CURRENT_TIMESTAMP;

-- Verify the admin users were created
-- This will show in the migration logs
DO $$
DECLARE
    admin_count INTEGER;
BEGIN
    SELECT COUNT(*) INTO admin_count FROM users WHERE role = 'ADMIN';
    RAISE NOTICE 'Total admin users after migration: %', admin_count;
END $$;
