package com.KIIT.fashionhub.productservice.utils;

public class DiscountUtil {

    public static double getDiscountByCategory(Long categoryId) {
        return switch (categoryId.intValue()) {
            case 1 -> 25.0;
            case 2 -> 39.0;
            case 3 -> 50.0;
            case 4 -> 20.0;
            default -> 0.0;
        };
    }
}

