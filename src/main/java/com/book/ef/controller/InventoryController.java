package com.book.ef.controller;

import com.book.ef.dto.InventoryResponse;
import com.book.ef.dto.UpdateInventoryDto;
import com.book.ef.entity.Book;
import com.book.ef.entity.Inventory;
import com.book.ef.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventories")
@ResponseBody
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    @GetMapping("list")
    public List<Inventory> getInventories() {
        return inventoryService.getInventories();
    }

    // Update stock for a book
    @PutMapping
    public ResponseEntity<Inventory> updateStock(@RequestParam Long bookId, @RequestBody UpdateInventoryDto updateInventoryDto) {
        Inventory updatedInventory = inventoryService.updateStock(bookId, updateInventoryDto.getQuantity());
        return ResponseEntity.ok(updatedInventory);
    }

    // Check stock availability for a book
    @GetMapping
    public ResponseEntity<InventoryResponse> getStockQuantity(@RequestParam Long bookId) {
        Integer quantity = inventoryService.getStockQuantity(bookId);
        InventoryResponse response = new InventoryResponse();
        response.setBookId(bookId);
        response.setQuantity(quantity);
        return ResponseEntity.ok(response);
    }
}