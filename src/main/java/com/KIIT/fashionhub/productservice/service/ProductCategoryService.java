package com.KIIT.fashionhub.productservice.service;

import com.KIIT.fashionhub.productservice.dto.ProductCategoryResponseDTO;

import java.util.List;

public interface ProductCategoryService {
    void fetchAndSyncFromRequestly();
    List<ProductCategoryResponseDTO> getAllActiveCategories();
    void deactivateProductById(Long id);
}
