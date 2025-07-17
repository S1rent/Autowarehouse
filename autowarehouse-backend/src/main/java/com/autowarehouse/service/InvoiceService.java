package com.autowarehouse.service;

import com.autowarehouse.entity.Order;
import com.autowarehouse.entity.OrderItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ApplicationScoped
public class InvoiceService {

    @Inject
    OrderService orderService;

    public byte[] generateInvoicePDF(Long orderId) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        return generateInvoicePDF(order);
    }

    public byte[] generateInvoicePDF(Order order) {
        try {
            // For now, we'll generate a simple text-based invoice
            // In a real implementation, you would use libraries like iText or Apache PDFBox
            String invoiceContent = generateInvoiceContent(order);
            return invoiceContent.getBytes("UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate invoice PDF", e);
        }
    }

    public String generateInvoiceHTML(Long orderId) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        return generateInvoiceHTML(order);
    }

    public String generateInvoiceHTML(Order order) {
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Invoice - ").append(order.orderNumber).append("</title>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; margin: 20px; }");
        html.append(".header { text-align: center; margin-bottom: 30px; }");
        html.append(".company-info { margin-bottom: 20px; }");
        html.append(".invoice-info { display: flex; justify-content: space-between; margin-bottom: 30px; }");
        html.append(".customer-info, .order-info { width: 45%; }");
        html.append("table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }");
        html.append("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
        html.append("th { background-color: #f2f2f2; }");
        html.append(".total-section { text-align: right; margin-top: 20px; }");
        html.append(".total-row { font-weight: bold; }");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");

        // Header
        html.append("<div class='header'>");
        html.append("<h1>AUTOWAREHOUSE</h1>");
        html.append("<h2>INVOICE</h2>");
        html.append("</div>");

        // Company Info
        html.append("<div class='company-info'>");
        html.append("<strong>AutoWarehouse Indonesia</strong><br>");
        html.append("Jl. Sudirman No. 123<br>");
        html.append("Jakarta Pusat 10110<br>");
        html.append("Indonesia<br>");
        html.append("Phone: +62-21-1234567<br>");
        html.append("Email: info@autowarehouse.com");
        html.append("</div>");

        // Invoice and Customer Info
        html.append("<div class='invoice-info'>");
        
        // Customer Info
        html.append("<div class='customer-info'>");
        html.append("<h3>Bill To:</h3>");
        html.append("<strong>").append(order.user.getFullName()).append("</strong><br>");
        html.append(order.user.email).append("<br>");
        if (order.user.phoneNumber != null) {
            html.append(order.user.phoneNumber).append("<br>");
        }
        if (order.shippingAddress != null) {
            html.append(order.shippingAddress.replace("\n", "<br>"));
        }
        html.append("</div>");

        // Order Info
        html.append("<div class='order-info'>");
        html.append("<h3>Invoice Details:</h3>");
        html.append("<strong>Invoice Number:</strong> INV-").append(order.orderNumber).append("<br>");
        html.append("<strong>Order Number:</strong> ").append(order.orderNumber).append("<br>");
        html.append("<strong>Invoice Date:</strong> ").append(order.createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("<br>");
        html.append("<strong>Status:</strong> ").append(order.status.name()).append("<br>");
        html.append("<strong>Payment Status:</strong> ").append(order.paymentStatus.name());
        html.append("</div>");

        html.append("</div>");

        // Order Items Table
        html.append("<table>");
        html.append("<thead>");
        html.append("<tr>");
        html.append("<th>Item</th>");
        html.append("<th>SKU</th>");
        html.append("<th>Quantity</th>");
        html.append("<th>Unit Price</th>");
        html.append("<th>Total</th>");
        html.append("</tr>");
        html.append("</thead>");
        html.append("<tbody>");

        List<OrderItem> items = order.items;
        if (items != null) {
            for (OrderItem item : items) {
                html.append("<tr>");
                html.append("<td>").append(item.productName).append("</td>");
                html.append("<td>").append(item.productSku != null ? item.productSku : "N/A").append("</td>");
                html.append("<td>").append(item.quantity).append("</td>");
                html.append("<td>Rp ").append(formatPrice(item.productPrice)).append("</td>");
                html.append("<td>Rp ").append(formatPrice(item.subtotal)).append("</td>");
                html.append("</tr>");
            }
        }

        html.append("</tbody>");
        html.append("</table>");

        // Totals Section
        html.append("<div class='total-section'>");
        html.append("<table style='width: 300px; margin-left: auto;'>");
        html.append("<tr>");
        html.append("<td><strong>Subtotal:</strong></td>");
        html.append("<td>Rp ").append(formatPrice(order.subtotal)).append("</td>");
        html.append("</tr>");
        
        if (order.taxAmount != null && order.taxAmount.compareTo(BigDecimal.ZERO) > 0) {
            html.append("<tr>");
            html.append("<td><strong>Tax (11%):</strong></td>");
            html.append("<td>Rp ").append(formatPrice(order.taxAmount)).append("</td>");
            html.append("</tr>");
        }
        
        if (order.shippingCost != null && order.shippingCost.compareTo(BigDecimal.ZERO) > 0) {
            html.append("<tr>");
            html.append("<td><strong>Shipping:</strong></td>");
            html.append("<td>Rp ").append(formatPrice(order.shippingCost)).append("</td>");
            html.append("</tr>");
        }
        
        html.append("<tr class='total-row'>");
        html.append("<td><strong>Total Amount:</strong></td>");
        html.append("<td><strong>Rp ").append(formatPrice(order.totalAmount)).append("</strong></td>");
        html.append("</tr>");
        html.append("</table>");
        html.append("</div>");

        // Footer
        html.append("<div style='margin-top: 40px; text-align: center; font-size: 12px; color: #666;'>");
        html.append("<p>Thank you for your business!</p>");
        html.append("<p>This is a computer-generated invoice and does not require a signature.</p>");
        html.append("</div>");

        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    private String generateInvoiceContent(Order order) {
        StringBuilder content = new StringBuilder();
        
        content.append("AUTOWAREHOUSE INVOICE\n");
        content.append("===================\n\n");
        
        content.append("Invoice Number: INV-").append(order.orderNumber).append("\n");
        content.append("Order Number: ").append(order.orderNumber).append("\n");
        content.append("Date: ").append(order.createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
        content.append("Status: ").append(order.status.name()).append("\n\n");
        
        content.append("CUSTOMER INFORMATION\n");
        content.append("-------------------\n");
        content.append("Name: ").append(order.user.getFullName()).append("\n");
        content.append("Email: ").append(order.user.email).append("\n");
        if (order.user.phoneNumber != null) {
            content.append("Phone: ").append(order.user.phoneNumber).append("\n");
        }
        if (order.shippingAddress != null) {
            content.append("Address: ").append(order.shippingAddress).append("\n");
        }
        content.append("\n");
        
        content.append("ORDER ITEMS\n");
        content.append("-----------\n");
        
        List<OrderItem> items = order.items;
        if (items != null) {
            for (OrderItem item : items) {
                content.append(String.format("%-30s %3d x Rp %12s = Rp %12s\n",
                    item.productName,
                    item.quantity,
                    formatPrice(item.productPrice),
                    formatPrice(item.subtotal)
                ));
            }
        }
        
        content.append("\n");
        content.append("SUMMARY\n");
        content.append("-------\n");
        content.append(String.format("Subtotal:      Rp %12s\n", formatPrice(order.subtotal)));
        
        if (order.taxAmount != null && order.taxAmount.compareTo(BigDecimal.ZERO) > 0) {
            content.append(String.format("Tax (11%%):     Rp %12s\n", formatPrice(order.taxAmount)));
        }
        
        if (order.shippingCost != null && order.shippingCost.compareTo(BigDecimal.ZERO) > 0) {
            content.append(String.format("Shipping:      Rp %12s\n", formatPrice(order.shippingCost)));
        }
        
        content.append("                 ").append("=".repeat(15)).append("\n");
        content.append(String.format("TOTAL:         Rp %12s\n", formatPrice(order.totalAmount)));
        
        content.append("\n");
        content.append("Thank you for your business!\n");
        content.append("AutoWarehouse Indonesia\n");
        
        return content.toString();
    }

    private String formatPrice(BigDecimal price) {
        if (price == null) return "0";
        return String.format("%,d", price.longValue());
    }

    public String generateInvoiceNumber(Order order) {
        return "INV-" + order.orderNumber;
    }
}
