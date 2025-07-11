# AutoWarehouse Login Credentials - Quick Reference

## 🔑 Admin Login Credentials

### Primary Admin
- **Email**: `admin@autowarehouse.com`
- **Password**: `admin123`
- **Access**: Full admin panel access

### Super Admin
- **Email**: `superadmin@autowarehouse.com`
- **Password**: `admin123`
- **Access**: Full admin panel access

### New Admin (Latest)
- **Email**: `newadmin@autowarehouse.com`
- **Password**: `admin123`
- **Access**: Full admin panel access

## 👤 Customer Login Credentials

### Primary Customer (Main Testing)
- **Email**: `customer@autowarehouse.com`
- **Password**: `password123`
- **Status**: ✅ Active, ✅ Verified

### Premium Customer
- **Email**: `premium.customer@autowarehouse.com`
- **Password**: `password123`
- **Status**: ✅ Active, ✅ Verified

### International Customer
- **Email**: `international@gmail.com`
- **Password**: `password123`
- **Status**: ✅ Active, ✅ Verified

### Auction Enthusiast
- **Email**: `auction.lover@autowarehouse.com`
- **Password**: `password123`
- **Status**: ✅ Active, ✅ Verified

### Local Customers
- **Email**: `jane.smith@gmail.com`
- **Password**: `password123`
- **Status**: ✅ Active, ✅ Verified

- **Email**: `budi.santoso@yahoo.com`
- **Password**: `password123`
- **Status**: ✅ Active, ✅ Verified

## ⚠️ Special Test Cases

### Unverified Email Customer
- **Email**: `newcustomer@outlook.com`
- **Password**: `password123`
- **Status**: ✅ Active, ❌ Email NOT Verified
- **Use**: Test email verification flow

### Inactive Customer
- **Email**: `inactive.customer@test.com`
- **Password**: `password123`
- **Status**: ❌ INACTIVE, ✅ Verified
- **Use**: Test inactive account handling

## 🚀 Quick Start Testing

### For Admin Testing:
```
Login URL: http://localhost:8080/admin
Email: admin@autowarehouse.com
Password: admin123
```

### For Customer Testing:
```
Login URL: http://localhost:8080/login
Email: customer@autowarehouse.com
Password: password123
```

### For Auction Testing:
```
Login URL: http://localhost:8080/login
Email: auction.lover@autowarehouse.com
Password: password123
```

## 📊 Database Status
- ✅ V3__Insert_sample_users.sql (4 users)
- ✅ V4__Insert_additional_test_users.sql (6 users)
- ✅ V5__Insert_new_admin_user.sql (1 admin user)
- **Total**: 11 users (3 admins + 8 customers)

## 🔐 Security Information
- **Password**: Most users use `password123`, new admin uses `admin123`
- **Hash**: BCrypt with 10 rounds
- **Hash Values**: 
  - `password123`: `$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi`
  - `admin123`: `$2a$12$jJvDhWXa4fDOTKYN62R78ebYxyjtn8P.I08dRBHEw3gTHxtDQmdFu`

---
*Last Updated: January 11, 2025*
*All credentials are for testing purposes only*
