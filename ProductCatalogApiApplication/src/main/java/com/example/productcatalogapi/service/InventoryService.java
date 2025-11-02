package com.example.productcatalogapi.service;

import com.example.productcatalogapi.dto.request.InventoryRequestDTO;
import com.example.productcatalogapi.dto.response.InventoryResponseDTO;
import com.example.productcatalogapi.entity.Inventory;
import com.example.productcatalogapi.entity.Product;
import com.example.productcatalogapi.mapper.EntityDtoMapper;
import com.example.productcatalogapi.repository.InventoryRepository;
import com.example.productcatalogapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryService(InventoryRepository invRepo, ProductRepository prodRepo) {
        this.inventoryRepository = invRepo;
        this.productRepository = prodRepo;
    }

    public List<InventoryResponseDTO> getAll() {
        return inventoryRepository.findAll()
            .stream()
            .map(EntityDtoMapper::toInventoryResponseDTO)
            .collect(Collectors.toList());
    }

    public InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO dto) {
        Inventory existing = inventoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Inventory not found with id " + id));

        // update product if changed

        existing.setStockQuantity(dto.getStockQuantity());
        existing.setWarehouseLocation(dto.getWarehouseLocation());
        existing.setLastUpdatedDate(LocalDateTime.now());

        Inventory updated = inventoryRepository.save(existing);
        return EntityDtoMapper.toInventoryResponseDTO(updated);
    }

    public InventoryResponseDTO createInventory(InventoryRequestDTO dto) {
        Inventory inv = EntityDtoMapper.toInventoryEntity(dto); 
        inv.setLastUpdatedDate(LocalDateTime.now());
        Inventory saved = inventoryRepository.save(inv);
        return EntityDtoMapper.toInventoryResponseDTO(saved);
    }
}
