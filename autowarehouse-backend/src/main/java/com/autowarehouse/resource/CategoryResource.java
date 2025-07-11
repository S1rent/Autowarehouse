package com.autowarehouse.resource;

import com.autowarehouse.entity.Category;
import com.autowarehouse.service.CategoryService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @Inject
    CategoryService categoryService;

    @GET
    public Response getAllCategories(@QueryParam("active") @DefaultValue("true") boolean activeOnly) {
        try {
            List<Category> categories;
            if (activeOnly) {
                categories = categoryService.getActiveCategories();
            } else {
                categories = categoryService.getAllCategories();
            }
            return Response.ok(categories).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Failed to fetch categories: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/root")
    public Response getRootCategories() {
        try {
            List<Category> categories = categoryService.getRootCategories();
            return Response.ok(categories).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Failed to fetch root categories: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getCategoryById(@PathParam("id") Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return Response.ok(category).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Failed to fetch category: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/slug/{slug}")
    public Response getCategoryBySlug(@PathParam("slug") String slug) {
        try {
            Category category = categoryService.getCategoryBySlug(slug);
            return Response.ok(category).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Failed to fetch category: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @RolesAllowed("ADMIN")
    public Response createCategory(Category category) {
        try {
            Category createdCategory = categoryService.createCategory(category);
            return Response.status(Response.Status.CREATED)
                    .entity(createdCategory)
                    .build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Failed to create category: " + e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response updateCategory(@PathParam("id") Long id, Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, category);
            return Response.ok(updatedCategory).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Failed to update category: " + e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response deleteCategory(@PathParam("id") Long id) {
        try {
            categoryService.deleteCategory(id);
            return Response.ok(Map.of("message", "Category deleted successfully")).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Failed to delete category: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/admin/stats")
    @RolesAllowed("ADMIN")
    public Response getCategoryStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalCategories", categoryService.getTotalCategoriesCount());
            stats.put("activeCategories", categoryService.getActiveCategoriesCount());
            stats.put("inactiveCategories", categoryService.getTotalCategoriesCount() - categoryService.getActiveCategoriesCount());
            
            return Response.ok(stats).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Failed to fetch category statistics: " + e.getMessage()))
                    .build();
        }
    }
}
