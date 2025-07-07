package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
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

    @Column(nullable = false, length = 200)
    @NotBlank
    @Size(max = 200)
    public String name;

    @Column(columnDefinition = "TEXT")
    public String description;

    @Column(name = "short_description", length = 500)
    @Size(max = 500)
    public String shortDescription;

    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal price;

    @Column(name = "original_price", precision = 12, scale = 2)
    public BigDecimal originalPrice;

    @Column(name = "stock_quantity", nullable = false)
    @NotNull
    public Integer stockQuantity = 0;

    @Column(name = "min_stock_level")
    public Integer minStockLevel = 5;

    @Column(unique = true, nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    public String sku;

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    public String brand;

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    public String model;

    @Column(columnDefinition = "TEXT")
    public String specifications;

    @Column(columnDefinition = "TEXT")
    public String features;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url", columnDefinition = "TEXT")
    public List<String> imageUrls;

    @ElementCollection
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag", length = 50)
    public List<String> tags;

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

    @Column(length = 100)
    public String dimensions;

    @Column(name = "warranty_months")
    public Integer warrantyMonths;

    @Column(name = "average_rating", precision = 3, scale = 2)
    public BigDecimal rating = BigDecimal.ZERO;

    @Column(name = "total_reviews")
    public Integer reviewCount = 0;

    @Column(name = "total_sold")
    public Integer salesCount = 0;

    @Column(name = "view_count")
    public Long viewCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull
    public Category category;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<CartItem> cartItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<WishlistItem> wishlistItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Review> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Auction> auctions;

    // Constructors
    public Product() {}

    public Product(String name, String description, BigDecimal price, String brand, String model, String sku, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.model = model;
        this.sku = sku;
        this.category = category;
    }

    // Static finder methods
    public static Product findBySku(String sku) {
        return find("sku", sku).firstResult();
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

    public static List<Product> findActiveProducts() {
        return find("isActive", true).list();
    }

    public static List<Product> findFeaturedProducts() {
        return find("isFeatured = true and isActive = true").list();
    }

    public static List<Product> findOnSaleProducts() {
        return find("isOnSale = true and isActive = true").list();
    }

    public static List<Product> findInPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return find("price >= ?1 and price <= ?2 and isActive = true", minPrice, maxPrice).list();
    }

    public static List<Product> findLowStock(int threshold) {
        return find("stockQuantity <= ?1 and isActive = true", threshold).list();
    }

    public static List<Product> findOutOfStock() {
        return find("stockQuantity = 0 and isActive = true").list();
    }

    public static List<Product> findPopular(int limit) {
        return find("isActive = true order by salesCount desc, viewCount desc")
                .page(0, limit).list();
    }

    public static List<Product> findTopRated(int limit) {
        return find("isActive = true and reviewCount > 0 order by rating desc, reviewCount desc")
                .page(0, limit).list();
    }

    public static List<Product> findRecent(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        return find("createdAt >= ?1 and isActive = true order by createdAt desc", cutoffDate).list();
    }

    public static List<Product> searchProducts(String query) {
        String searchQuery = "%" + query.toLowerCase() + "%";
        return find("(lower(name) like ?1 or lower(description) like ?1 or lower(brand) like ?1 or lower(model) like ?1) and isActive = true", 
                   searchQuery).list();
    }

    public static long countActiveProducts() {
        return count("isActive", true);
    }

    public static long countLowStock(int threshold) {
        return count("stockQuantity <= ?1 and isActive = true", threshold);
    }

    public static long countOutOfStock() {
        return count("stockQuantity = 0 and isActive = true");
    }

    // Helper methods
    public boolean isOnSaleNow() {
        if (!isOnSale) return false;
        LocalDateTime now = LocalDateTime.now();
        return (saleStartDate == null || saleStartDate.isBefore(now) || saleStartDate.isEqual(now)) &&
               (saleEndDate == null || saleEndDate.isAfter(now));
    }

    public BigDecimal getCurrentPrice() {
        return isOnSaleNow() && originalPrice != null ? originalPrice : price;
    }

    public BigDecimal getSavings() {
        if (isOnSaleNow() && originalPrice != null) {
            return originalPrice.subtract(price);
        }
        return BigDecimal.ZERO;
    }

    public boolean isLowStock() {
        return stockQuantity <= minStockLevel;
    }

    public boolean isOutOfStock() {
        return stockQuantity <= 0;
    }

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void incrementSalesCount(int quantity) {
        this.salesCount += quantity;
    }

    public void updateRating(BigDecimal newRating, int newReviewCount) {
        this.rating = newRating;
        this.reviewCount = newReviewCount;
    }

    public void reduceStock(int quantity) {
        if (stockQuantity >= quantity) {
            stockQuantity -= quantity;
        } else {
            throw new IllegalArgumentException("Insufficient stock");
        }
    }

    public void increaseStock(int quantity) {
        stockQuantity += quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", sku='" + sku + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", isActive=" + isActive +
                '}';
    }
}
