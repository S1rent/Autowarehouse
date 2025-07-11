package com.autowarehouse.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(unique = true, nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    public String name;

    @Column(columnDefinition = "TEXT")
    public String description;

    @Column(unique = true, nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    public String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    public Category parent;

    @Column(name = "image_url", columnDefinition = "TEXT")
    public String imageUrl;

    @Column(name = "is_active")
    public Boolean isActive = true;

    @Column(name = "sort_order")
    public Integer sortOrder = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    public List<Category> children;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"category"})
    public List<Product> products;

    // Constructors
    public Category() {}

    public Category(String name, String slug, String description) {
        this.name = name;
        this.slug = slug;
        this.description = description;
    }

    public Category(String name, String slug, String description, Category parent) {
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.parent = parent;
    }

    // Static finder methods
    public static Category findBySlug(String slug) {
        return find("slug", slug).firstResult();
    }

    public static List<Category> findActiveCategories() {
        return find("isActive = true order by sortOrder, name").list();
    }

    public static List<Category> findRootCategories() {
        return find("parent is null and isActive = true order by sortOrder, name").list();
    }

    public static List<Category> findByParent(Category parent) {
        return find("parent = ?1 and isActive = true order by sortOrder, name", parent).list();
    }

    public static List<Category> findByParentId(Long parentId) {
        return find("parent.id = ?1 and isActive = true order by sortOrder, name", parentId).list();
    }

    public static long countActiveCategories() {
        return count("isActive", true);
    }

    public static long countProductsInCategory(Long categoryId) {
        return Product.count("category.id = ?1 and isActive = true", categoryId);
    }

    public static List<Category> searchByName(String query) {
        return find("lower(name) like lower(?1) and isActive = true order by sortOrder, name", "%" + query + "%").list();
    }

    // Helper methods
    public boolean isRootCategory() {
        return parent == null;
    }

    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }

    public boolean hasProducts() {
        return products != null && !products.isEmpty();
    }

    public long getProductCount() {
        return Product.count("category = ?1 and isActive = true", this);
    }

    public String getFullPath() {
        if (parent == null) {
            return name;
        }
        return parent.getFullPath() + " > " + name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", isActive=" + isActive +
                ", sortOrder=" + sortOrder +
                '}';
    }
}
