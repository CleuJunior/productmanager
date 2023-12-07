package br.com.cleonildo.productmanager.dto;

import java.util.Date;

public record ProductResponse(
        Long id,
        String name,
        boolean active,
        String sku,
        String image,
        Date registrationDate,
        int stockQuantity,
        double costValue,
        double icms,
        double saleValue,
        CategoryResponse category
) { }
