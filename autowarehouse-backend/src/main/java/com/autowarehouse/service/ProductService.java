package com.autowarehouse.service;

import com.autowarehouse.entity.Product;
import com.autowarehouse.entity.Category;
import com.autowarehouse.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ProductService {

    @Transactional
    public Product createProduct(@Valid Product product) {
        // Generate SKU if not provided
        if (product.sku == null || product.sku.trim().isEmpty()) {
            product.sku = generateSKU(product);
        }

        // Check if SKU already exists
        if (Product.findBySku(product.sku) != null) {
            throw new IllegalArgumentException("SKU already exists");
        }

        // Set default values
        product.isActive = true;
        product.viewCount = 0L;
        product.salesCount = 0;
        product.rating = BigDecimal.ZERO;
        product.reviewCount = 0;

        product.persist();
        
        // Update category product count
        if (product.category != null) {
            updateCategoryProductCount(product.category);
        }
        
        return product;
    }

    @Transactional
    public Product updateProduct(Long productId, @Valid Product updatedProduct) {
        Product product = Product.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        Category oldCategory = product.category;

        // Update fields
        product.name = updatedProduct.name;
        product.description = updatedProduct.description;
        product.price = updatedProduct.price;
        product.originalPrice = updatedProduct.originalPrice;
        product.stockQuantity = updatedProduct.stockQuantity;
        product.brand = updatedProduct.brand;
        product.model = updatedProduct.model;
        product.specifications = updatedProduct.specifications;
        product.features = updatedProduct.features;
        product.imageUrls = updatedProduct.imageUrls;
        product.category = updatedProduct.category;
        product.saleStartDate = updatedProduct.saleStartDate;
        product.saleEndDate = updatedProduct.saleEndDate;
        product.isActive = updatedProduct.isActive;

        product.persist();

        // Update category product counts if category changed
        if (oldCategory != null && !oldCategory.equals(product.category)) {
            updateCategoryProductCount(oldCategory);
        }
        if (product.category != null) {
            updateCategoryProductCount(product.category);
        }

        return product;
    }

    public Product findById(Long id) {
        return Product.findById(id);
    }

    public Product findBySku(String sku) {
        return Product.findBySku(sku);
    }

    public List<Product> findAll() {
        return Product.listAll();
    }

    public List<Product> findActiveProducts() {
        return Product.findActiveProducts();
    }

    public List<Product> findByCategory(Category category) {
        return Product.findByCategory(category);
    }

    public List<Product> findByBrand(String brand) {
        return Product.findByBrand(brand);
    }

    public List<Product> findInPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return Product.findInPriceRange(minPrice, maxPrice);
    }

    public List<Product> findOnSale() {
        return Product.find("saleStartDate <= ?1 and saleEndDate >= ?1 and isActive = true", LocalDateTime.now()).list();
    }

    public List<Product> findLowStock(int threshold) {
        return Product.find("stockQuantity <= ?1 and stockQuantity > 0 and isActive = true", threshold).list();
    }

    public List<Product> findOutOfStock() {
        return Product.find("stockQuantity = 0 and isActive = true").list();
    }

    public List<Product> findPopularProducts(int limit) {
        return Product.find("isActive = true order by salesCount desc").page(0, limit).list();
    }

    public List<Product> findTopRatedProducts(int limit) {
        return Product.find("isActive = true and reviewCount > 0 order by rating desc").page(0, limit).list();
    }

    public List<Product> findRecentProducts(int days) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(days);
        return Product.find("createdAt >= ?1 and isActive = true order by createdAt desc", cutoff).list();
    }

    public List<Product> searchProducts(String query) {
        return Product.find("lower(name) like ?1 or lower(description) like ?1 and isActive = true", 
                           "%" + query.toLowerCase() + "%").list();
    }

    public List<Product> findFeaturedProducts(int limit) {
        return findPopularProducts(limit);
    }

    public List<Product> findOnSaleProducts(int limit) {
        return Product.find("saleStartDate <= ?1 and saleEndDate >= ?1 and isActive = true order by createdAt desc", 
                           LocalDateTime.now()).page(0, limit).list();
    }

    @Transactional
    public void incrementViewCount(Long productId) {
        Product product = Product.findById(productId);
        if (product != null) {
            product.viewCount++;
            product.persist();
        }
    }

    @Transactional
    public void updateStock(Long productId, int newStock) {
        Product product = Product.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        product.stockQuantity = newStock;
        product.persist();
    }

    @Transactional
    public void decreaseStock(Long productId, int quantity) {
        Product product = Product.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        if (product.stockQuantity < quantity) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        product.stockQuantity -= quantity;
        product.persist();
    }

    @Transactional
    public void increaseStock(Long productId, int quantity) {
        Product product = Product.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        product.stockQuantity += quantity;
        product.persist();
    }

    @Transactional
    public void updateRating(Long productId, BigDecimal newRating, int reviewCount) {
        Product product = Product.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        product.rating = newRating;
        product.reviewCount = reviewCount;
        product.persist();
    }

    @Transactional
    public void incrementSalesCount(Long productId, int quantity) {
        Product product = Product.findById(productId);
        if (product != null) {
            product.salesCount += quantity;
            product.persist();
        }
    }

    @Transactional
    public void activateProduct(Long productId) {
        Product product = Product.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        product.isActive = true;
        product.persist();
    }

    @Transactional
    public void deactivateProduct(Long productId) {
        Product product = Product.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        product.isActive = false;
        product.persist();
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = Product.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        Category category = product.category;
        product.delete();

        // Update category product count
        if (category != null) {
            updateCategoryProductCount(category);
        }
    }

    @Transactional
    public void setSalePrice(Long productId, BigDecimal salePrice, LocalDateTime startDate, LocalDateTime endDate) {
        Product product = Product.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        if (product.originalPrice == null) {
            product.originalPrice = product.price;
        }
        
        product.price = salePrice;
        product.saleStartDate = startDate;
        product.saleEndDate = endDate;
        product.persist();
    }

    @Transactional
    public void clearSale(Long productId) {
        Product product = Product.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        if (product.originalPrice != null) {
            product.price = product.originalPrice;
            product.originalPrice = null;
        }
        product.saleStartDate = null;
        product.saleEndDate = null;
        product.persist();
    }

    public long getTotalProductsCount() {
        return Product.count();
    }

    public long getActiveProductsCount() {
        return Product.countActiveProducts();
    }

    public long getLowStockCount(int threshold) {
        return Product.countLowStock(threshold);
    }

    public long getOutOfStockCount() {
        return Product.countOutOfStock();
    }

    public boolean isProductAvailable(Long productId, int quantity) {
        Product product = Product.findById(productId);
        return product != null && product.isActive && product.stockQuantity >= quantity;
    }

    private String generateSKU(Product product) {
        String prefix = product.category != null ? 
            product.category.name.substring(0, Math.min(3, product.category.name.length())).toUpperCase() : 
            "PRD";
        
        long timestamp = System.currentTimeMillis() % 100000;
        return prefix + "-" + timestamp;
    }

    @Transactional
    public void updateCategoryProductCount(Category category) {
        if (category != null) {
            long count = Product.count("category = ?1 and isActive = true", category);
            // Note: Category entity needs productCount field to be added
            // For now, we'll skip this update to avoid compilation error
            // category.productCount = (int) count;
            // category.persist();
        }
    }
}
