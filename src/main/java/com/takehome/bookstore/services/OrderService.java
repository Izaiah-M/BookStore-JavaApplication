package com.takehome.bookstore.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .status(Status.PENDING)
                .build();

        // Save the order first
        Order newOrder = orderRepository.save(order);

        Order savedOrder = orderRepository.findById(newOrder.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Order not Found"));

        // Calculate total price and create order items
        Double totalPrice = 0.0;

        List<OrderItemRequest> orderItems = request.getOrderItems();
        List<OrderItem> newOrderItems = new ArrayList<>();

        for (OrderItemRequest item : orderItems) {
            // Make sure the books in the list exist
            Book book = bookRepository.findById(item.getBookId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Book not Found"));

            Integer requestedQuantity = item.getQuantity();
            Integer availableQuantity = book.getQuantity();

            if (requestedQuantity > availableQuantity) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Not enough stock for the book titled: " + book.getTitle());
            }

            // Create order item
            OrderItem orderItem = OrderItem.builder()
                    .book(book)
                    .quantity(requestedQuantity)
                    .subtotal(book.getPrice() * requestedQuantity)
                    .order(savedOrder) // Associate the OrderItem with the saved Order
                    .build();

            // Save the order item
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);

            newOrderItems.add(savedOrderItem);

            // Add the subtotal to the total price of the order
            totalPrice += savedOrderItem.getSubtotal();

            // Update book quantity
            book.setQuantity(availableQuantity - requestedQuantity);
            bookRepository.save(book);
        }

        // Set the list of order items to the saved order
        savedOrder.setOrderItems(newOrderItems);

        // Set the total price for the order
        savedOrder.setTotalPrice(totalPrice);

        // Save the updated order with associated order items
        Order updatedOrder = orderRepository.save(newOrder);

        return OrderResponse.builder()
                .message("Your order has been successfully placed!")
                .orderId(updatedOrder.getId())
                .orderAmount(totalPrice)
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
