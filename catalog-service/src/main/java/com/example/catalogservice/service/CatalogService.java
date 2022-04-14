package com.example.catalogservice.service;

import com.example.catalogservice.entities.Product;
import com.example.catalogservice.repository.CatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CatalogService {
    private final CatalogRepository catalogRepository;

    @Autowired
    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public Optional<Product> findProductById(String id) {
        return catalogRepository.findByUniqId(id);
    }

    public List<Product> findProductsBySku(String sku) {
        return catalogRepository.findBySku(sku);
    }
}