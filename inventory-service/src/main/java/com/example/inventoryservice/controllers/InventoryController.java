package com.example.inventoryservice.controllers;

import com.example.inventoryservice.entities.InventoryItem;
import com.example.inventoryservice.service.InventoryService;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/api/inventory")
    public void getAvailability(@RequestBody List<String> uniqIds) throws Exception {
        log.info("Finding inventory for requested ids");
        //return inventoryService.getAvailabilityForIds(uniqIds);
        inventoryService.getAvailabilityForIds(uniqIds);
    }
}
