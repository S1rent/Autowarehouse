package com.autowarehouse.resource;

import com.autowarehouse.service.InvoiceService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/invoices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceResource {

    @Inject
    InvoiceService invoiceService;

    @GET
    @Path("/{orderId}/pdf")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    @Produces("application/pdf")
    public Response generateInvoicePDF(@PathParam("orderId") Long orderId) {
        try {
            byte[] pdfData = invoiceService.generateInvoicePDF(orderId);
            String filename = "invoice_" + orderId + ".pdf";
            
            return Response.ok(pdfData)
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .header("Content-Type", "application/pdf")
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to generate invoice PDF"))
                    .build();
        }
    }

    @GET
    @Path("/{orderId}/html")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    @Produces(MediaType.TEXT_HTML)
    public Response generateInvoiceHTML(@PathParam("orderId") Long orderId) {
        try {
            String htmlContent = invoiceService.generateInvoiceHTML(orderId);
            return Response.ok(htmlContent).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to generate invoice HTML"))
                    .build();
        }
    }

    @GET
    @Path("/{orderId}/download")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    @Produces("application/octet-stream")
    public Response downloadInvoice(@PathParam("orderId") Long orderId) {
        try {
            String htmlContent = invoiceService.generateInvoiceHTML(orderId);
            String filename = "invoice_" + orderId + ".html";
            
            return Response.ok(htmlContent.getBytes("UTF-8"))
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to download invoice"))
                    .build();
        }
    }

    public static class ErrorResponse {
        public String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }
}
