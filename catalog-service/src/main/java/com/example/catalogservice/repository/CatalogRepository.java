package com.example.catalogservice.repository;

import com.example.catalogservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByUniqId(String id);

    List<Product> findBySku(String sku);
}
