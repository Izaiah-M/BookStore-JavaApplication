package com.takehome.bookstore.services;

import static com.takehome.bookstore.models.Orders.Status.CANCELED;
import static com.takehome.bookstore.models.Orders.Status.COMPLETED;
import static com.takehome.bookstore.models.Orders.Status.PENDING;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.takehome.bookstore.DTOs.books.DeleteResponse;
import com.takehome.bookstore.DTOs.orders.CancelOrderRequest;
import com.takehome.bookstore.DTOs.orders.CancelOrderResponse;
import com.takehome.bookstore.DTOs.orders.OrderItemRequest;
import com.takehome.bookstore.DTOs.orders.OrderRequest;
import com.takehome.bookstore.DTOs.orders.OrderResponse;
import com.takehome.bookstore.models.Books.Book;
import com.takehome.bookstore.models.Books.BookRepository;
import com.takehome.bookstore.models.Orders.Order;
import com.takehome.bookstore.models.Orders.OrderItem;
import com.takehome.bookstore.models.Orders.OrderItemRepository;
import com.takehome.bookstore.models.Orders.OrderRepository;
import com.takehome.bookstore.models.User.User;
import com.takehome.bookstore.models.User.UserRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
                                .status(PENDING)
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
                Order updatedOrder = orderRepository.save(savedOrder);

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

        public DeleteResponse delete(@NotNull Integer orderId) {
                Order existingOrder = orderRepository.findById(orderId)
                                .orElseThrow(() -> new NoSuchElementException(
                                                "Order with ID " + orderId + " not found"));

                orderRepository.delete(existingOrder);

                return DeleteResponse.builder()
                                .message("Order has been successfully deleted")
                                .build();
        }

        public Page<Order> getUserOrdersByUserId(@NotNull Integer userId, PageRequest pageRequest) {
                return orderRepository.findByUserId(userId, pageRequest);
        }

        public CancelOrderResponse cancelOrder(@Valid CancelOrderRequest request) {
                Order existingOrder = orderRepository.findById(request.getOrderId())
                                .orElseThrow(() -> new NoSuchElementException(
                                                "Order with ID " + request.getOrderId() + " not found"));

                // Check if the order status is already "Cancelled" or "Delivered"
                if (existingOrder.getStatus() == CANCELED || existingOrder.getStatus() == COMPLETED) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "Order with ID " + request.getOrderId() + " cannot be cancelled.");
                }

                // Update the order status to "Cancelled"
                existingOrder.setStatus(CANCELED);

                // Save the updated order
                Order cancelledOrder = orderRepository.save(existingOrder);

                return CancelOrderResponse.builder()
                                .message("Order with ID " + request.getOrderId() + " has been cancelled.")
                                .orderId(cancelledOrder.getId())
                                .build();

        }

        public CancelOrderResponse completeOrder(@Valid CancelOrderRequest request) {
                Order existingOrder = orderRepository.findById(request.getOrderId())
                                .orElseThrow(() -> new NoSuchElementException(
                                                "Order with ID " + request.getOrderId() + " not found"));

                // Check if the order status is "Cancelled" or "Completed"
                if (existingOrder.getStatus() == CANCELED || existingOrder.getStatus() == COMPLETED) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        "Order with ID " + request.getOrderId()
                                                        + " has either been cancelled or completed");
                }

                // Update the order status to "Completed"
                existingOrder.setStatus(COMPLETED);

                // Save the updated order
                Order completedOrder = orderRepository.save(existingOrder);

                return CancelOrderResponse.builder()
                                .message("Order with ID " + request.getOrderId() + " is completed")
                                .orderId(completedOrder.getId())
                                .build();
        }

}
