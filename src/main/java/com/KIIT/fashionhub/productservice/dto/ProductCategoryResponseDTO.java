package com.KIIT.fashionhub.productservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCategoryResponseDTO {
    private Long id;
    private String sku;
    private Long categoryId;
    private Double price;
    private Double discount;
    private String status;
}

