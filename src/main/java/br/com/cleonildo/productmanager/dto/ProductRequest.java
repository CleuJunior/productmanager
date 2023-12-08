package br.com.cleonildo.productmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Date;

public record ProductRequest(
        @NotBlank
        @NotEmpty
        String name,
        @NotNull
        boolean active,
        @NotBlank
        @NotEmpty
        String sku,
        @NotNull
        @NotEmpty
        String image,
        @NotNull
        Date registrationDate,
        @NotNull
        @PositiveOrZero
        int stockQuantity,
        @NotNull
        @PositiveOrZero
        double costValue,
        @NotNull
        @PositiveOrZero
        double icms,
        @NotNull
        @PositiveOrZero
        double saleValue,
        @NotNull
        @PositiveOrZero
        Long categoryId
) { }
