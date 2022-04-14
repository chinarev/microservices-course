package com.example.catalogservice.controllers;

import com.example.catalogservice.entities.Product;
import com.example.catalogservice.exceptions.ProductNotFoundException;
import com.example.catalogservice.service.CatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
@Slf4j
public class CatalogController {
    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/{id}")
    public Product productById(@PathVariable String id) {
        return catalogService.findProductById(id).orElseThrow(() -> new ProductNotFoundException("Product with uniq_id ["+id+"] doesn't exist"));
    }

    @GetMapping("/products/{sku}")
    public List<Product> productsBySku(@PathVariable String sku) {
        return catalogService.findProductsBySku(sku);
    }
}
