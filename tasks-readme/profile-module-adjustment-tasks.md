# Profile Module Adjustment Tasks

## 📋 **OVERVIEW**
Profile module memiliki 2 sub-module utama yang perlu diintegrasikan dengan backend:
1. **Change Password** - Sudah ada endpoint tapi perlu perbaikan frontend integration
2. **Update Personal Information** - Sudah ada endpoint tapi perlu perbaikan frontend integration

## 🎯 **CURRENT STATUS ANALYSIS**

### ✅ **BACKEND - SUDAH ADA**
- **UserResource.java**: Endpoint `/api/users/change-password` dan `/api/users/profile/{id}` sudah tersedia
- **UserService.java**: Method `changePassword()` dan `updateProfile()` sudah implemented
- **Authentication**: JWT token validation sudah ada
- **Password Hashing**: BCrypt sudah digunakan untuk security

### ❌ **FRONTEND - PERLU PERBAIKAN**
- **API Service**: Belum ada method untuk change password dan update profile
- **Profile Form**: Hardcoded functionality, belum terintegrasi dengan backend
- **Error Handling**: Belum ada proper error handling untuk API calls
- **Loading States**: Belum ada loading indicators
- **Validation**: Belum ada client-side validation

---

## 🚀 **TASK BREAKDOWN**

### **PHASE 1: BACKEND FIXES & IMPROVEMENTS** 
**Priority: HIGH** | **Estimated: 2-3 hours**

#### **Task 1.1: Fix Backend DTO Issues** ⚠️
**Status**: CRITICAL - Missing DTOs causing compilation errors

**Issues Found**:
- `UpdateProfileRequest` class ada di UserResource tapi tidak di import
- `ChangePasswordRequest` class ada tapi tidak di import  
- Missing imports untuk DTO classes

**Action Items**:
- [ ] Move DTO classes ke package `com.autowarehouse.dto`
- [ ] Create proper DTO files:
  - `UpdateProfileRequest.java`
  - `ChangePasswordRequest.java`
- [ ] Add proper validation annotations
- [ ] Update UserResource imports

**Files to Modify**:
```
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java (NEW)
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java (NEW)
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java (UPDATE)
```

#### **Task 1.2: Enhance Backend Validation** 🔒
**Status**: MEDIUM - Improve security and validation

**Action Items**:
- [ ] Add proper validation annotations to DTOs
- [ ] Implement password strength validation
- [ ] Add rate limiting for password change attempts
- [ ] Improve error messages

**Files to Modify**:
```
autowarehouse-backend/src/main/java/com/autowarehouse/dto/UpdateProfileRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/dto/ChangePasswordRequest.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
```

#### **Task 1.3: Add User Context Validation** 🛡️
**Status**: HIGH - Security improvement

**Current Issue**: Change password endpoint menggunakan `userId` dari request body, seharusnya dari JWT token

**Action Items**:
- [ ] Modify change password endpoint untuk extract user ID dari JWT
- [ ] Add security check untuk ensure user can only update own profile
- [ ] Update endpoint signature

**Files to Modify**:
```
autowarehouse-backend/src/main/java/com/autowarehouse/resource/UserResource.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/UserService.java
```

---

### **PHASE 2: FRONTEND API INTEGRATION**
**Priority: HIGH** | **Estimated: 3-4 hours**

#### **Task 2.1: Add Missing API Methods** 🔌
**Status**: CRITICAL - Required for functionality

**Action Items**:
- [ ] Add `changePassword()` method to ApiService
- [ ] Add `updateProfile()` method to ApiService  
- [ ] Add proper TypeScript interfaces
- [ ] Add error handling

**Files to Modify**:
```
autowarehouse-frontend/src/services/api.ts
```

**New Methods to Add**:
```typescript
// Change Password
async changePassword(currentPassword: string, newPassword: string): Promise<{ message: string }>

// Update Profile  
async updateProfile(userId: number, profileData: UpdateProfileRequest): Promise<User>
```

#### **Task 2.2: Create Profile Store** 🏪
**Status**: MEDIUM - Better state management

**Action Items**:
- [ ] Create `useProfileStore()` untuk manage profile state
- [ ] Add loading states
- [ ] Add error handling
- [ ] Integrate dengan existing auth store

**Files to Create**:
```
autowarehouse-frontend/src/stores/profile.ts (NEW)
```

---

### **PHASE 3: FRONTEND UI IMPROVEMENTS**
**Priority: MEDIUM** | **Estimated: 4-5 hours**

#### **Task 3.1: Enhance Change Password Form** 🔐
**Status**: HIGH - Core functionality

**Current Issues**:
- Form tidak terintegrasi dengan backend
- Tidak ada validation
- Tidak ada loading states
- Tidak ada proper error handling

**Action Items**:
- [ ] Integrate form dengan API service
- [ ] Add client-side validation:
  - Current password required
  - New password strength validation
  - Confirm password matching
- [ ] Add loading spinner during API call
- [ ] Add success/error notifications
- [ ] Clear form after successful change

**Files to Modify**:
```
autowarehouse-frontend/src/views/UserProfileView.vue
```

#### **Task 3.2: Enhance Update Profile Form** 👤
**Status**: HIGH - Core functionality

**Current Issues**:
- Form tidak terintegrasi dengan backend
- Edit mode tidak functional
- Tidak ada validation
- Tidak ada loading states

**Action Items**:
- [ ] Integrate form dengan API service
- [ ] Fix edit mode toggle functionality
- [ ] Add client-side validation:
  - Email format validation
  - Phone number format validation
  - Required field validation
- [ ] Add loading spinner during API call
- [ ] Add success/error notifications
- [ ] Update auth store after successful update

**Files to Modify**:
```
autowarehouse-frontend/src/views/UserProfileView.vue
```

#### **Task 3.3: Add Form Validation Components** ✅
**Status**: MEDIUM - UX improvement

**Action Items**:
- [ ] Create reusable validation components
- [ ] Add real-time validation feedback
- [ ] Add password strength indicator
- [ ] Add form field error states

**Files to Create**:
```
autowarehouse-frontend/src/components/forms/ValidationInput.vue (NEW)
autowarehouse-frontend/src/components/forms/PasswordStrengthIndicator.vue (NEW)
```

---

### **PHASE 4: SECURITY & UX ENHANCEMENTS**
**Priority: LOW** | **Estimated: 2-3 hours**

#### **Task 4.1: Add Security Features** 🛡️
**Status**: LOW - Nice to have

**Action Items**:
- [ ] Add "Logout from all devices" after password change
- [ ] Add email notification for profile changes
- [ ] Add confirmation dialog for sensitive actions
- [ ] Add session timeout handling

#### **Task 4.2: Improve User Experience** ✨
**Status**: LOW - Polish

**Action Items**:
- [ ] Add auto-save for profile changes
- [ ] Add keyboard shortcuts
- [ ] Add better mobile responsiveness
- [ ] Add accessibility improvements

---

## 🔧 **TECHNICAL REQUIREMENTS**

### **Backend Dependencies**
```xml
<!-- Already available in pom.xml -->
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-security</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-elytron-security-common</artifactId>
</dependency>
```

### **Frontend Dependencies**
```json
// Already available in package.json
"axios": "^1.x.x",
"vue": "^3.x.x",
"pinia": "^2.x.x"
```

---

## 📝 **API ENDPOINTS REFERENCE**

### **Change Password**
```http
POST /api/users/change-password
Authorization: Bearer {jwt_token}
Content-Type: application/json

{
  "userId": 123,
  "currentPassword": "oldpass123",
  "newPassword": "newpass456"
}
```

### **Update Profile**
```http
PUT /api/users/profile/{userId}
Authorization: Bearer {jwt_token}
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe", 
  "phoneNumber": "+1234567890"
}
```

---

## 🧪 **TESTING CHECKLIST**

### **Backend Testing**
- [ ] Test change password dengan valid credentials
- [ ] Test change password dengan invalid current password
- [ ] Test update profile dengan valid data
- [ ] Test update profile dengan invalid data
- [ ] Test JWT token validation
- [ ] Test rate limiting (if implemented)

### **Frontend Testing**
- [ ] Test form validation
- [ ] Test API integration
- [ ] Test loading states
- [ ] Test error handling
- [ ] Test success notifications
- [ ] Test responsive design

---

## 🚨 **CRITICAL ISSUES TO FIX FIRST**

1. **Backend DTO Missing** - Compilation errors
2. **Security Issue** - User ID should come from JWT, not request body
3. **Frontend API Integration** - No methods for profile operations
4. **Form Functionality** - Currently hardcoded, not working

---

## 📊 **ESTIMATED TIMELINE**

| Phase | Tasks | Estimated Time | Priority |
|-------|-------|----------------|----------|
| Phase 1 | Backend Fixes | 2-3 hours | HIGH |
| Phase 2 | API Integration | 3-4 hours | HIGH |
| Phase 3 | UI Improvements | 4-5 hours | MEDIUM |
| Phase 4 | Enhancements | 2-3 hours | LOW |
| **TOTAL** | **All Tasks** | **11-15 hours** | - |

---

## 🎯 **SUCCESS CRITERIA**

### **Minimum Viable Product (MVP)**
- [ ] User dapat change password dengan validasi proper
- [ ] User dapat update profile information
- [ ] Form validation bekerja dengan baik
- [ ] Error handling dan success notifications
- [ ] Security: JWT validation dan user context

### **Full Feature Complete**
- [ ] All MVP criteria met
- [ ] Loading states dan UX improvements
- [ ] Email notifications
- [ ] Advanced security features
- [ ] Mobile responsive
- [ ] Accessibility compliant

---

## 📚 **DOCUMENTATION UPDATES NEEDED**

- [ ] Update API documentation
- [ ] Update frontend component documentation
- [ ] Update security guidelines
- [ ] Update testing procedures

---

**Last Updated**: July 16, 2025
**Status**: Ready for Implementation
**Next Action**: Start with Phase 1 - Backend Fixes
