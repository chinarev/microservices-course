package com.example.catalogservice.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String uniqId;
    private String sku;
    private String nameTitle;
    private String description;
    private String listPrice;
    private String salePrice;
    private String category;
    private String categoryTree;
    private String averageProductRating;
    private String productUrl;
    private String productImageUrls;
    private String brand;
    private String totalNumberReviews;
    private String reviews;
}
