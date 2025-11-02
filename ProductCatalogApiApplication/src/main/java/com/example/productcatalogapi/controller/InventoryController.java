package com.example.productcatalogapi.controller;

import com.example.productcatalogapi.dto.request.InventoryRequestDTO;
import com.example.productcatalogapi.dto.response.InventoryResponseDTO;
import com.example.productcatalogapi.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService svc) {
        this.inventoryService = svc;
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAll() {
        List<InventoryResponseDTO> list = inventoryService.getAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<InventoryResponseDTO> create(@Valid @RequestBody InventoryRequestDTO dto) {
        InventoryResponseDTO created = inventoryService.createInventory(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> update(@PathVariable Long id,
                                                       @Valid @RequestBody InventoryRequestDTO dto) {
        InventoryResponseDTO updated = inventoryService.updateInventory(id, dto);
        return ResponseEntity.ok(updated);
    }
}
