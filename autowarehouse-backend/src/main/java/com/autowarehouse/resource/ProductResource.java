package com.autowarehouse.resource;

import com.autowarehouse.entity.Category;
import com.autowarehouse.entity.Product;
import com.autowarehouse.service.ProductService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    public Response getAllProducts(
            @QueryParam("category") Long categoryId,
            @QueryParam("brand") String brand,
            @QueryParam("minPrice") BigDecimal minPrice,
            @QueryParam("maxPrice") BigDecimal maxPrice,
            @QueryParam("search") String search,
            @QueryParam("onSale") Boolean onSale,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size) {
        
        try {
            List<Product> products;
            
            if (search != null && !search.trim().isEmpty()) {
                products = productService.searchProducts(search);
            } else if (categoryId != null) {
                Category category = Category.findById(categoryId);
                products = productService.findByCategory(category);
            } else if (brand != null) {
                products = productService.findByBrand(brand);
            } else if (minPrice != null && maxPrice != null) {
                products = productService.findInPriceRange(minPrice, maxPrice);
            } else if (onSale != null && onSale) {
                products = productService.findOnSale();
            } else {
                products = productService.findActiveProducts();
            }

            List<ProductResponse> response = products.stream()
                    .map(ProductResponse::new)
                    .toList();
            
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch products"))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getProduct(@PathParam("id") Long id) {
        try {
            Product product = productService.findById(id);
            if (product == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Product not found"))
                        .build();
            }
            
            // Increment view count
            productService.incrementViewCount(id);
            
            return Response.ok(new ProductDetailResponse(product)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch product"))
                    .build();
        }
    }

    @GET
    @Path("/sku/{sku}")
    public Response getProductBySku(@PathParam("sku") String sku) {
        try {
            Product product = productService.findBySku(sku);
            if (product == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Product not found"))
                        .build();
            }
            return Response.ok(new ProductDetailResponse(product)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch product"))
                    .build();
        }
    }

    @GET
    @Path("/featured/popular")
    public Response getPopularProducts(@QueryParam("limit") @DefaultValue("10") int limit) {
        List<Product> products = productService.findPopularProducts(limit);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    @GET
    @Path("/featured/top-rated")
    public Response getTopRatedProducts(@QueryParam("limit") @DefaultValue("10") int limit) {
        List<Product> products = productService.findTopRatedProducts(limit);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    @GET
    @Path("/featured/recent")
    public Response getRecentProducts(@QueryParam("days") @DefaultValue("30") int days) {
        List<Product> products = productService.findRecentProducts(days);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    // Admin endpoints
    @POST
    @Path("/admin")
    @RolesAllowed("ADMIN")
    public Response createProduct(@Valid CreateProductRequest request) {
        try {
            Product product = new Product();
            product.name = request.name;
            product.description = request.description;
            product.price = request.price;
            product.originalPrice = request.originalPrice;
            product.stockQuantity = request.stockQuantity;
            product.brand = request.brand;
            product.model = request.model;
            product.specifications = request.specifications;
            product.features = request.features;
            product.imageUrls = request.imageUrls;
            product.category = Category.findById(request.categoryId);
            
            Product createdProduct = productService.createProduct(product);
            return Response.status(Response.Status.CREATED)
                    .entity(new ProductDetailResponse(createdProduct))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}")
    @RolesAllowed("ADMIN")
    public Response updateProduct(@PathParam("id") Long id, @Valid UpdateProductRequest request) {
        try {
            Product updatedProduct = new Product();
            updatedProduct.name = request.name;
            updatedProduct.description = request.description;
            updatedProduct.price = request.price;
            updatedProduct.originalPrice = request.originalPrice;
            updatedProduct.stockQuantity = request.stockQuantity;
            updatedProduct.brand = request.brand;
            updatedProduct.model = request.model;
            updatedProduct.specifications = request.specifications;
            updatedProduct.features = request.features;
            updatedProduct.imageUrls = request.imageUrls;
            updatedProduct.category = Category.findById(request.categoryId);
            updatedProduct.isActive = request.isActive;
            
            Product product = productService.updateProduct(id, updatedProduct);
            return Response.ok(new ProductDetailResponse(product)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/stock")
    @RolesAllowed("ADMIN")
    public Response updateStock(@PathParam("id") Long id, @Valid UpdateStockRequest request) {
        try {
            productService.updateStock(id, request.stockQuantity);
            return Response.ok(new SuccessResponse("Stock updated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/sale")
    @RolesAllowed("ADMIN")
    public Response setSalePrice(@PathParam("id") Long id, @Valid SetSalePriceRequest request) {
        try {
            productService.setSalePrice(id, request.salePrice, request.startDate, request.endDate);
            return Response.ok(new SuccessResponse("Sale price set successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}/sale")
    @RolesAllowed("ADMIN")
    public Response clearSale(@PathParam("id") Long id) {
        try {
            productService.clearSale(id);
            return Response.ok(new SuccessResponse("Sale cleared successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/activate")
    @RolesAllowed("ADMIN")
    public Response activateProduct(@PathParam("id") Long id) {
        try {
            productService.activateProduct(id);
            return Response.ok(new SuccessResponse("Product activated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/deactivate")
    @RolesAllowed("ADMIN")
    public Response deactivateProduct(@PathParam("id") Long id) {
        try {
            productService.deactivateProduct(id);
            return Response.ok(new SuccessResponse("Product deactivated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    @RolesAllowed("ADMIN")
    public Response deleteProduct(@PathParam("id") Long id) {
        try {
            productService.deleteProduct(id);
            return Response.ok(new SuccessResponse("Product deleted successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/admin/stats")
    @RolesAllowed("ADMIN")
    public Response getProductStats() {
        ProductStatsResponse stats = new ProductStatsResponse();
        stats.totalProducts = productService.getTotalProductsCount();
        stats.activeProducts = productService.getActiveProductsCount();
        stats.lowStockProducts = productService.getLowStockCount(10);
        stats.outOfStockProducts = productService.getOutOfStockCount();
        return Response.ok(stats).build();
    }

    @GET
    @Path("/admin/low-stock")
    @RolesAllowed("ADMIN")
    public Response getLowStockProducts(@QueryParam("threshold") @DefaultValue("10") int threshold) {
        List<Product> products = productService.findLowStock(threshold);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    @GET
    @Path("/admin/out-of-stock")
    @RolesAllowed("ADMIN")
    public Response getOutOfStockProducts() {
        List<Product> products = productService.findOutOfStock();
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    // DTO Classes
    public static class CreateProductRequest {
        public String name;
        public String description;
        public BigDecimal price;
        public BigDecimal originalPrice;
        public Integer stockQuantity;
        public String brand;
        public String model;
        public String specifications;
        public String features;
        public List<String> imageUrls;
        public Long categoryId;
    }

    public static class UpdateProductRequest extends CreateProductRequest {
        public Boolean isActive;
    }

    public static class UpdateStockRequest {
        public Integer stockQuantity;
    }

    public static class SetSalePriceRequest {
        public BigDecimal salePrice;
        public LocalDateTime startDate;
        public LocalDateTime endDate;
    }

    public static class ProductResponse {
        public Long id;
        public String name;
        public String description;
        public BigDecimal price;
        public BigDecimal originalPrice;
        public String brand;
        public String model;
        public List<String> imageUrls;
        public Integer stockQuantity;
        public Boolean isActive;
        public Boolean isOnSale;
        public BigDecimal rating;
        public Integer reviewCount;
        public Integer viewCount;
        public Integer salesCount;

        public ProductResponse(Product product) {
            this.id = product.id;
            this.name = product.name;
            this.description = product.description;
            this.price = product.price;
            this.originalPrice = product.originalPrice;
            this.brand = product.brand;
            this.model = product.model;
            this.imageUrls = product.imageUrls;
            this.stockQuantity = product.stockQuantity;
            this.isActive = product.isActive;
            this.isOnSale = product.isOnSaleNow();
            this.rating = product.rating;
            this.reviewCount = product.reviewCount;
            this.viewCount = product.viewCount;
            this.salesCount = product.salesCount;
        }
    }

    public static class ProductDetailResponse extends ProductResponse {
        public String specifications;
        public String features;
        public String sku;
        public String categoryName;
        public LocalDateTime createdAt;

        public ProductDetailResponse(Product product) {
            super(product);
            this.specifications = product.specifications;
            this.features = product.features;
            this.sku = product.sku;
            this.categoryName = product.category != null ? product.category.name : null;
            this.createdAt = product.createdAt;
        }
    }

    public static class ProductStatsResponse {
        public long totalProducts;
        public long activeProducts;
        public long lowStockProducts;
        public long outOfStockProducts;
    }

    public static class ErrorResponse {
        public String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }

    public static class SuccessResponse {
        public String message;

        public SuccessResponse(String message) {
            this.message = message;
        }
    }
}
