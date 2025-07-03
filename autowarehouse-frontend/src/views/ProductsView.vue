<template>
  <div class="products-view">
    <!-- Header -->
    <header class="products-header">
      <div class="container">
        <h1 class="page-title">All Products</h1>
        <p class="page-subtitle">Discover our complete range of computer hardware</p>
      </div>
    </header>

    <!-- Filters and Search -->
    <section class="filters-section">
      <div class="container">
        <div class="filters-container">
          <div class="search-filter">
            <input 
              type="text" 
              placeholder="Search products..." 
              class="search-input"
              v-model="searchQuery"
            />
          </div>
          
          <div class="category-filter">
            <select v-model="selectedCategory" class="filter-select">
              <option value="">All Categories</option>
              <option value="laptops">Laptops</option>
              <option value="desktops">Desktops</option>
              <option value="components">Components</option>
              <option value="accessories">Accessories</option>
            </select>
          </div>

          <div class="price-filter">
            <select v-model="priceRange" class="filter-select">
              <option value="">All Prices</option>
              <option value="0-100">$0 - $100</option>
              <option value="100-500">$100 - $500</option>
              <option value="500-1000">$500 - $1000</option>
              <option value="1000+">$1000+</option>
            </select>
          </div>

          <div class="sort-filter">
            <select v-model="sortBy" class="filter-select">
              <option value="name">Sort by Name</option>
              <option value="price-low">Price: Low to High</option>
              <option value="price-high">Price: High to Low</option>
              <option value="rating">Highest Rated</option>
            </select>
          </div>
        </div>
      </div>
    </section>

    <!-- Products Grid -->
    <section class="products-section">
      <div class="container">
        <div class="products-grid">
          <div class="product-card" v-for="product in filteredProducts" :key="product.id">
            <div class="product-image">
              <div class="product-placeholder">
                <span>{{ product.name.charAt(0) }}</span>
              </div>
              <div class="product-badge" v-if="product.badge">{{ product.badge }}</div>
              <div class="product-actions">
                <button class="action-btn wishlist-btn" :class="{ active: product.inWishlist }">
                  <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                    <path d="M20.84 4.61C20.3292 4.099 19.7228 3.69364 19.0554 3.41708C18.3879 3.14052 17.6725 2.99817 16.95 2.99817C16.2275 2.99817 15.5121 3.14052 14.8446 3.41708C14.1772 3.69364 13.5708 4.099 13.06 4.61L12 5.67L10.94 4.61C9.9083 3.5783 8.50903 2.9987 7.05 2.9987C5.59096 2.9987 4.19169 3.5783 3.16 4.61C2.1283 5.6417 1.5487 7.04097 1.5487 8.5C1.5487 9.95903 2.1283 11.3583 3.16 12.39L12 21.23L20.84 12.39C21.351 11.8792 21.7563 11.2728 22.0329 10.6053C22.3095 9.93789 22.4518 9.22248 22.4518 8.5C22.4518 7.77752 22.3095 7.06211 22.0329 6.39467C21.7563 5.72723 21.351 5.1208 20.84 4.61V4.61Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                  </svg>
                </button>
                <button class="action-btn quick-view-btn">
                  <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                    <path d="M1 12S5 4 12 4S23 12 23 12S19 20 12 20S1 12 1 12Z" stroke="currentColor" stroke-width="2"/>
                    <circle cx="12" cy="12" r="3" stroke="currentColor" stroke-width="2"/>
                  </svg>
                </button>
              </div>
            </div>
            <div class="product-info">
              <h3 class="product-name">{{ product.name }}</h3>
              <div class="product-rating">
                <div class="stars">
                  <span v-for="i in 5" :key="i" :class="{ filled: i <= product.rating }">★</span>
                </div>
                <span class="rating-count">({{ product.reviews }})</span>
              </div>
              <p class="product-description">{{ product.description }}</p>
              <div class="product-price">
                <span class="current-price">${{ product.price }}</span>
                <span class="original-price" v-if="product.originalPrice">${{ product.originalPrice }}</span>
              </div>
              <div class="product-buttons">
                <button class="btn btn-primary btn-small add-to-cart-btn">
                  Add to Cart
                </button>
                <button class="btn btn-outline btn-small">
                  View Details
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Load More -->
        <div class="load-more-section" v-if="hasMoreProducts">
          <button class="btn btn-outline btn-large" @click="loadMoreProducts">
            Load More Products
          </button>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'

// Filter states
const searchQuery = ref('')
const selectedCategory = ref('')
const priceRange = ref('')
const sortBy = ref('name')

// Pagination
const currentPage = ref(1)
const productsPerPage = 12
const hasMoreProducts = ref(true)

// Sample products data
const allProducts = reactive([
  {
    id: 1,
    name: 'Gaming Laptop RTX 4070',
    description: 'High-performance gaming laptop with RTX 4070 graphics',
    price: 1299,
    originalPrice: 1499,
    rating: 4,
    reviews: 124,
    category: 'laptops',
    badge: 'Sale',
    inWishlist: false
  },
  {
    id: 2,
    name: 'Desktop PC Intel i7',
    description: 'Powerful desktop computer for work and gaming',
    price: 899,
    rating: 5,
    reviews: 89,
    category: 'desktops',
    badge: null,
    inWishlist: true
  },
  {
    id: 3,
    name: 'Graphics Card RTX 4080',
    description: 'Latest generation graphics card for 4K gaming',
    price: 799,
    originalPrice: 899,
    rating: 5,
    reviews: 156,
    category: 'components',
    badge: 'Hot',
    inWishlist: false
  },
  {
    id: 4,
    name: 'Mechanical Keyboard',
    description: 'RGB mechanical keyboard with Cherry MX switches',
    price: 149,
    rating: 4,
    reviews: 203,
    category: 'accessories',
    badge: null,
    inWishlist: false
  },
  {
    id: 5,
    name: 'Gaming Monitor 27"',
    description: '144Hz gaming monitor with 1ms response time',
    price: 299,
    rating: 4,
    reviews: 87,
    category: 'accessories',
    badge: null,
    inWishlist: true
  },
  {
    id: 6,
    name: 'SSD 1TB NVMe',
    description: 'High-speed NVMe SSD for faster boot times',
    price: 89,
    originalPrice: 120,
    rating: 5,
    reviews: 234,
    category: 'components',
    badge: 'Sale',
    inWishlist: false
  },
  {
    id: 7,
    name: 'Gaming Mouse',
    description: 'Precision gaming mouse with RGB lighting',
    price: 59,
    rating: 4,
    reviews: 145,
    category: 'accessories',
    badge: null,
    inWishlist: false
  },
  {
    id: 8,
    name: 'RAM 32GB DDR4',
    description: 'High-performance DDR4 memory kit',
    price: 179,
    rating: 5,
    reviews: 98,
    category: 'components',
    badge: null,
    inWishlist: false
  }
])

// Computed filtered products
const filteredProducts = computed(() => {
  let filtered = [...allProducts]

  // Search filter
  if (searchQuery.value) {
    filtered = filtered.filter(product =>
      product.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      product.description.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  // Category filter
  if (selectedCategory.value) {
    filtered = filtered.filter(product => product.category === selectedCategory.value)
  }

  // Price filter
  if (priceRange.value) {
    const [min, max] = priceRange.value.split('-').map(p => p.replace('+', ''))
    filtered = filtered.filter(product => {
      if (max) {
        return product.price >= parseInt(min) && product.price <= parseInt(max)
      } else {
        return product.price >= parseInt(min)
      }
    })
  }

  // Sort
  switch (sortBy.value) {
    case 'price-low':
      filtered.sort((a, b) => a.price - b.price)
      break
    case 'price-high':
      filtered.sort((a, b) => b.price - a.price)
      break
    case 'rating':
      filtered.sort((a, b) => b.rating - a.rating)
      break
    default:
      filtered.sort((a, b) => a.name.localeCompare(b.name))
  }

  return filtered.slice(0, currentPage.value * productsPerPage)
})

// Methods
const loadMoreProducts = () => {
  currentPage.value++
  if (filteredProducts.value.length >= allProducts.length) {
    hasMoreProducts.value = false
  }
}
</script>

<style scoped>
.products-view {
  min-height: 100vh;
  background: #f8fafc;
}

.products-header {
  background: white;
  padding: 3rem 0;
  border-bottom: 1px solid #e2e8f0;
}

.page-title {
  font-size: 2.5rem;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.page-subtitle {
  font-size: 1.125rem;
  color: var(--text-secondary);
}

.filters-section {
  background: white;
  padding: 2rem 0;
  border-bottom: 1px solid #e2e8f0;
}

.filters-container {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr;
  gap: 1rem;
  align-items: center;
}

.search-input,
.filter-select {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #e2e8f0;
  border-radius: 0.5rem;
  font-size: 0.875rem;
}

.search-input:focus,
.filter-select:focus {
  outline: none;
  border-color: var(--primary-color);
}

.products-section {
  padding: 3rem 0;
}

.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 2rem;
  margin-bottom: 3rem;
}

.product-card {
  background: white;
  border-radius: 1rem;
  overflow: hidden;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.product-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 10px 25px -3px rgba(0, 0, 0, 0.1);
}

.product-image {
  position: relative;
  height: 220px;
  background: #f1f5f9;
  overflow: hidden;
}

.product-placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  font-size: 4rem;
  font-weight: bold;
  color: #94a3b8;
}

.product-badge {
  position: absolute;
  top: 0.75rem;
  right: 0.75rem;
  background: #ef4444;
  color: white;
  padding: 0.25rem 0.75rem;
  border-radius: 1rem;
  font-size: 0.75rem;
  font-weight: 600;
}

.product-actions {
  position: absolute;
  top: 0.75rem;
  left: 0.75rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.product-card:hover .product-actions {
  opacity: 1;
}

.action-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 2.5rem;
  height: 2.5rem;
  background: white;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.2s ease;
}

.action-btn:hover {
  transform: scale(1.1);
}

.wishlist-btn.active {
  background: #ef4444;
  color: white;
}

.product-info {
  padding: 1.5rem;
}

.product-name {
  font-size: 1.125rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: var(--text-primary);
}

.product-rating {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 0.75rem;
}

.stars {
  display: flex;
  gap: 0.125rem;
}

.stars span {
  color: #e2e8f0;
  font-size: 1rem;
}

.stars span.filled {
  color: #fbbf24;
}

.rating-count {
  color: var(--text-secondary);
  font-size: 0.875rem;
}

.product-description {
  color: var(--text-secondary);
  font-size: 0.875rem;
  line-height: 1.5;
  margin-bottom: 1rem;
}

.product-price {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 1.5rem;
}

.current-price {
  font-size: 1.375rem;
  font-weight: 700;
  color: var(--primary-color);
}

.original-price {
  font-size: 1rem;
  color: var(--text-secondary);
  text-decoration: line-through;
}

.product-buttons {
  display: flex;
  gap: 0.5rem;
}

.product-buttons .btn {
  flex: 1;
}

.load-more-section {
  text-align: center;
}

/* Responsive */
@media (max-width: 1024px) {
  .filters-container {
    grid-template-columns: 1fr 1fr;
    gap: 1rem;
  }
  
  .search-filter {
    grid-column: 1 / -1;
  }
}

@media (max-width: 768px) {
  .filters-container {
    grid-template-columns: 1fr;
  }
  
  .products-grid {
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 1.5rem;
  }
  
  .product-buttons {
    flex-direction: column;
  }
}

@media (max-width: 480px) {
  .products-grid {
    grid-template-columns: 1fr;
  }
}
</style>
