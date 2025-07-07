package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 100)
    public String name;

    @Column(columnDefinition = "TEXT")
    public String description;

    @Column(name = "image_url")
    public String imageUrl;

    @Column(name = "icon_class")
    @Size(max = 50)
    public String iconClass;

    @Column(name = "is_active")
    public Boolean isActive = true;

    @Column(name = "sort_order")
    public Integer sortOrder = 0;

    @Column(name = "product_count")
    public Integer productCount = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Product> products;

    // Constructors
    public Category() {}

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Static finder methods
    public static List<Category> findActiveCategories() {
        return find("isActive = true order by sortOrder, name").list();
    }

    public static Category findByName(String name) {
        return find("name", name).firstResult();
    }

    // Helper methods
    public void updateProductCount() {
        this.productCount = Product.find("category = ?1 and isActive = true", this).list().size();
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                ", productCount=" + productCount +
                '}';
    }
}
