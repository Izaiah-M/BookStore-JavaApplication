package com.takehome.bookstore.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.takehome.bookstore.DTOs.orders.OrderItemRequest;
import com.takehome.bookstore.DTOs.orders.OrderRequest;
import com.takehome.bookstore.DTOs.orders.OrderResponse;
import com.takehome.bookstore.models.Books.Book;
import com.takehome.bookstore.models.Books.BookRepository;
import com.takehome.bookstore.models.Orders.Order;
import com.takehome.bookstore.models.Orders.OrderItem;
import com.takehome.bookstore.models.Orders.OrderItemRepository;
import com.takehome.bookstore.models.Orders.OrderRepository;
import com.takehome.bookstore.models.Orders.Status;
import com.takehome.bookstore.models.User.User;
import com.takehome.bookstore.models.User.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public OrderResponse placeOrder(@Valid OrderRequest request) {

        // Retrieve user from the database
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "User not Found"));

        // Create a new order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.PENDING);

        // Calculate total price and create order items
        Double totalPrice = 0.0;

        List<OrderItemRequest> orderItems = request.getOrderItems();

        for (OrderItemRequest item : orderItems) {

            // Make sure the books in the list exist
            Book book = bookRepository.findById(item.getBookId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Book not Found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setBook(book);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setOrder(order);

            // Calculate and set the subtotal for each order item
            Double subtotal = book.getPrice() * item.getQuantity();
            orderItem.setSubtotal(subtotal);

            // Save the order item
            orderItemRepository.save(orderItem);

            // Add the subtotal to the total price of the order
            totalPrice += subtotal;
        }

        // Set the total price for the order
        order.setTotalPrice(totalPrice);

        // Save the order
        Order newOrder = orderRepository.save(order);

        return OrderResponse.builder()
                .message("Your order has been successfully placed!")
                .orderId(newOrder.getId())
                .build();
    }

    // Service that handles getting all orders
    public Page<Order> getAllOrders(PageRequest pageRequest) {
        return orderRepository.findAll(pageRequest);
    }

    public ResponseEntity<Order> getOrderById(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Order Not Found");
                });

        return ResponseEntity.ok(order);
    }

}
