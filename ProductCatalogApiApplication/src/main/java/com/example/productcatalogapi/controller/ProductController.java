package com.example.productcatalogapi.controller;

import com.example.productcatalogapi.dto.request.ProductRequestDTO;
import com.example.productcatalogapi.dto.response.ProductResponseDTO;
import com.example.productcatalogapi.entity.Product;
import com.example.productcatalogapi.service.ProductService;
import jakarta.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;   // support?
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAll() {           // -------------- not changed
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {       // changed
        ProductResponseDTO dto = productService.getProductById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO dto) {   //changed
        ProductResponseDTO created = productService.createProduct(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id,
                                                      @Valid @RequestBody ProductRequestDTO dto) {      //changed
        ProductResponseDTO updated = productService.updateProduct(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {    //changed
        productService.deleteProduct(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product with id " + id + " was successfully deleted");
        return ResponseEntity.ok(response);    }
    
    @GetMapping("/filter")
    public ResponseEntity<Page<ProductResponseDTO>> getFiltered(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean available,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "price,asc") String[] sort // or a single string "price,asc"
    ) {
        // parse sort array or string into Sort object
        Sort sortObj;
        // Example: sort = ["price,desc"] or sort = ["price,desc", "name,asc"]
        sortObj = Sort.by(
            Arrays.stream(sort)
                  .map(s -> {
                      String[] parts = s.split(",");
                      return parts.length == 2 && parts[1].equalsIgnoreCase("desc")
                         ? Sort.Order.desc(parts[0])
                         : Sort.Order.asc(parts[0]);
                  })
                  .toList()
        );

        Pageable pageable = PageRequest.of(page, size, sortObj);

        Page<ProductResponseDTO> result = productService.getFilteredProducts(category, available, pageable);
        return ResponseEntity.ok(result);
    }


}
