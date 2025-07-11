# User Test Data for AutoWarehouse Application

## Quick Reference for Login Testing

### Admin Users
```
Primary Admin:
Email: admin@autowarehouse.com
Password: password123

Super Admin:
Email: superadmin@autowarehouse.com
Password: password123
```

### Customer Users
```
Primary Customer:
Email: customer@autowarehouse.com
Password: password123

Premium Customer:
Email: premium.customer@autowarehouse.com
Password: password123

International Customer:
Email: international@gmail.com
Password: password123

Auction Enthusiast:
Email: auction.lover@autowarehouse.com
Password: password123

Local Customer:
Email: jane.smith@gmail.com
Password: password123

Indonesian Customer:
Email: budi.santoso@yahoo.com
Password: password123
```

### Special Test Cases
```
Unverified Email Customer:
Email: newcustomer@outlook.com
Password: password123
Note: Email is NOT verified - use for email verification testing

Inactive Customer:
Email: inactive.customer@test.com
Password: password123
Note: Account is INACTIVE - use for inactive user testing
```

## Complete User Database

| ID | Email | Name | Role | Phone | Status | Email Verified | Use Case |
|----|-------|------|------|-------|--------|----------------|----------|
| 1 | admin@autowarehouse.com | Admin AutoWarehouse | ADMIN | +62812345678 | Active | Yes | Primary admin testing |
| 2 | customer@autowarehouse.com | John Doe | CUSTOMER | +62812345679 | Active | Yes | Primary customer testing |
| 3 | jane.smith@gmail.com | Jane Smith | CUSTOMER | +62812345680 | Active | Yes | Local customer testing |
| 4 | budi.santoso@yahoo.com | Budi Santoso | CUSTOMER | +62812345681 | Active | Yes | Indonesian customer testing |
| 5 | superadmin@autowarehouse.com | Super Admin | ADMIN | +62812345677 | Active | Yes | Super admin testing |
| 6 | premium.customer@autowarehouse.com | Sarah Johnson | CUSTOMER | +62812345682 | Active | Yes | Premium customer testing |
| 7 | international@gmail.com | Michael Chen | CUSTOMER | +65987654321 | Active | Yes | International customer testing |
| 8 | newcustomer@outlook.com | Emma Wilson | CUSTOMER | +62812345683 | Active | No | Email verification testing |
| 9 | inactive.customer@test.com | David Brown | CUSTOMER | +62812345684 | Inactive | Yes | Inactive user testing |
| 10 | auction.lover@autowarehouse.com | Lisa Rodriguez | CUSTOMER | +62812345685 | Active | Yes | Auction feature testing |

## Testing Workflows

### Authentication Testing
1. **Successful Login**: Use any active, verified user
2. **Admin Login**: Use `admin@autowarehouse.com` or `superadmin@autowarehouse.com`
3. **Customer Login**: Use `customer@autowarehouse.com`
4. **Unverified Email**: Use `newcustomer@outlook.com`
5. **Inactive Account**: Use `inactive.customer@test.com`

### Feature Testing
1. **Admin Panel**: Login with admin accounts
2. **Shopping Cart**: Login with any active customer
3. **Order Management**: Login with any active customer
4. **Auction Features**: Login with `auction.lover@autowarehouse.com`
5. **International Features**: Login with `international@gmail.com`
6. **Premium Features**: Login with `premium.customer@autowarehouse.com`

### Multi-User Testing
- Use different customer accounts to test interactions
- Test admin managing customer accounts
- Test auction bidding between multiple customers
- Test notification system across different users

## Database Migration Status
- **V3__Insert_sample_users.sql**: Contains original 4 users (1 admin, 3 customers)
- **V4__Insert_additional_test_users.sql**: Contains additional 6 users (1 admin, 5 customers)
- **Total Users**: 10 users (2 admins, 8 customers)

## Password Information
- **Plain Text Password**: `password123`
- **BCrypt Hash**: `$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi`
- **Hash Rounds**: 10

## Usage Notes
- All users have the same password for testing convenience
- Use different users to test various scenarios and edge cases
- Admin users have full access to admin panel and management features
- Customer users have access to shopping, auction, and profile features
- Special test users (unverified, inactive) are for testing error handling and edge cases
