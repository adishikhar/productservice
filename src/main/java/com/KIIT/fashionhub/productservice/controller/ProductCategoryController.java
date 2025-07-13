package com.KIIT.fashionhub.productservice.controller;

import com.KIIT.fashionhub.productservice.dto.ProductCategoryResponseDTO;
import com.KIIT.fashionhub.productservice.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @PostMapping("/sync")
    public ResponseEntity<String> syncFromRequestly() {
        productCategoryService.fetchAndSyncFromRequestly();
        return ResponseEntity.ok("Sync completed successfully.");
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProductCategoryResponseDTO>> getAllActive() {
        return ResponseEntity.ok(productCategoryService.getAllActiveCategories());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        productCategoryService.deactivateProductById(id);
        return ResponseEntity.noContent().build();
    }
}
