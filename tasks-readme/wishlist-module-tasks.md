# Wishlist Module - Task Breakdown

## 📋 Current Status Analysis

### ✅ What's Already Implemented:

#### **Frontend:**
1. **Wishlist Store** (`/src/stores/wishlist.ts`)
   - ✅ Pinia store dengan state management
   - ✅ Basic CRUD operations (add, remove, toggle)
   - ✅ LocalStorage persistence (temporary solution)
   - ✅ Loading states dan error handling
   - ❌ **ISSUE**: Menggunakan localStorage, bukan API calls

2. **Wishlist View** (`/src/views/WishlistView.vue`)
   - ✅ Complete UI dengan modern design
   - ✅ Empty state handling
   - ✅ Product cards dengan rating, price, stock status
   - ✅ Add to cart functionality
   - ✅ Remove from wishlist functionality
   - ✅ Add all to cart feature
   - ❌ **ISSUE**: Menggunakan mock data, bukan real data dari store

3. **Router Configuration**
   - ✅ Route `/wishlist` sudah terdaftar
   - ✅ Protected route (requires authentication)

4. **Navbar Integration**
   - ✅ Wishlist icon di UserNavbar
   - ✅ Wishlist count badge
   - ✅ Mobile responsive menu
   - ❌ **ISSUE**: Count masih hardcoded (ref(0))

5. **Product Integration**
   - ✅ Wishlist toggle di ProductsView.vue
   - ✅ Wishlist toggle di ProductDetailView.vue
   - ✅ Heart icon dengan visual feedback
   - ✅ Authentication check sebelum toggle

#### **Backend:**
1. **Database Entity** (`/src/main/java/com/autowarehouse/entity/WishlistItem.java`)
   - ✅ Complete entity dengan proper relationships
   - ✅ User-Product many-to-many relationship
   - ✅ Unique constraint (user_id, product_id)
   - ✅ Static finder methods
   - ✅ Timestamp tracking

2. **Entity Relationships**
   - ✅ User entity memiliki `List<WishlistItem> wishlistItems`
   - ✅ Product entity memiliki `List<WishlistItem> wishlistItems`

3. **Missing Components**
   - ❌ **MISSING**: WishlistService.java
   - ❌ **MISSING**: WishlistResource.java (REST endpoints)

---

## 🎯 Tasks to Complete

### **TASK 1: Backend API Implementation**

#### **1.1 Create WishlistService.java**
**Location**: `autowarehouse-backend/src/main/java/com/autowarehouse/service/WishlistService.java`

**Required Methods**:
```java
@ApplicationScoped
public class WishlistService {
    // Get user's wishlist
    public List<WishlistItem> getUserWishlist(Long userId)
    
    // Add product to wishlist
    public WishlistItem addToWishlist(Long userId, Long productId)
    
    // Remove product from wishlist
    public void removeFromWishlist(Long userId, Long productId)
    
    // Check if product is in wishlist
    public boolean isInWishlist(Long userId, Long productId)
    
    // Get wishlist count for user
    public long getWishlistCount(Long userId)
    
    // Clear entire wishlist
    public void clearWishlist(Long userId)
    
    // Get wishlist with product details
    public List<WishlistItemResponse> getUserWishlistWithProducts(Long userId)
}
```

**Business Logic**:
- Validate user exists
- Validate product exists and is active
- Handle duplicate prevention
- Proper error handling
- Transaction management

#### **1.2 Create WishlistResource.java**
**Location**: `autowarehouse-backend/src/main/java/com/autowarehouse/resource/WishlistResource.java`

**Required Endpoints**:
```java
@Path("/api/wishlist")
public class WishlistResource {
    // GET /api/wishlist/user/{userId} - Get user wishlist
    // POST /api/wishlist/add - Add to wishlist
    // DELETE /api/wishlist/remove - Remove from wishlist
    // GET /api/wishlist/user/{userId}/count - Get wishlist count
    // DELETE /api/wishlist/user/{userId}/clear - Clear wishlist
    // GET /api/wishlist/user/{userId}/check/{productId} - Check if in wishlist
}
```

**Request/Response DTOs**:
```java
public static class AddToWishlistRequest {
    public Long userId;
    public Long productId;
}

public static class WishlistItemResponse {
    public Long id;
    public ProductResponse product;
    public LocalDateTime createdAt;
}
```

### **TASK 2: Frontend API Integration**

#### **2.1 Update API Service**
**Location**: `autowarehouse-frontend/src/services/api.ts`

**Add Wishlist Types**:
```typescript
export interface WishlistItem {
  id: number
  product: Product
  createdAt: string
}

export interface AddToWishlistRequest {
  userId: number
  productId: number
}
```

**Add Wishlist Methods**:
```typescript
// Wishlist APIs
async getUserWishlist(userId: number): Promise<WishlistItem[]>
async addToWishlist(request: AddToWishlistRequest): Promise<WishlistItem>
async removeFromWishlist(userId: number, productId: number): Promise<{ message: string }>
async getWishlistCount(userId: number): Promise<{ count: number }>
async clearWishlist(userId: number): Promise<{ message: string }>
async checkInWishlist(userId: number, productId: number): Promise<{ exists: boolean }>
```

#### **2.2 Update Wishlist Store**
**Location**: `autowarehouse-frontend/src/stores/wishlist.ts`

**Changes Required**:
- Replace localStorage logic dengan API calls
- Add proper error handling
- Add loading states untuk setiap operation
- Integrate dengan auth store untuk userId
- Add optimistic updates untuk better UX

**Key Methods to Update**:
```typescript
const loadWishlist = async () => {
  // Use apiService.getUserWishlist(authStore.user.id)
  // Remove localStorage logic
}

const addToWishlist = async (productId: number) => {
  // Use apiService.addToWishlist({ userId, productId })
  // Add optimistic update
}

const removeFromWishlist = async (productId: number) => {
  // Use apiService.removeFromWishlist(userId, productId)
  // Add optimistic update
}
```

### **TASK 3: Frontend UI Improvements**

#### **3.1 Update WishlistView.vue**
**Location**: `autowarehouse-frontend/src/views/WishlistView.vue`

**Changes Required**:
- Remove mock data
- Connect to wishlist store
- Add loading states
- Add error handling
- Implement real add to cart functionality
- Add pagination untuk large wishlists
- Add sorting options (date added, price, name)
- Add bulk operations (select multiple, remove selected)

#### **3.2 Update UserNavbar.vue**
**Location**: `autowarehouse-frontend/src/components/UserNavbar.vue`

**Changes Required**:
- Connect wishlistCount ke wishlist store
- Add real-time updates
- Remove hardcoded ref(0)

```typescript
import { useWishlistStore } from '@/stores/wishlist'

const wishlistStore = useWishlistStore()
const wishlistCount = computed(() => wishlistStore.wishlistCount)

// Load wishlist count on component mount
onMounted(() => {
  if (authStore.isAuthenticated) {
    wishlistStore.loadWishlist()
  }
})
```

### **TASK 4: Enhanced Features**

#### **4.1 Wishlist Analytics**
**Backend Enhancement**:
- Track most wishlisted products
- Wishlist conversion rate (wishlist → purchase)
- Popular wishlist items by category

**Admin Dashboard Integration**:
- Wishlist statistics
- Most wishlisted products report
- User engagement metrics

#### **4.2 Wishlist Notifications**
**Features**:
- Price drop notifications
- Back in stock notifications
- Sale/discount notifications for wishlisted items
- Wishlist reminder emails

#### **4.3 Wishlist Sharing**
**Features**:
- Share wishlist dengan friends/family
- Public wishlist URLs
- Wishlist gift suggestions

#### **4.4 Advanced Wishlist Management**
**Features**:
- Multiple wishlist categories (e.g., "Birthday", "Christmas", "Upgrade List")
- Wishlist notes/comments
- Priority levels (High, Medium, Low)
- Price tracking history

### **TASK 5: Testing & Validation**

#### **5.1 Backend Testing**
- Unit tests untuk WishlistService
- Integration tests untuk WishlistResource
- Database constraint testing
- Performance testing untuk large wishlists

#### **5.2 Frontend Testing**
- Component testing untuk WishlistView
- Store testing untuk wishlist operations
- E2E testing untuk complete wishlist flow
- Mobile responsiveness testing

#### **5.3 User Experience Testing**
- Wishlist performance dengan 100+ items
- Concurrent user testing
- Cross-browser compatibility
- Accessibility testing

---

## 🚀 Detailed Implementation Steps

### **PRIORITY 1: Backend Implementation (CRITICAL)**

#### **Step 1.1: Create WishlistService.java**
**File**: `autowarehouse-backend/src/main/java/com/autowarehouse/service/WishlistService.java`

**Sub-steps**:
1. **Create basic service class structure**
   ```java
   @ApplicationScoped
   public class WishlistService {
       // Basic class setup with dependencies
   }
   ```

2. **Implement getUserWishlist method**
   - Query WishlistItem by userId
   - Return List<WishlistItem> with product details
   - Handle empty wishlist case

3. **Implement addToWishlist method**
   - Validate user exists
   - Validate product exists and is active
   - Check for duplicates
   - Create new WishlistItem
   - Handle transaction

4. **Implement removeFromWishlist method**
   - Find WishlistItem by userId and productId
   - Delete item if exists
   - Handle not found case

5. **Implement utility methods**
   - `isInWishlist(userId, productId)`
   - `getWishlistCount(userId)`
   - `clearWishlist(userId)`

6. **Add error handling and validation**
   - User validation
   - Product validation
   - Duplicate handling
   - Transaction management

#### **Step 1.2: Create WishlistResource.java**
**File**: `autowarehouse-backend/src/main/java/com/autowarehouse/resource/WishlistResource.java`

**Sub-steps**:
1. **Create basic resource class structure**
   ```java
   @Path("/api/wishlist")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public class WishlistResource {
       @Inject WishlistService wishlistService;
   }
   ```

2. **Create DTO classes**
   - `AddToWishlistRequest`
   - `WishlistItemResponse`
   - `ErrorResponse`
   - `SuccessResponse`

3. **Implement GET /api/wishlist/user/{userId}**
   - Get user's complete wishlist
   - Return WishlistItemResponse list
   - Handle empty wishlist

4. **Implement POST /api/wishlist/add**
   - Add product to wishlist
   - Validate request body
   - Return created WishlistItemResponse

5. **Implement DELETE /api/wishlist/remove**
   - Remove product from wishlist
   - Validate userId and productId
   - Return success message

6. **Implement GET /api/wishlist/user/{userId}/count**
   - Get wishlist item count
   - Return count object

7. **Implement utility endpoints**
   - `GET /api/wishlist/user/{userId}/check/{productId}` - Check if in wishlist
   - `DELETE /api/wishlist/user/{userId}/clear` - Clear entire wishlist

8. **Add comprehensive error handling**
   - Try-catch blocks for all endpoints
   - Proper HTTP status codes
   - Meaningful error messages

#### **Step 1.3: Test Backend Implementation**
**Sub-steps**:
1. **Test WishlistService methods**
   - Unit test each method
   - Test edge cases
   - Test error scenarios

2. **Test WishlistResource endpoints**
   - Test all HTTP endpoints
   - Test request/response formats
   - Test error responses

3. **Integration testing**
   - Test with real database
   - Test with existing User/Product entities
   - Test concurrent operations

---

### **PRIORITY 2: Frontend API Integration**

#### **Step 2.1: Update API Service**
**File**: `autowarehouse-frontend/src/services/api.ts`

**Sub-steps**:
1. **Add WishlistItem interface**
   ```typescript
   export interface WishlistItem {
     id: number
     product: Product
     createdAt: string
   }
   ```

2. **Add request/response types**
   ```typescript
   export interface AddToWishlistRequest {
     userId: number
     productId: number
   }
   ```

3. **Implement getUserWishlist method**
   ```typescript
   async getUserWishlist(userId: number): Promise<WishlistItem[]>
   ```

4. **Implement addToWishlist method**
   ```typescript
   async addToWishlist(request: AddToWishlistRequest): Promise<WishlistItem>
   ```

5. **Implement removeFromWishlist method**
   ```typescript
   async removeFromWishlist(userId: number, productId: number): Promise<{ message: string }>
   ```

6. **Implement utility methods**
   - `getWishlistCount(userId)`
   - `checkInWishlist(userId, productId)`
   - `clearWishlist(userId)`

7. **Add error handling**
   - Proper error types
   - Network error handling
   - Response validation

#### **Step 2.2: Update Wishlist Store**
**File**: `autowarehouse-frontend/src/stores/wishlist.ts`

**Sub-steps**:
1. **Import required dependencies**
   ```typescript
   import { useAuthStore } from '@/stores/auth'
   import { apiService, type WishlistItem } from '@/services/api'
   ```

2. **Update state structure**
   - Change from `Product[]` to `WishlistItem[]`
   - Add proper typing
   - Remove localStorage references

3. **Refactor loadWishlist method**
   - Replace localStorage with API call
   - Add proper error handling
   - Add loading states

4. **Refactor addToWishlist method**
   - Use API service
   - Add optimistic updates
   - Handle auth check
   - Update local state

5. **Refactor removeFromWishlist method**
   - Use API service
   - Add optimistic updates
   - Update local state

6. **Update computed properties**
   - Fix `isInWishlist` to work with new data structure
   - Update `wishlistCount` calculation

7. **Add new utility methods**
   - `syncWithServer()` - Force sync with backend
   - `handleAuthChange()` - Clear wishlist on logout

8. **Add comprehensive error handling**
   - Network errors
   - Authentication errors
   - Validation errors

---

### **PRIORITY 3: UI Connection & Real Data**

#### **Step 3.1: Update WishlistView.vue**
**File**: `autowarehouse-frontend/src/views/WishlistView.vue`

**Sub-steps**:
1. **Import wishlist store**
   ```typescript
   import { useWishlistStore } from '@/stores/wishlist'
   import { useCartStore } from '@/stores/cart'
   import { useAuthStore } from '@/stores/auth'
   ```

2. **Remove mock data**
   - Delete `wishlistItems` ref with mock data
   - Remove all hardcoded sample items

3. **Connect to wishlist store**
   ```typescript
   const wishlistStore = useWishlistStore()
   const wishlistItems = computed(() => wishlistStore.wishlistItems)
   ```

4. **Add loading states**
   - Show loading spinner while fetching
   - Show loading on individual operations
   - Disable buttons during operations

5. **Update data transformation**
   - Transform `WishlistItem[]` to display format
   - Handle product image URLs
   - Calculate prices and discounts

6. **Fix addToCart functionality**
   - Connect to real cart store
   - Add proper error handling
   - Show success/error messages

7. **Fix removeFromWishlist functionality**
   - Connect to wishlist store method
   - Add confirmation dialog
   - Handle optimistic updates

8. **Add error handling UI**
   - Error messages for failed operations
   - Retry buttons
   - Network error handling

9. **Add empty state improvements**
   - Better empty state design
   - Call-to-action buttons
   - Loading state for empty check

#### **Step 3.2: Update UserNavbar.vue**
**File**: `autowarehouse-frontend/src/components/UserNavbar.vue`

**Sub-steps**:
1. **Import wishlist store**
   ```typescript
   import { useWishlistStore } from '@/stores/wishlist'
   ```

2. **Remove hardcoded wishlist count**
   - Delete `const wishlistCount = ref(0)`
   - Replace with store connection

3. **Connect to real wishlist count**
   ```typescript
   const wishlistStore = useWishlistStore()
   const wishlistCount = computed(() => wishlistStore.wishlistCount)
   ```

4. **Add lifecycle hooks**
   ```typescript
   onMounted(async () => {
     if (authStore.isAuthenticated) {
       await wishlistStore.loadWishlist()
     }
   })
   ```

5. **Add auth state watching**
   ```typescript
   watch(() => authStore.isAuthenticated, (isAuth) => {
     if (isAuth) {
       wishlistStore.loadWishlist()
     } else {
       wishlistStore.clearWishlist()
     }
   })
   ```

6. **Add loading state for count**
   - Show loading indicator while fetching count
   - Handle count update animations

7. **Add error handling**
   - Handle wishlist load errors
   - Fallback count display

---

### **PRIORITY 4: Testing & Validation**

#### **Step 4.1: Backend Testing**
**Sub-steps**:
1. **Unit test WishlistService**
   - Test each method individually
   - Mock dependencies
   - Test edge cases and error scenarios

2. **Integration test WishlistResource**
   - Test HTTP endpoints
   - Test request/response formats
   - Test authentication/authorization

3. **Database testing**
   - Test unique constraints
   - Test cascade operations
   - Test performance with large datasets

#### **Step 4.2: Frontend Testing**
**Sub-steps**:
1. **Test wishlist store**
   - Test all store methods
   - Test state management
   - Test error handling

2. **Test WishlistView component**
   - Test rendering with different data states
   - Test user interactions
   - Test loading and error states

3. **Test navbar integration**
   - Test count updates
   - Test authentication flow
   - Test responsive behavior

#### **Step 4.3: End-to-End Testing**
**Sub-steps**:
1. **Test complete wishlist flow**
   - Add to wishlist from product pages
   - View wishlist page
   - Remove from wishlist
   - Add to cart from wishlist

2. **Test authentication scenarios**
   - Login/logout behavior
   - Wishlist persistence
   - Unauthorized access handling

3. **Test error scenarios**
   - Network failures
   - Server errors
   - Invalid data handling

#### **Step 4.4: Performance & UX Testing**
**Sub-steps**:
1. **Performance testing**
   - Large wishlist handling
   - Concurrent user testing
   - Mobile performance

2. **UX testing**
   - Loading states
   - Error messages
   - Responsive design
   - Accessibility

---

## 📋 **Implementation Checklist**

### **Backend (Priority 1)**
- [ ] **Step 1.1.1**: Create WishlistService class structure
- [ ] **Step 1.1.2**: Implement getUserWishlist method
- [ ] **Step 1.1.3**: Implement addToWishlist method
- [ ] **Step 1.1.4**: Implement removeFromWishlist method
- [ ] **Step 1.1.5**: Implement utility methods
- [ ] **Step 1.1.6**: Add error handling and validation
- [ ] **Step 1.2.1**: Create WishlistResource class structure
- [ ] **Step 1.2.2**: Create DTO classes
- [ ] **Step 1.2.3**: Implement GET wishlist endpoint
- [ ] **Step 1.2.4**: Implement POST add endpoint
- [ ] **Step 1.2.5**: Implement DELETE remove endpoint
- [ ] **Step 1.2.6**: Implement GET count endpoint
- [ ] **Step 1.2.7**: Implement utility endpoints
- [ ] **Step 1.2.8**: Add comprehensive error handling
- [ ] **Step 1.3.1**: Test WishlistService methods
- [ ] **Step 1.3.2**: Test WishlistResource endpoints
- [ ] **Step 1.3.3**: Integration testing

### **Frontend API (Priority 2)**
- [ ] **Step 2.1.1**: Add WishlistItem interface
- [ ] **Step 2.1.2**: Add request/response types
- [ ] **Step 2.1.3**: Implement getUserWishlist method
- [ ] **Step 2.1.4**: Implement addToWishlist method
- [ ] **Step 2.1.5**: Implement removeFromWishlist method
- [ ] **Step 2.1.6**: Implement utility methods
- [ ] **Step 2.1.7**: Add error handling
- [ ] **Step 2.2.1**: Import required dependencies
- [ ] **Step 2.2.2**: Update state structure
- [ ] **Step 2.2.3**: Refactor loadWishlist method
- [ ] **Step 2.2.4**: Refactor addToWishlist method
- [ ] **Step 2.2.5**: Refactor removeFromWishlist method
- [ ] **Step 2.2.6**: Update computed properties
- [ ] **Step 2.2.7**: Add new utility methods
- [ ] **Step 2.2.8**: Add comprehensive error handling

### **UI Integration (Priority 3)**
- [ ] **Step 3.1.1**: Import wishlist store
- [ ] **Step 3.1.2**: Remove mock data
- [ ] **Step 3.1.3**: Connect to wishlist store
- [ ] **Step 3.1.4**: Add loading states
- [ ] **Step 3.1.5**: Update data transformation
- [ ] **Step 3.1.6**: Fix addToCart functionality
- [ ] **Step 3.1.7**: Fix removeFromWishlist functionality
- [ ] **Step 3.1.8**: Add error handling UI
- [ ] **Step 3.1.9**: Add empty state improvements
- [ ] **Step 3.2.1**: Import wishlist store
- [ ] **Step 3.2.2**: Remove hardcoded wishlist count
- [ ] **Step 3.2.3**: Connect to real wishlist count
- [ ] **Step 3.2.4**: Add lifecycle hooks
- [ ] **Step 3.2.5**: Add auth state watching
- [ ] **Step 3.2.6**: Add loading state for count
- [ ] **Step 3.2.7**: Add error handling

### **Testing (Priority 4)**
- [ ] **Step 4.1.1**: Unit test WishlistService
- [ ] **Step 4.1.2**: Integration test WishlistResource
- [ ] **Step 4.1.3**: Database testing
- [ ] **Step 4.2.1**: Test wishlist store
- [ ] **Step 4.2.2**: Test WishlistView component
- [ ] **Step 4.2.3**: Test navbar integration
- [ ] **Step 4.3.1**: Test complete wishlist flow
- [ ] **Step 4.3.2**: Test authentication scenarios
- [ ] **Step 4.3.3**: Test error scenarios
- [ ] **Step 4.4.1**: Performance testing
- [ ] **Step 4.4.2**: UX testing

---

## ⏱️ **Estimated Time per Step**

### **Backend (Priority 1)**: ~6-8 hours
- WishlistService: 2-3 hours
- WishlistResource: 2-3 hours  
- Testing: 2 hours

### **Frontend API (Priority 2)**: ~4-6 hours
- API Service: 1-2 hours
- Wishlist Store: 3-4 hours

### **UI Integration (Priority 3)**: ~4-6 hours
- WishlistView: 3-4 hours
- UserNavbar: 1-2 hours

### **Testing (Priority 4)**: ~4-6 hours
- Backend Testing: 2-3 hours
- Frontend Testing: 2-3 hours

**Total Estimated Time**: 18-26 hours (2-3 working days)

---

## 🔧 Technical Considerations

### **Database Performance**
- Index pada `user_id` dan `product_id` columns
- Consider pagination untuk large wishlists
- Soft delete vs hard delete untuk wishlist items

### **Caching Strategy**
- Cache wishlist count di frontend
- Redis caching untuk frequently accessed wishlists
- Invalidation strategy saat product changes

### **Security**
- Validate user can only access own wishlist
- Rate limiting untuk wishlist operations
- Input validation dan sanitization

### **Mobile Optimization**
- Touch-friendly wishlist interactions
- Optimized loading untuk mobile networks
- Offline wishlist viewing capability

---

## 📊 Success Metrics

### **Functional Metrics**
- ✅ All wishlist CRUD operations working
- ✅ Real-time wishlist count updates
- ✅ Proper error handling dan user feedback
- ✅ Mobile responsive design

### **Performance Metrics**
- ⚡ Wishlist load time < 500ms
- ⚡ Add/remove operations < 200ms
- ⚡ Support 1000+ items per wishlist
- ⚡ Concurrent user support

### **User Experience Metrics**
- 📱 Mobile-friendly interface
- 🎨 Consistent design dengan rest of app
- 🔄 Smooth animations dan transitions
- ♿ Accessibility compliance

---

## 🐛 Known Issues to Address

### **Current Issues**:
1. **WishlistView.vue menggunakan mock data** - Needs real store integration
2. **Wishlist store menggunakan localStorage** - Needs API integration
3. **Navbar wishlist count hardcoded** - Needs real-time updates
4. **No backend API endpoints** - Needs complete backend implementation
5. **No error handling untuk network failures** - Needs robust error handling
6. **No loading states di UI** - Needs loading indicators
7. **No pagination untuk large wishlists** - Needs pagination implementation

### **Potential Edge Cases**:
- Product deleted while in wishlist
- User account deactivated
- Concurrent wishlist modifications
- Network connectivity issues
- Large wishlist performance
- Cross-device wishlist sync

---

## 📝 Notes

### **Business Requirements**:
- Wishlist should persist across sessions
- Users can add unlimited items to wishlist
- Wishlist items should show current price/availability
- Easy conversion from wishlist to cart
- Admin visibility into wishlist analytics

### **Technical Constraints**:
- Must work dengan existing auth system
- Must integrate dengan existing cart system
- Must support existing product catalog
- Must maintain performance dengan large datasets
- Must be mobile responsive

### **Future Enhancements**:
- AI-powered wishlist recommendations
- Social wishlist features
- Wishlist-based marketing campaigns
- Integration dengan external price comparison
- Wishlist export/import functionality

---

**Last Updated**: July 16, 2025
**Status**: Ready for Implementation
**Estimated Effort**: 2-3 days for Phase 1, 1-2 weeks for complete implementation
