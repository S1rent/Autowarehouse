-- Ensure all required notification columns exist
-- This migration will add columns only if they don't exist

-- Add action_url column if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name = 'notifications' AND column_name = 'action_url') THEN
        ALTER TABLE notifications ADD COLUMN action_url VARCHAR(500);
    END IF;
END $$;

-- Add icon_class column if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name = 'notifications' AND column_name = 'icon_class') THEN
        ALTER TABLE notifications ADD COLUMN icon_class VARCHAR(50);
    END IF;
END $$;

-- Add reference_id column if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name = 'notifications' AND column_name = 'reference_id') THEN
        ALTER TABLE notifications ADD COLUMN reference_id BIGINT;
    END IF;
END $$;

-- Add reference_type column if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name = 'notifications' AND column_name = 'reference_type') THEN
        ALTER TABLE notifications ADD COLUMN reference_type VARCHAR(50);
    END IF;
END $$;

-- Add priority column if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name = 'notifications' AND column_name = 'priority') THEN
        ALTER TABLE notifications ADD COLUMN priority VARCHAR(20) DEFAULT 'NORMAL';
    END IF;
END $$;

-- Add expires_at column if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name = 'notifications' AND column_name = 'expires_at') THEN
        ALTER TABLE notifications ADD COLUMN expires_at TIMESTAMP;
    END IF;
END $$;

-- Create indexes if they don't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_notifications_reference') THEN
        CREATE INDEX idx_notifications_reference ON notifications(reference_id, reference_type);
    END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_notifications_type') THEN
        CREATE INDEX idx_notifications_type ON notifications(type);
    END IF;
END $$;
