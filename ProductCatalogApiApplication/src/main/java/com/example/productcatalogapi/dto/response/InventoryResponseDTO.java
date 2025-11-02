package com.example.productcatalogapi.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InventoryResponseDTO {
    private Long id;
    private Integer stockQuantity;
    private String warehouseLocation;
    private LocalDateTime lastUpdatedDate;

}
