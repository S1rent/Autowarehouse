# Test Credentials for AutoWarehouse

## Admin Credentials

### Primary Admin
- **Email**: admin@autowarehouse.com
- **Password**: password123
- **Role**: ADMIN
- **Name**: Admin AutoWarehouse
- **Phone**: +62812345678
- **Status**: Active, Email Verified

### Super Admin
- **Email**: superadmin@autowarehouse.com
- **Password**: password123
- **Role**: ADMIN
- **Name**: Super Admin
- **Phone**: +62812345677
- **Status**: Active, Email Verified

## Customer Credentials

### Customer 1 (Primary Test User)
- **Email**: customer@autowarehouse.com
- **Password**: password123
- **Role**: CUSTOMER
- **Name**: John Doe
- **Phone**: +62812345679
- **Status**: Active, Email Verified

### Customer 2 (Local Customer)
- **Email**: jane.smith@gmail.com
- **Password**: password123
- **Role**: CUSTOMER
- **Name**: Jane Smith
- **Phone**: +62812345680
- **Status**: Active, Email Verified

### Customer 3 (Indonesian Customer)
- **Email**: budi.santoso@yahoo.com
- **Password**: password123
- **Role**: CUSTOMER
- **Name**: Budi Santoso
- **Phone**: +62812345681
- **Status**: Active, Email Verified

### Customer 4 (Premium Customer)
- **Email**: premium.customer@autowarehouse.com
- **Password**: password123
- **Role**: CUSTOMER
- **Name**: Sarah Johnson
- **Phone**: +62812345682
- **Status**: Active, Email Verified

### Customer 5 (International Customer)
- **Email**: international@gmail.com
- **Password**: password123
- **Role**: CUSTOMER
- **Name**: Michael Chen
- **Phone**: +65987654321
- **Status**: Active, Email Verified

### Customer 6 (New Customer - Unverified)
- **Email**: newcustomer@outlook.com
- **Password**: password123
- **Role**: CUSTOMER
- **Name**: Emma Wilson
- **Phone**: +62812345683
- **Status**: Active, Email NOT Verified

### Customer 7 (Inactive Customer)
- **Email**: inactive.customer@test.com
- **Password**: password123
- **Role**: CUSTOMER
- **Name**: David Brown
- **Phone**: +62812345684
- **Status**: INACTIVE, Email Verified

### Customer 8 (Auction Enthusiast)
- **Email**: auction.lover@autowarehouse.com
- **Password**: password123
- **Role**: CUSTOMER
- **Name**: Lisa Rodriguez
- **Phone**: +62812345685
- **Status**: Active, Email Verified

## Database Information
- **Password Hash**: `$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi`
- **Migration Files**: 
  - V3__Insert_sample_users.sql (Original users)
  - V4__Insert_additional_test_users.sql (Additional users)

## Testing Scenarios

### Admin Testing
- **Primary Admin Functions**: Use `admin@autowarehouse.com`
- **Super Admin Functions**: Use `superadmin@autowarehouse.com`
- **Admin Category Management**: Use either admin account
- **User Management**: Use either admin account

### Customer Testing
- **Standard Customer Flow**: Use `customer@autowarehouse.com`
- **Premium Customer Features**: Use `premium.customer@autowarehouse.com`
- **International Customer**: Use `international@gmail.com`
- **Email Verification Testing**: Use `newcustomer@outlook.com`
- **Inactive User Testing**: Use `inactive.customer@test.com`
- **Auction Features**: Use `auction.lover@autowarehouse.com`
- **Multiple Customer Scenarios**: Use any combination of active customers

### Authentication Testing
- **Login Success**: Use any active, verified user
- **Login with Unverified Email**: Use `newcustomer@outlook.com`
- **Login with Inactive Account**: Use `inactive.customer@test.com`
- **Role-based Access**: Test admin vs customer permissions

### Application Features Testing
- **Shopping Cart**: Use any active customer
- **Order Management**: Use any active customer
- **Auction Bidding**: Use `auction.lover@autowarehouse.com` or any active customer
- **Wishlist**: Use any active customer
- **Reviews**: Use any active customer
- **Notifications**: Use any active user
