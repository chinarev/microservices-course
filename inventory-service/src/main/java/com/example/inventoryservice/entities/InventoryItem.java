package com.example.inventoryservice.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "INVENTORY")
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String uniqId;
    private String sku;
    private String nameTitle;
}
