package com.example.inventoryservice.service;

import com.example.inventoryservice.entities.InventoryItem;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Optional<InventoryItem> findInventoryItemById(String id) {
        return inventoryRepository.findByUniqId(id);
    }
}
