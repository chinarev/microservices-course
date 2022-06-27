package com.example.userservice.entities;

import lombok.Data;

@Data
public class Product {
    private String uniqId;
    private String sku;
    private String nameTitle;
    private Integer availableQuantity;
}
