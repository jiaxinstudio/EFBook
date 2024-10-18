package com.book.ef.service;

import com.book.ef.entity.Book;
import com.book.ef.entity.Order;
import com.book.ef.entity.OrderStatus;
import com.book.ef.entity.User;
import com.book.ef.repository.BookRepository;
import com.book.ef.repository.OrderRepository;
import com.book.ef.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private OrderService orderService;

    private Book book;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book");
        book.setPrice(new BigDecimal("20.00"));

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
    }

    @Test
    void testCreateOrderSuccess() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(inventoryService.getStockQuantity(1L)).thenReturn(10);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order order = orderService.createOrder("admin",1L, 2);

        assertNotNull(order);
        assertEquals(user, order.getUser());
        assertEquals(2, order.getQuantity());
        verify(inventoryService).updateStock(1L, 8);
    }

    @Test
    void testCreateOrderBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> orderService.createOrder("admin", 1L, 2));
        assertEquals("Book not found", thrown.getMessage());
    }

    @Test
    void testCancelOrderSuccess() {
        Order order = new Order();
        order.setId(1L);
        order.setBook(book);
        order.setQuantity(2);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(inventoryService.getStockQuantity(1L)).thenReturn(10);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        orderService.cancelOrder(1L);

        verify(orderRepository).save(order);
        verify(inventoryService).updateStock(1L, 12);
    }

    @Test
    void testCancelOrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        orderService.cancelOrder(1L);

        verify(orderRepository, never()).save(any(Order.class));
        verify(inventoryService, never()).updateStock(anyLong(), anyInt());
    }

    @Test
    void testCheckOrderStatusSuccess() {
        // Arrange
        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.CREATED);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Act
        OrderStatus status = orderService.checkOrderStatus(1L);

        // Assert
        assertEquals(OrderStatus.CREATED, status);
    }

    @Test
    void testCheckOrderStatusNotFound() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderService.checkOrderStatus(1L));
    }
}