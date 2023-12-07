package br.com.cleonildo.productmanager.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Date;

public record ProductRequest(
        @NotNull
        @NotEmpty
        String name,
        boolean active,
        @NotNull
        @NotEmpty
        String sku,
        @NotNull
        @NotEmpty
        String image,
        Date registrationDate,
        @PositiveOrZero
        int stockQuantity,
        @PositiveOrZero
        double costValue,
        @PositiveOrZero
        double icms,
        @PositiveOrZero
        double saleValue,
        @PositiveOrZero
        Long categoryId
) { }
