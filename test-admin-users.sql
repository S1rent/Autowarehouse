-- Test query to verify admin users exist
SELECT 
    id,
    email,
    first_name,
    last_name,
    role,
    is_active,
    is_email_verified,
    created_at
FROM users 
WHERE role = 'ADMIN'
ORDER BY created_at;
