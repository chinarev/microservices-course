package com.example.inventoryservice.repository;

import com.example.inventoryservice.entities.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByUniqId(String id);
    List<InventoryItem> findAll();
}
