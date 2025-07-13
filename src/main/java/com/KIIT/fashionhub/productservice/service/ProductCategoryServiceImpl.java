package com.KIIT.fashionhub.productservice.service;

import com.KIIT.fashionhub.productservice.dao.ProductCategory;
import com.KIIT.fashionhub.productservice.dto.ProductCategoryRequestDTO;
import com.KIIT.fashionhub.productservice.dto.ProductCategoryResponseDTO;
import com.KIIT.fashionhub.productservice.exception.ResourceNotFoundException;
import com.KIIT.fashionhub.productservice.repository.ProductCategoryRepository;
import com.KIIT.fashionhub.productservice.utils.DiscountUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository repository;
    private final ProductValidation validator;
    private final ObjectMapper objectMapper;

    @Override
    public void fetchAndSyncFromRequestly() {
        try {
            String json = "[{\"sku\":\"SKU123\",\"price\":1000.0,\"category_id\":1}," +
                    "{\"sku\":\"SKU456\",\"price\":2000.0,\"category_id\":2}]";

            List<ProductCategoryRequestDTO> requestList = objectMapper.readValue(
                    json,
                    new TypeReference<List<ProductCategoryRequestDTO>>() {}
            );

            for (ProductCategoryRequestDTO dto : requestList) {
                validator.validateRequest(dto);
                Optional<ProductCategory> existing = repository.findBySku(dto.getSku());

                if (existing.isPresent()) {
                    ProductCategory entity = existing.get();
                    entity.setCategoryId(dto.getCategoryId());
                    entity.setPrice(dto.getPrice());
                    entity.setDiscount(DiscountUtil.getDiscountByCategory(dto.getCategoryId()));
                    entity.setStatus("ACTIVE");
                    repository.save(entity);
                    log.info("Updated product category for SKU {}", dto.getSku());
                } else {
                    ProductCategory newEntity = ProductCategory.builder()
                            .sku(dto.getSku())
                            .categoryId(dto.getCategoryId())
                            .price(dto.getPrice())
                            .discount(DiscountUtil.getDiscountByCategory(dto.getCategoryId()))
                            .status("ACTIVE")
                            .build();
                    repository.save(newEntity);
                    log.info("Inserted new product category for SKU {}", dto.getSku());
                }
            }

        } catch (Exception e) {
            log.error("Error syncing data: {}", e.getMessage());
            throw new RuntimeException("Failed to sync product categories", e);
        }
    }

    @Override
    public List<ProductCategoryResponseDTO> getAllActiveCategories() {
        return repository.findAllByStatus("ACTIVE")
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivateProductById(Long id) {
        ProductCategory entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product ID not found: " + id));
        entity.setStatus("INACTIVE");
        repository.save(entity);
        log.info("Deactivated product category with ID {}", id);
    }

    private ProductCategoryResponseDTO mapToResponseDTO(ProductCategory entity) {
        return ProductCategoryResponseDTO.builder()
                .id(entity.getId())
                .sku(entity.getSku())
                .categoryId(entity.getCategoryId())
                .price(entity.getPrice())
                .discount(entity.getDiscount())
                .status(entity.getStatus())
                .build();
    }
}

