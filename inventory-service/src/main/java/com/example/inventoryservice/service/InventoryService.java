package com.example.inventoryservice.service;

import com.example.inventoryservice.entities.InventoryItem;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final Map<InventoryItem, Boolean> items = new HashMap<>();

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
        setAvailabilityToData();
    }

    public void setAvailabilityToData() {
        List<InventoryItem> data = inventoryRepository.findAll();
        for (InventoryItem item : data) {
            items.put(item, ThreadLocalRandom.current().nextBoolean());
        }
    }

    public Map<InventoryItem, Boolean> getAvailabilityForIds(List<String> ids) throws Exception {
        Map<InventoryItem, Boolean> selectedItems = new HashMap<>();
        for (String id : ids) {
            selectedItems.put(inventoryRepository.findByUniqId(id).orElseThrow(() -> new Exception("Item not found with id " + id)), items.get(inventoryRepository.findByUniqId(id).orElseThrow(() -> new Exception("Item not found with id " + id))));
        }
        return selectedItems;
    }
}
