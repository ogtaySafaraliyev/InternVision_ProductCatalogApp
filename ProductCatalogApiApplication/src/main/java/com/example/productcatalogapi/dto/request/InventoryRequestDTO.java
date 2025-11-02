package com.example.productcatalogapi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryRequestDTO {    

    @Min(value = 0, message = "Stock quantity must be non-negative")
    private Integer stockQuantity;

    private String warehouseLocation;

}
