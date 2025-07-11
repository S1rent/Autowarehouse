package com.autowarehouse.service;

import com.autowarehouse.entity.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.BadRequestException;

import java.util.List;

@ApplicationScoped
public class CategoryService {

    public List<Category> getAllCategories() {
        return Category.findAll().list();
    }

    public List<Category> getActiveCategories() {
        return Category.findActiveCategories();
    }

    public List<Category> getRootCategories() {
        return Category.findRootCategories();
    }

    public List<Category> getCategoriesByParent(Long parentId) {
        return Category.findByParentId(parentId);
    }

    public Category getCategoryById(Long id) {
        Category category = Category.findById(id);
        if (category == null) {
            throw new NotFoundException("Category not found with id: " + id);
        }
        return category;
    }

    public Category getCategoryBySlug(String slug) {
        Category category = Category.findBySlug(slug);
        if (category == null) {
            throw new NotFoundException("Category not found with slug: " + slug);
        }
        return category;
    }

    @Transactional
    public Category createCategory(Category category) {
        // Validate required fields
        if (category.name == null || category.name.trim().isEmpty()) {
            throw new BadRequestException("Category name is required");
        }

        if (category.slug == null || category.slug.trim().isEmpty()) {
            throw new BadRequestException("Category slug is required");
        }

        // Check if slug already exists
        Category existingCategory = Category.findBySlug(category.slug);
        if (existingCategory != null) {
            throw new BadRequestException("Category with slug '" + category.slug + "' already exists");
        }

        // Set default values
        if (category.isActive == null) {
            category.isActive = true;
        }
        if (category.sortOrder == null) {
            category.sortOrder = 0;
        }

        // Validate parent category if provided
        if (category.parent != null && category.parent.id != null) {
            Category parentCategory = Category.findById(category.parent.id);
            if (parentCategory == null) {
                throw new BadRequestException("Parent category not found");
            }
            category.parent = parentCategory;
        }

        category.persist();
        return category;
    }

    @Transactional
    public Category updateCategory(Long id, Category updatedCategory) {
        Category category = getCategoryById(id);

        // Update fields if provided
        if (updatedCategory.name != null && !updatedCategory.name.trim().isEmpty()) {
            category.name = updatedCategory.name.trim();
        }

        if (updatedCategory.description != null) {
            category.description = updatedCategory.description;
        }

        if (updatedCategory.slug != null && !updatedCategory.slug.trim().isEmpty()) {
            // Check if slug is being changed and if new slug already exists
            if (!category.slug.equals(updatedCategory.slug)) {
                Category existingCategory = Category.findBySlug(updatedCategory.slug);
                if (existingCategory != null && !existingCategory.id.equals(id)) {
                    throw new BadRequestException("Category with slug '" + updatedCategory.slug + "' already exists");
                }
                category.slug = updatedCategory.slug.trim();
            }
        }

        if (updatedCategory.imageUrl != null) {
            category.imageUrl = updatedCategory.imageUrl;
        }

        if (updatedCategory.isActive != null) {
            category.isActive = updatedCategory.isActive;
        }

        if (updatedCategory.sortOrder != null) {
            category.sortOrder = updatedCategory.sortOrder;
        }

        // Handle parent category update
        if (updatedCategory.parent != null) {
            if (updatedCategory.parent.id != null) {
                // Prevent circular reference
                if (updatedCategory.parent.id.equals(id)) {
                    throw new BadRequestException("Category cannot be its own parent");
                }
                
                Category parentCategory = Category.findById(updatedCategory.parent.id);
                if (parentCategory == null) {
                    throw new BadRequestException("Parent category not found");
                }
                category.parent = parentCategory;
            }
        }

        return category;
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);

        // Check if category has products
        long productCount = category.getProductCount();
        if (productCount > 0) {
            throw new BadRequestException("Cannot delete category with " + productCount + " products. Move or delete products first.");
        }

        // Check if category has children
        if (category.hasChildren()) {
            throw new BadRequestException("Cannot delete category with subcategories. Delete or move subcategories first.");
        }

        category.delete();
    }

    @Transactional
    public void softDeleteCategory(Long id) {
        Category category = getCategoryById(id);
        category.isActive = false;
    }

    @Transactional
    public void restoreCategory(Long id) {
        Category category = getCategoryById(id);
        category.isActive = true;
    }

    public long getTotalCategoriesCount() {
        return Category.count();
    }

    public long getActiveCategoriesCount() {
        return Category.countActiveCategories();
    }

    public long getProductCountInCategory(Long categoryId) {
        return Category.countProductsInCategory(categoryId);
    }

    @Transactional
    public void reorderCategories(List<Long> categoryIds) {
        for (int i = 0; i < categoryIds.size(); i++) {
            Category category = Category.findById(categoryIds.get(i));
            if (category != null) {
                category.sortOrder = i;
            }
        }
    }

    public List<Category> searchCategories(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getActiveCategories();
        }
        
