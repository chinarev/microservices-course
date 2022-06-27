package com.example.catalogservice.service;

import com.example.catalogservice.entities.Product;
import com.example.catalogservice.repository.CatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CatalogService {
    private final CatalogRepository catalogRepository;

    ///
    private final RestTemplate restTemplate;
    ///

    @Autowired
    public CatalogService(CatalogRepository catalogRepository, RestTemplate restTemplate) {
        this.catalogRepository = catalogRepository;
        this.restTemplate = restTemplate;
    }

    public Optional<Product> findProductById(String id) {
        log.info("Finding product...");
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            log.error("InterruptedException happened! But this is just to simulate circuit breaker behavior");
//            Thread.currentThread().interrupt();
//        }

//        ///delete
//
//
//        log.info("Sending request to MEM FROM MEM...");
//        ResponseEntity<String> inventoryResponseEntity =
//                restTemplate.getForEntity(
//                        //"http://inventory-service:8282/api/inventory/{id}", String.class, id);
//                        "http://inventory-service/api/inventory/{id}", String.class, id);
//        log.info("Response from MEM: " + inventoryResponseEntity.getBody());
//
//        ///delete
        return catalogRepository.findByUniqId(id);
    }

    public List<String> findProductsBySku(String sku) {
        log.info("Finding list of products...");
        return catalogRepository.findBySku(sku)
                .stream()
                .map(Product::getUniqId)
                .collect(Collectors.toList());
    }
}