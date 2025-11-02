package com.example.productcatalogapi.service;


import com.example.productcatalogapi.dto.request.CategoryRequestDTO;
import com.example.productcatalogapi.dto.response.CategoryResponseDTO;
import com.example.productcatalogapi.entity.Category;
import com.example.productcatalogapi.mapper.EntityDtoMapper;
import com.example.productcatalogapi.repository.CategoryRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll()
            .stream()
            .map(EntityDtoMapper::toCategoryResponseDTO)
            .collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
        return EntityDtoMapper.toCategoryResponseDTO(category);
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        Category entity = EntityDtoMapper.toCategoryEntity(dto);
        Category saved = categoryRepository.save(entity);
        return EntityDtoMapper.toCategoryResponseDTO(saved);
    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto) {
        Category existing = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        Category updated = categoryRepository.save(existing);
        return EntityDtoMapper.toCategoryResponseDTO(updated);
    }

    public void deleteCategory(Long id) {
        boolean exists = categoryRepository.existsById(id);
        if (!exists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Category not found with id " + id);
        }
        categoryRepository.deleteById(id);
    }


}
