package com.book.ef.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    public static Order createOrder(Book book, int quantity) {
        Order order = new Order();
        order.setBook(book);
        order.setQuantity(quantity);
        order.setStatus(OrderStatus.CREATED);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(book.getPrice().multiply(BigDecimal.valueOf(quantity)));

        return order;
    }

    public void cancelOrder() {
        if (this.status == OrderStatus.CREATED) {
            this.setStatus(OrderStatus.CANCELED);
        }
    }

    public OrderStatus checkOrderStatus() {
        return this.status;
    }
}
