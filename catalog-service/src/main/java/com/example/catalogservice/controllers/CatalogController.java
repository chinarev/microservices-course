package com.example.catalogservice.controllers;

import com.example.catalogservice.entities.Product;
import com.example.catalogservice.service.CatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Optional<Product>> productById(@PathVariable String id) {
        Optional<Product> productItem = catalogService.findProductById(id);
        if (productItem.isPresent()) {
            return new ResponseEntity<>(productItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/range/{sku}")
    public ResponseEntity<List<String>> productsBySku(@PathVariable String sku) {
        List<String> productList = catalogService.findProductsBySku(sku);
        if (!productList.isEmpty()) {
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
