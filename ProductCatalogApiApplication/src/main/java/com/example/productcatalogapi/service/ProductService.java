package com.example.productcatalogapi.service;

import com.example.productcatalogapi.dto.request.ProductRequestDTO;
import com.example.productcatalogapi.dto.response.ProductResponseDTO;
import com.example.productcatalogapi.entity.Category;
import com.example.productcatalogapi.entity.Product;
import com.example.productcatalogapi.mapper.EntityDtoMapper;
import com.example.productcatalogapi.repository.CategoryRepository;
import com.example.productcatalogapi.repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getAllProducts() { // -------------- not changed
		return productRepository.findAll();
	}

	public ProductResponseDTO getProductById(Long id) { // changed
		Product p = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found with id " + id));
		return EntityDtoMapper.toProductResponseDTO(p);
	}

	public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Product newProd = EntityDtoMapper.toProductEntity(dto);
        Product saved = productRepository.save(newProd);
        return EntityDtoMapper.toProductResponseDTO(saved);
    }

	public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) { // changed
		Product existing = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found with id " + id));

		// update fields
		existing.setName(dto.getName());
		existing.setDescription(dto.getDescription());
		existing.setPrice(dto.getPrice());
		existing.setAvailable(dto.getAvailable());

		Product updated = productRepository.save(existing);
		return EntityDtoMapper.toProductResponseDTO(updated);
	}

	public void deleteProduct(Long id) { // changed
		if (!productRepository.existsById(id)) {
			throw new RuntimeException("Product not found with id " + id);
		}
		productRepository.deleteById(id);
	}

	public Page<ProductResponseDTO> getFilteredProducts(String categoryName, Boolean available, Pageable pageable) {
		Page<Product> page;

		page = productRepository.findAll(pageable);

		return page.map(EntityDtoMapper::toProductResponseDTO);
	}

}
