package com.example.userservice.controllers;

import com.example.userservice.entities.Product;
import com.example.userservice.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/product/{id}")
    public Optional<Product> getAvailabilityForId(@PathVariable String id) throws JsonProcessingException {
        log.info("Finding product with requested id");
        return productService.getProductInventoryById(id);
    }

    @GetMapping("/api/product")
    public Optional<List<Product>> getAvailabilityForSku(@RequestParam String sku) throws JsonProcessingException {
        log.info("Finding products for requested sku");
        return productService.getProductsInventoryBySku(sku);
    }
}
