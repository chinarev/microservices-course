package com.example.inventoryservice.service;

import com.example.inventoryservice.entities.InventoryDto;
import com.example.inventoryservice.entities.InventoryItem;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
@Slf4j
public class InventoryService {
  private final InventoryRepository inventoryRepository;
  private final Map<String, InventoryDto> items = new HashMap();

  @Autowired
  public InventoryService(InventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
  }

  public void generateAvailability() {
    List<InventoryItem> data = inventoryRepository.findAll();
    for (InventoryItem item : data) {
      InventoryDto inventoryDto =
          new InventoryDto(
              item.getUniqId(),
              item.getSku(),
              item.getNameTitle(),
              ThreadLocalRandom.current().nextInt(0, 5));
      items.put(inventoryDto.getUniqId(), inventoryDto);
    }
  }

  public ResponseEntity<InventoryDto> getAvailabilityForId(String id)
      throws IllegalArgumentException {
    generateAvailability();

    log.info("Finding inventory for id: " + id);
    InventoryDto selectedItem = new InventoryDto();
    Optional<InventoryItem> inventoryItem = inventoryRepository.findByUniqId(id);
    if (inventoryItem.isPresent()) {
      selectedItem = items.get(id);
    } else {
      log.info("Item not found with id " + id);
    }
    return new ResponseEntity<>(selectedItem, HttpStatus.OK);
  }

  public ResponseEntity<List<InventoryDto>> getAvailabilityForIds(List<String> ids)
      throws IllegalArgumentException {
    generateAvailability();

    log.info("Finding inventory for ids: " + ids);
    List<InventoryDto> selectedItems = new ArrayList<>();
    for (String id : ids) {
      Optional<InventoryItem> inventoryItem = inventoryRepository.findByUniqId(id);
      if (inventoryItem.isPresent()) {
        selectedItems.add(items.get(id));
      } else {
        log.info("Item not found with id " + id);
      }
    }
    return new ResponseEntity<>(selectedItems, HttpStatus.OK);
  }
}
