package com.book.ef.config;

import com.book.ef.entity.Book;
import com.book.ef.entity.Inventory;
import com.book.ef.entity.User;
import com.book.ef.repository.BookRepository;
import com.book.ef.repository.InventoryRepository;
import com.book.ef.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    private BookRepository bookRepository;
    private InventoryRepository inventoryRepository;
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(BookRepository bookRepository,
                           InventoryRepository inventoryRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.bookRepository = bookRepository;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Book book1 = new Book(null, "Clean Code", "Robert C. Martin", "9780132350884", "https://example.com/cover1.jpg", new BigDecimal("35.99"));
        Book book2 = new Book(null, "The Pragmatic Programmer", "Andrew Hunt", "9780201616224", "https://example.com/cover2.jpg", new BigDecimal("42.50"));
        Book book3 = new Book(null, "Effective Java", "Joshua Bloch", "9780134685991", "https://example.com/cover3.jpg", new BigDecimal("45.00"));
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        inventoryRepository.save(new Inventory(null, book1, 100));
        inventoryRepository.save(new Inventory(null, book2, 200));
        inventoryRepository.save(new Inventory(null, book3, 300));

        if (!userRepository.findByUsername("admin").isPresent()) {
            User admin = new User(null, "admin", passwordEncoder.encode("admin"), "admin@admin.com", Set.of("ADMIN", "USER"));
            userRepository.save(admin);
        }
    }
}
