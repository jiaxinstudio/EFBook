package com.book.ef.service;

import com.book.ef.entity.Book;
import com.book.ef.entity.Inventory;
import com.book.ef.repository.InventoryRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> getInventories() {
        return inventoryRepository.findAll();
    }

    public Inventory updateStock(Long bookId, Integer newStock) {
        Inventory inventory = inventoryRepository.findByBookId(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for book ID: " + bookId));

        inventory.setQuantity(newStock);
        return inventoryRepository.save(inventory);
    }

    public Integer getStockQuantity(Long bookId) {
        Inventory inventory = inventoryRepository.findByBookId(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for book ID: " + bookId));

        return inventory.getQuantity();
    }
}
