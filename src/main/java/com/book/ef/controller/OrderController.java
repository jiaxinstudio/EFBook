package com.book.ef.controller;

import com.book.ef.dto.CreateOrderDto;
import com.book.ef.entity.Order;
import com.book.ef.entity.OrderStatus;
import com.book.ef.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderDto orderDto) {
        return orderService.createOrder("admin", orderDto.getBookId(), orderDto.getQuantity());
    }

    @PutMapping("/cancel/{orderId}")
    public void cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
    }

    @GetMapping("/status/{orderId}")
    public OrderStatus checkOrderStatus(@PathVariable Long orderId) {
        return orderService.checkOrderStatus(orderId);
    }
}
