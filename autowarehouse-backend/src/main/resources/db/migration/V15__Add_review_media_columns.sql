-- Add missing columns to reviews table for media support
ALTER TABLE reviews ADD COLUMN IF NOT EXISTS image_urls TEXT DEFAULT '[]';
ALTER TABLE reviews ADD COLUMN IF NOT EXISTS video_urls TEXT DEFAULT '[]';
