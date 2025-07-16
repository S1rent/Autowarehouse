-- Add missing columns to notifications table to match the Notification entity

-- Add reference_id column for linking notifications to related entities
ALTER TABLE notifications ADD COLUMN reference_id BIGINT;

-- Add reference_type column to specify the type of related entity
ALTER TABLE notifications ADD COLUMN reference_type VARCHAR(50);

-- Add action_url column for clickable notifications
ALTER TABLE notifications ADD COLUMN action_url VARCHAR(500);

-- Add icon_class column for notification icons
ALTER TABLE notifications ADD COLUMN icon_class VARCHAR(50);

-- Update the existing data column to match entity field name (if needed)
-- The entity uses 'data' but we'll keep it as is since it's JSONB

-- Add indexes for better performance on the new columns
CREATE INDEX idx_notifications_reference ON notifications(reference_id, reference_type);
CREATE INDEX idx_notifications_type ON notifications(type);
