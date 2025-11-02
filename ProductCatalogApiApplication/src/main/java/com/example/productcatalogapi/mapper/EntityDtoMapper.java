package com.example.productcatalogapi.mapper;

import com.example.productcatalogapi.dto.request.CategoryRequestDTO;
import com.example.productcatalogapi.dto.request.InventoryRequestDTO;
import com.example.productcatalogapi.dto.request.ProductRequestDTO;
import com.example.productcatalogapi.dto.response.CategoryResponseDTO;
import com.example.productcatalogapi.dto.response.InventoryResponseDTO;
import com.example.productcatalogapi.dto.response.ProductResponseDTO;
import com.example.productcatalogapi.entity.Product;
import com.example.productcatalogapi.entity.Category;
import com.example.productcatalogapi.entity.Inventory;

public class EntityDtoMapper {

    /**
     * Convert request DTO to Product entity.
     * NOTE: category must be set externally (e.g., service layer finds Category by ID).
     */
	public static Product toProductEntity(ProductRequestDTO dto) {
	    Product product = new Product();
	    product.setName(dto.getName());
	    product.setDescription(dto.getDescription());
	    product.setPrice(dto.getPrice());
	    product.setAvailable(dto.getAvailable());
	    // no categoryName / categoryId set
	    return product;
	}

    /**
     * Convert Product entity to response DTO.
     */
    public static ProductResponseDTO toProductResponseDTO(Product product) {
        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setAvailable(product.isAvailable()); //// changes
        
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        return response;
    }
    
 // --- Category mapping ---
    public static Category toCategoryEntity(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    public static CategoryResponseDTO toCategoryResponseDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        return dto;
    }

    // --- Inventory mapping ---
    public static Inventory toInventoryEntity(InventoryRequestDTO dto) {
        Inventory inv = new Inventory();
        inv.setStockQuantity(dto.getStockQuantity());
        inv.setWarehouseLocation(dto.getWarehouseLocation());
        // lastUpdatedDate may be set by service or automatically
        return inv;
    }

    public static InventoryResponseDTO toInventoryResponseDTO(Inventory inv) {
        InventoryResponseDTO dto = new InventoryResponseDTO();
        dto.setId(inv.getId());
        dto.setStockQuantity(inv.getStockQuantity());
        dto.setWarehouseLocation(inv.getWarehouseLocation());
        dto.setLastUpdatedDate(inv.getLastUpdatedDate());
        return dto;
    }
}
