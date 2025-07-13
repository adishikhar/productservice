package com.KIIT.fashionhub.productservice.repository;

import com.KIIT.fashionhub.productservice.dao.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Optional<ProductCategory> findBySku(String sku);
    List<ProductCategory> findAllByStatus(String status);
}

