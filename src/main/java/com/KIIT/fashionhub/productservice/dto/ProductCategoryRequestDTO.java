package com.KIIT.fashionhub.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryRequestDTO {
    private String sku;
    private Long categoryId;
    private Double price;
}

