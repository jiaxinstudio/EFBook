package com.book.ef.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    private Long id;
    private Long bookId;
    private int quantity;
}
