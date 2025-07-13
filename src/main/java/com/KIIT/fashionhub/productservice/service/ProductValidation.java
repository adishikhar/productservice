package com.KIIT.fashionhub.productservice.service;

import com.KIIT.fashionhub.productservice.dto.ProductCategoryRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductValidation {

    public void validateRequest(ProductCategoryRequestDTO dto) {
        if (dto.getSku() == null || dto.getSku().isEmpty()) {
            throw new IllegalArgumentException("SKU is required");
        }

        if (dto.getCategoryId() == null || dto.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Category ID must be positive");
        }

        if (dto.getPrice() == null || dto.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
    }
}

