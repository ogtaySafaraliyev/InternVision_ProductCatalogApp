package com.example.productcatalogapi.repository;

import com.example.productcatalogapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	
}
