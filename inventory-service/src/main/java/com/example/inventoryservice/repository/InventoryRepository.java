package com.example.inventoryservice.repository;

import com.example.inventoryservice.entities.InventoryItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class InventoryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public InventoryItem getItemById(String id) {
        return entityManager.find(InventoryItem.class, id);
    }

}
