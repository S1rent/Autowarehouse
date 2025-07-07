package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
public class Product extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 200)
    public String name;

    @Column(columnDefinition = "TEXT")
    public String description;

    @Column(name = "short_description")
    @Size(max = 500)
    public String shortDescription;

    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    public BigDecimal price;

    @Column(name = "original_price", precision = 12, scale = 2)
    public BigDecimal originalPrice;

    @Column(name = "stock_quantity", nullable = false)
    @NotNull
    public Integer stockQuantity = 0;

    @Column(name = "min_stock_level")
    public Integer minStockLevel = 5;

    @Column(nullable = false)
    @Size(max = 100)
    public String sku;

    @Column(nullable = false)
    @Size(max = 100)
    public String brand;

    @Column(nullable = false)
    @Size(max = 100)
    public String model;

    @Column(columnDefinition = "TEXT")
    public String specifications;

    @Column(name = "is_active")
    public Boolean isActive = true;

    @Column(name = "is_featured")
    public Boolean isFeatured = false;

    @Column(name = "is_on_sale")
    public Boolean isOnSale = false;

    @Column(name = "sale_start_date")
    public LocalDateTime saleStartDate;

    @Column(name = "sale_end_date")
    public LocalDateTime saleEndDate;

    @Column(name = "weight_kg", precision = 8, scale = 3)
    public BigDecimal weightKg;

    @Column(name = "dimensions")
    @Size(max = 100)
    public String dimensions;

    @Column(name = "warranty_months")
    public Integer warrantyMonths;

    @Column(name = "average_rating", precision = 3, scale = 2)
    public BigDecimal averageRating = BigDecimal.ZERO;

    @Column(name = "total_reviews")
    public Integer totalReviews = 0;

    @Column(name = "total_sold")
    public Integer totalSold = 0;

    @Column(name = "view_count")
    public Long viewCount = 0L;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    public List<String> imageUrls;

    @ElementCollection
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag")
    public List<String> tags;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    public Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Review> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<WishlistItem> wishlistItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<CartItem> cartItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<OrderItem> orderItems;

    // Constructors
    public Product() {}

    public Product(String name, String description, BigDecimal price, String sku, String brand, String model, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.sku = sku;
        this.brand = brand;
        this.model = model;
        this.category = category;
    }

    // Static finder methods
    public static List<Product> findActiveProducts() {
        return find("isActive", true).list();
    }

    public static List<Product> findByCategory(Category category) {
        return find("category = ?1 and isActive = true", category).list();
    }

    public static List<Product> findByCategoryId(Long categoryId) {
        return find("category.id = ?1 and isActive = true", categoryId).list();
    }

    public static List<Product> findByBrand(String brand) {
        return find("brand = ?1 and isActive = true", brand).list();
    }

    public static List<Product> findFeaturedProducts() {
        return find("isFeatured = true and isActive = true").list();
    }

    public static List<Product> findOnSaleProducts() {
        return find("isOnSale = true and isActive = true and saleStartDate <= ?1 and (saleEndDate is null or saleEndDate >= ?1)", 
                   LocalDateTime.now()).list();
    }

    public static List<Product> findLowStockProducts() {
        return find("stockQuantity <= minStockLevel and isActive = true").list();
    }

    public static List<Product> searchByName(String searchTerm) {
        return find("lower(name) like ?1 and isActive = true", "%" + searchTerm.toLowerCase() + "%").list();
    }

    public static List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return find("price >= ?1 and price <= ?2 and isActive = true", minPrice, maxPrice).list();
    }

    public static Product findBySku(String sku) {
        return find("sku", sku).firstResult();
    }

    // Helper methods
    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public boolean isLowStock() {
        return stockQuantity <= minStockLevel;
    }

    public boolean isOnSaleNow() {
        LocalDateTime now = LocalDateTime.now();
        return isOnSale && 
               (saleStartDate == null || saleStartDate.isBefore(now) || saleStartDate.isEqual(now)) &&
               (saleEndDate == null || saleEndDate.isAfter(now) || saleEndDate.isEqual(now));
    }

    public BigDecimal getCurrentPrice() {
        return isOnSaleNow() && originalPrice != null ? originalPrice : price;
    }

    public BigDecimal getDiscountAmount() {
        if (isOnSaleNow() && originalPrice != null) {
            return originalPrice.subtract(price);
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getDiscountPercentage() {
        if (isOnSaleNow() && originalPrice != null && originalPrice.compareTo(BigDecimal.ZERO) > 0) {
            return getDiscountAmount().divide(originalPrice, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
        }
        return BigDecimal.ZERO;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void updateStock(int quantity) {
        this.stockQuantity += quantity;
        if (this.stockQuantity < 0) {
            this.stockQuantity = 0;
        }
    }

    public void updateRating(BigDecimal newRating, int newReviewCount) {
        this.averageRating = newRating;
        this.totalReviews = newReviewCount;
    }

    public void incrementSoldCount(int quantity) {
        this.totalSold += quantity;
    }

    public String getPrimaryImageUrl() {
        return imageUrls != null && !imageUrls.isEmpty() ? imageUrls.get(0) : null;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", sku='" + sku + '\'' +
                ", brand='" + brand + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", isActive=" + isActive +
                '}';
    }
}
