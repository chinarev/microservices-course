package com.example.userservice.service;

import com.example.userservice.entities.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ProductService {
  private final RestTemplate restTemplate;

  @Autowired
  public ProductService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @HystrixCommand(
      fallbackMethod = "getDefaultProductInventoryById",
      commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000")
      },
      threadPoolProperties = {
        @HystrixProperty(name = "coreSize", value = "10"),
        @HystrixProperty(name = "maxQueueSize", value = "20")
      })
  public Optional<Product> getProductInventoryById(String id) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();

    log.info("Sending request to catalog...");
    try {
      ResponseEntity<String> catalogResponseEntity =
          restTemplate.getForEntity(
              "http://catalog-service:8181/api/catalog/{id}", String.class, id);
      log.info("Response from catalog: " + catalogResponseEntity.getBody());

      if (catalogResponseEntity.getStatusCode() == HttpStatus.OK) {
        log.info("Sending request to inventory...");
        ResponseEntity<String> inventoryResponseEntity =
            restTemplate.getForEntity(
                "http://inventory-service:8282/api/inventory/{id}", String.class, id);
        log.info("Response from inventory: " + inventoryResponseEntity.getBody());

        return Optional.of(objectMapper.readValue(inventoryResponseEntity.getBody(), Product.class))
            .filter(obj -> obj.getAvailableQuantity() > 0)
            .stream()
            .findFirst();
      } else {
        log.error(
            "Unable to get inventory level for id: "
                + id
                + ", StatusCode: "
                + catalogResponseEntity.getStatusCode());
        return Optional.empty();
      }
    } catch (HttpClientErrorException.NotFound e) {
      log.error("Unable to get catalog for " + id + ", StatusCode: " + e.getStatusCode());
      return Optional.empty();
    }
  }

  @SuppressWarnings("unused")
  public Optional<Product> getDefaultProductInventoryById(String id) {
    log.info("Returning default ProductInventoryById for id: " + id);
    Product defaultProduct = new Product();
    defaultProduct.setUniqId(id);
    defaultProduct.setSku("pp5006380337");
    defaultProduct.setNameTitle("Alfred Dunner® Essential Pull On Capri Pant");
    defaultProduct.setAvailableQuantity(1);

    return Optional.of(defaultProduct);
  }

  @HystrixCommand(
      fallbackMethod = "getDefaultProductsInventoryBySku",
      commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
      },
      threadPoolProperties = {
        @HystrixProperty(name = "coreSize", value = "10"),
        @HystrixProperty(name = "maxQueueSize", value = "30")
      })
  public Optional<List<Product>> getProductsInventoryBySku(String sku)
      throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();

    log.info("Sending request to catalog...");
    try {
      ResponseEntity<String> catalogResponseEntity =
          restTemplate.getForEntity(
              "http://catalog-service:8181/api/catalog/range/" + sku, String.class);
      log.info("Response from catalog: " + catalogResponseEntity.getBody());

      String availableIds =
          Objects.requireNonNull(catalogResponseEntity.getBody()).replaceAll("[\\[\\]\"]", "");

      if (catalogResponseEntity.getStatusCode() == HttpStatus.OK) {
        log.info("Sending request to inventory...");
        ResponseEntity<String> inventoryResponseEntity =
            restTemplate.getForEntity(
                "http://inventory-service:8282/api/inventory?uniqIds=" + availableIds,
                String.class);
        log.info("Response from inventory: " + inventoryResponseEntity.getBody());

        return Optional.of(
            Arrays.stream(
                    objectMapper.readValue(inventoryResponseEntity.getBody(), Product[].class))
                .sequential()
                .filter(obj -> obj.getAvailableQuantity() > 0)
                .collect(Collectors.toList()));
      } else {
        log.error(
            "Unable to get inventory level for sku: "
                + sku
                + ", StatusCode: "
                + catalogResponseEntity.getStatusCode());
        return Optional.empty();
      }
    } catch (HttpClientErrorException.NotFound e) {
      log.error("Unable to get catalog for " + sku + ", StatusCode: " + e.getStatusCode());
      return Optional.empty();
    }
  }

  @SuppressWarnings("unused")
  public Optional<List<Product>> getDefaultProductsInventoryBySku(String sku) {
    log.info("Returning default getDefaultProductsInventoryBySku for sku: " + sku);
    List<Product> defaultProducts = new ArrayList<>();
    Product defaultProduct = new Product();
    defaultProduct.setUniqId("b6c0b6bea69c722939585baeac73c13d");
    defaultProduct.setSku(sku);
    defaultProduct.setNameTitle("Alfred Dunner® Essential Pull On Capri Pant");
    defaultProduct.setAvailableQuantity(1);
    defaultProducts.add(defaultProduct);

    return Optional.of(defaultProducts);
  }
}
