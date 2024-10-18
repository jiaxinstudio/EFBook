package com.book.ef.service;

import com.book.ef.entity.Book;
import com.book.ef.entity.Order;
import com.book.ef.entity.OrderStatus;
import com.book.ef.entity.User;
import com.book.ef.repository.BookRepository;
import com.book.ef.repository.OrderRepository;
import com.book.ef.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final InventoryService inventoryService;

    public OrderService(OrderRepository orderRepository, BookRepository bookRepository, InventoryService inventoryService, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
        this.inventoryService = inventoryService;
        this.userRepository = userRepository;
    }

    @Transactional
    public Order createOrder(Long bookId, int quantity) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
        Order order = Order.createOrder(book, quantity);
        order.setUser(user);
        orderRepository.save(order);

        Integer stockQuantity = inventoryService.getStockQuantity(bookId);
        inventoryService.updateStock(bookId, stockQuantity - quantity);
        return order;
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.cancelOrder();
            orderRepository.save(order);

            Long bookId = order.getBook().getId();
            Integer stockQuantity = inventoryService.getStockQuantity(bookId);
            inventoryService.updateStock(bookId, stockQuantity + order.getQuantity());
        }
    }

    public OrderStatus checkOrderStatus(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        return orderOpt.map(Order::checkOrderStatus).orElseThrow();
    }
}
