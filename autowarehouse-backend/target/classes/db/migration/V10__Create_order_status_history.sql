-- Create order_status_history table to track order status changes over time
CREATE TABLE order_status_history (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    previous_status VARCHAR(50),
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    changed_by VARCHAR(255),
    notes VARCHAR(500),
    tracking_number VARCHAR(100),
    estimated_delivery TIMESTAMP,
    actual_delivery TIMESTAMP,
    
    CONSTRAINT fk_order_status_history_order 
        FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- Create indexes for better query performance
CREATE INDEX idx_order_status_history_order_id ON order_status_history(order_id);
CREATE INDEX idx_order_status_history_status ON order_status_history(status);
CREATE INDEX idx_order_status_history_changed_at ON order_status_history(changed_at);

-- Insert initial status history for existing orders
INSERT INTO order_status_history (order_id, status, previous_status, changed_at, changed_by, notes)
SELECT 
    id as order_id,
    status,
    NULL as previous_status,
    created_at as changed_at,
    'SYSTEM' as changed_by,
    'Initial order creation' as notes
FROM orders
WHERE status IS NOT NULL;

-- Update tracking numbers from orders table if they exist
UPDATE order_status_history 
SET tracking_number = o.tracking_number
FROM orders o 
WHERE order_status_history.order_id = o.id 
AND o.tracking_number IS NOT NULL 
AND order_status_history.status IN ('SHIPPED', 'DELIVERED');
