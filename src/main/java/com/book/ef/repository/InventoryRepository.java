package com.book.ef.repository;

import com.book.ef.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository  extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByBookId(Long bookId);

    void deleteByBook_Id(Long bookId);
}
