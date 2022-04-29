package com.example.inventoryservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDto {
    private String uniqId;
    private String sku;
    private String nameTitle;
    private Integer availableQuantity = 0;
}
