package com.takehome.bookstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.takehome.bookstore.DTOs.books.DeleteResponse;
import com.takehome.bookstore.DTOs.orders.CancelOrderRequest;
import com.takehome.bookstore.DTOs.orders.CancelOrderResponse;
import com.takehome.bookstore.DTOs.orders.OrderRequest;
import com.takehome.bookstore.DTOs.orders.OrderResponse;
import com.takehome.bookstore.models.Orders.Order;
import com.takehome.bookstore.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Order")
public class OrdersController {

    private final OrderService service;

    // EndPoint for placing an order
    @PostMapping("/place")
    @Operation(description = "This is for placing an order, Access level required is USER, ADMIN, MANAGER. When an order is placed, its status is pending until you either cancel or complete the order", summary = "Place Order")
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest request) {

        return ResponseEntity.ok(service.placeOrder(request));
    }

    @PostMapping("/cancel")
    @Operation(description = "This is for canceling an order, Access level required is USER, ADMIN, MANAGER.", summary = "Cancel Order")
    public ResponseEntity<CancelOrderResponse> cancelOrder(@Valid @RequestBody CancelOrderRequest request) {

        return ResponseEntity.ok(service.cancelOrder(request));
    }

    @PostMapping("/complete-order")
    @Operation(description = "This is for completing an order, Access level required is USER, ADMIN, MANAGER. This can be done after the book has been paid for", summary = "Cancel Order")
    public ResponseEntity<CancelOrderResponse> completeOrder(@Valid @RequestBody CancelOrderRequest request) {

        return ResponseEntity.ok(service.completeOrder(request));
    }

    // Endpoint for getting all orders
    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(description = "This is for getting all orders of every USER, Access level required is ADMIN, and MANAGER.", summary = "Get all Orders")
    public ResponseEntity<Page<Order>> getAllOrders(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(service.getAllOrders(pageRequest));
    }

    @GetMapping("/user/{userId}")
    @Operation(description = "This is for getting orders for a specific USER, Access level required is USER, ADMIN and MANAGER.", summary = "Get User Orders")
    public ResponseEntity<Page<Order>> getUserOrdersByUserId(@PathVariable @NotNull Integer userId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(service.getUserOrdersByUserId(userId, pageRequest));
    }

    // Endpoint for getting a specific order by ID
    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(description = "This is for getting any Order by its ID, Access level required is ADMIN and MANAGER.", summary = "Get Orders By Id")
    public ResponseEntity<Order> getOrderById(@PathVariable @NotNull Integer orderId) {

        return service.getOrderById(orderId);
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(description = "This is for deleting an order. Access level required is ADMIN and MANAGER.", summary = "Delete Order")
    public ResponseEntity<DeleteResponse> delete(@PathVariable @NotNull Integer orderId) {
        return ResponseEntity.ok(service.delete(orderId));
    }
}
