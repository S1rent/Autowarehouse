-- Add GENERAL category to the support tickets category constraint
ALTER TABLE support_tickets DROP CONSTRAINT chk_category;
ALTER TABLE support_tickets ADD CONSTRAINT chk_category CHECK (category IN ('GENERAL', 'PAYMENT', 'SHIPPING', 'ACCOUNT', 'RETURNS', 'TECHNICAL', 'OTHER'));
