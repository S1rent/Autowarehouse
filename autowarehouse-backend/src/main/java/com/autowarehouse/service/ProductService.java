package com.autowarehouse.service;

import com.autowarehouse.entity.Product;
import com.autowarehouse.entity.Category;
import com.autowarehouse.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        return Product.find("(lower(name) like ?1 or lower(description) like ?1 or lower(sku) like ?1) and isActive = true", 
                           "%" + query.toLowerCase() + "%").list();
    }

    public List<Product> searchAllProducts(String query) {
        return Product.find("(lower(name) like ?1 or lower(description) like ?1 or lower(sku) like ?1)", 
                           "%" + query.toLowerCase() + "%").list();
    }

    public List<Product> findProductsWithFilters(Long categoryId, String brand, BigDecimal minPrice, 
                                               BigDecimal maxPrice, String search, Boolean onSale, String status) {
        try {
            // If only search is provided with no status or "Semua Status" (empty string), use searchAllProducts
            if (search != null && !search.trim().isEmpty() && 
                categoryId == null && brand == null && minPrice == null && 
                maxPrice == null && onSale == null && 
                (status == null || status.trim().isEmpty())) {
                return searchAllProducts(search);
            }

            // If only search and specific status are provided, use appropriate search method
            if (search != null && !search.trim().isEmpty() && 
                categoryId == null && brand == null && minPrice == null && 
                maxPrice == null && onSale == null && 
                status != null && !status.trim().isEmpty()) {
                if (status.toLowerCase().equals("all") || status.toLowerCase().equals("semua")) {
                    return searchAllProducts(search);
                } else {
                    // For specific status filters, continue with complex query
                }
            }

            StringBuilder queryBuilder = new StringBuilder("1=1");
            List<Object> parameters = new ArrayList<>();
            int paramIndex = 1;

            // Category filter
            if (categoryId != null) {
                queryBuilder.append(" and category.id = ?").append(paramIndex++);
                parameters.add(categoryId);
            }

            // Brand filter
            if (brand != null && !brand.trim().isEmpty()) {
                queryBuilder.append(" and lower(brand) like ?").append(paramIndex++);
                parameters.add("%" + brand.toLowerCase() + "%");
            }

            // Price range filter
            if (minPrice != null) {
                queryBuilder.append(" and price >= ?").append(paramIndex++);
                parameters.add(minPrice);
            }
            if (maxPrice != null) {
                queryBuilder.append(" and price <= ?").append(paramIndex++);
                parameters.add(maxPrice);
            }

            // Search filter - Fixed parameter indexing
            if (search != null && !search.trim().isEmpty()) {
                queryBuilder.append(" and (lower(name) like ?").append(paramIndex)
                           .append(" or lower(description) like ?").append(paramIndex + 1)
                           .append(" or lower(sku) like ?").append(paramIndex + 2)
                           .append(" or lower(brand) like ?").append(paramIndex + 3).append(")");
                String searchParam = "%" + search.toLowerCase() + "%";
                parameters.add(searchParam);
                parameters.add(searchParam);
                parameters.add(searchParam);
                parameters.add(searchParam);
                paramIndex += 4;
            }

            // On sale filter
            if (onSale != null && onSale) {
                queryBuilder.append(" and saleStartDate <= ?").append(paramIndex)
                           .append(" and saleEndDate >= ?").append(paramIndex + 1);
                LocalDateTime now = LocalDateTime.now();
                parameters.add(now);
                parameters.add(now);
                paramIndex += 2;
            }

            // Status filter
            if (status != null && !status.trim().isEmpty()) {
                switch (status.toLowerCase()) {
                    case "active":
                        queryBuilder.append(" and isActive = true and stockQuantity > 0");
                        break;
                    case "inactive":
                        queryBuilder.append(" and isActive = false");
                        break;
                    case "out_of_stock":
                        queryBuilder.append(" and stockQuantity = 0");
                        break;
                    case "all":
                    case "semua":
                        // No filter for status - show all products regardless of status
                        break;
                    default:
                        // No additional filter for unknown status
                        break;
                }
            } else {
                // When status is null or empty (which means "Semua Status" is selected)
                // Don't add any status filter - show all products regardless of status
                // This handles the case where frontend sends empty string for "Semua Status"
            }

            // Add ordering
            queryBuilder.append(" order by createdAt desc");

            System.out.println("Query: " + queryBuilder.toString());
            System.out.println("Parameters: " + parameters);

            return Product.find(queryBuilder.toString(), parameters.toArray()).list();
        } catch (Exception e) {
            System.err.println("Error in findProductsWithFilters: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback: if search is provided, try simple search
            if (search != null && !search.trim().isEmpty()) {
                System.out.println("Falling back to simple search");
                // Check if status is null/empty (Semua Status) or "all"/"semua" to use searchAllProducts
                if (status == null || status.trim().isEmpty() || 
                    status.toLowerCase().equals("all") || status.toLowerCase().equals("semua")) {
                    return searchAllProducts(search);
                } else {
                    return searchProducts(search);
                }
            }
            
            // If no search, return appropriate products based on status
            if (status == null || status.trim().isEmpty() || 
                status.toLowerCase().equals("all") || status.toLowerCase().equals("semua")) {
                return findAll();
            } else {
                return findActiveProducts();
            }
        }
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
