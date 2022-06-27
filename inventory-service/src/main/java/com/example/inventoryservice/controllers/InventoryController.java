package com.example.inventoryservice.controllers;

import com.example.inventoryservice.entities.InventoryDto;
import com.example.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/api/inventory/{id}")
    public ResponseEntity<InventoryDto> getAvailabilityForId(@PathVariable String id) {
        return inventoryService.getAvailabilityForId(id);
    }

    @GetMapping("/api/inventory")
    public ResponseEntity<List<InventoryDto>> getAvailability(@RequestParam List<String> uniqIds) {
        return inventoryService.getAvailabilityForIds(uniqIds);
    }
}
