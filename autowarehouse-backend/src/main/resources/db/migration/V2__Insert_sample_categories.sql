-- Insert sample categories
INSERT INTO categories (name, description, slug, parent_id, image_url, is_active, sort_order) VALUES
-- Root categories
('Processor', 'CPU dan processor untuk komputer', 'processor', NULL, 'https://images.unsplash.com/photo-1591799264318-7e6ef8ddb7ea?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80', TRUE, 1),
('Memory', 'RAM dan memory untuk komputer', 'memory', NULL, 'https://images.unsplash.com/photo-1562976540-1502c2145186?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80', TRUE, 2),
('Storage', 'SSD, HDD dan storage devices', 'storage', NULL, 'https://images.unsplash.com/photo-1597872200969-2b65d56bd16b?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80', TRUE, 3),
('Graphics Card', 'VGA Card dan GPU untuk gaming', 'graphics-card', NULL, 'https://images.unsplash.com/photo-1591488320449-011701bb6704?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80', TRUE, 4),
('Gaming Accessories', 'Keyboard, mouse, headset gaming', 'gaming-accessories', NULL, 'https://images.unsplash.com/photo-1541140532154-b024d705b90a?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80', TRUE, 5),
('Audio', 'Speaker, headphone, dan audio equipment', 'audio', NULL, 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80', TRUE, 6);

-- Get the IDs of root categories for subcategories
-- Processor subcategories
INSERT INTO categories (name, description, slug, parent_id, image_url, is_active, sort_order) VALUES
('Intel Processor', 'Processor Intel Core series', 'intel-processor', (SELECT id FROM categories WHERE slug = 'processor'), NULL, TRUE, 1),
('AMD Processor', 'Processor AMD Ryzen series', 'amd-processor', (SELECT id FROM categories WHERE slug = 'processor'), NULL, TRUE, 2);

-- Memory subcategories
INSERT INTO categories (name, description, slug, parent_id, image_url, is_active, sort_order) VALUES
('DDR4 RAM', 'Memory DDR4 untuk komputer', 'ddr4-ram', (SELECT id FROM categories WHERE slug = 'memory'), NULL, TRUE, 1),
('DDR5 RAM', 'Memory DDR5 generasi terbaru', 'ddr5-ram', (SELECT id FROM categories WHERE slug = 'memory'), NULL, TRUE, 2);

-- Storage subcategories
INSERT INTO categories (name, description, slug, parent_id, image_url, is_active, sort_order) VALUES
('SSD', 'Solid State Drive untuk performa tinggi', 'ssd', (SELECT id FROM categories WHERE slug = 'storage'), NULL, TRUE, 1),
('HDD', 'Hard Disk Drive untuk storage besar', 'hdd', (SELECT id FROM categories WHERE slug = 'storage'), NULL, TRUE, 2),
('NVMe SSD', 'NVMe SSD untuk performa maksimal', 'nvme-ssd', (SELECT id FROM categories WHERE slug = 'storage'), NULL, TRUE, 3);

-- Graphics Card subcategories
INSERT INTO categories (name, description, slug, parent_id, image_url, is_active, sort_order) VALUES
('NVIDIA GeForce', 'VGA Card NVIDIA GeForce series', 'nvidia-geforce', (SELECT id FROM categories WHERE slug = 'graphics-card'), NULL, TRUE, 1),
('AMD Radeon', 'VGA Card AMD Radeon series', 'amd-radeon', (SELECT id FROM categories WHERE slug = 'graphics-card'), NULL, TRUE, 2);

-- Gaming Accessories subcategories
INSERT INTO categories (name, description, slug, parent_id, image_url, is_active, sort_order) VALUES
('Gaming Keyboard', 'Keyboard mechanical untuk gaming', 'gaming-keyboard', (SELECT id FROM categories WHERE slug = 'gaming-accessories'), NULL, TRUE, 1),
('Gaming Mouse', 'Mouse gaming dengan sensor presisi', 'gaming-mouse', (SELECT id FROM categories WHERE slug = 'gaming-accessories'), NULL, TRUE, 2),
('Gaming Headset', 'Headset gaming dengan microphone', 'gaming-headset', (SELECT id FROM categories WHERE slug = 'gaming-accessories'), NULL, TRUE, 3);

-- Audio subcategories
INSERT INTO categories (name, description, slug, parent_id, image_url, is_active, sort_order) VALUES
('Speakers', 'Speaker untuk komputer dan gaming', 'speakers', (SELECT id FROM categories WHERE slug = 'audio'), NULL, TRUE, 1),
('Headphones', 'Headphone untuk musik dan gaming', 'headphones', (SELECT id FROM categories WHERE slug = 'audio'), NULL, TRUE, 2);
