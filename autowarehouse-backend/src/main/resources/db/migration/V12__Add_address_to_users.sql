-- Add address column to users table
ALTER TABLE users ADD COLUMN IF NOT EXISTS address VARCHAR(500);
