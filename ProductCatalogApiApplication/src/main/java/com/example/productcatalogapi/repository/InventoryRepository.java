package com.example.productcatalogapi.repository;

import com.example.productcatalogapi.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}