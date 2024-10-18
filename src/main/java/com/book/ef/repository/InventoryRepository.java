package com.book.ef.repository;

import com.book.ef.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository  extends JpaRepository<Inventory, Long> {
    void deleteByBook_Id(Long bookId);
}
