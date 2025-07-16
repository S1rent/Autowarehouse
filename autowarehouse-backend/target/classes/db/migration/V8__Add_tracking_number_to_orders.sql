-- Add tracking_number column to orders table
ALTER TABLE orders ADD COLUMN tracking_number VARCHAR(255);

-- Create index for tracking_number for better performance
CREATE INDEX idx_orders_tracking_number ON orders(tracking_number);
