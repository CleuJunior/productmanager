package br.com.cleonildo.productmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Date;

@JsonPropertyOrder({"id", "name", "active", "sku", "image", "registration_date", "stock_quantity", "cost_value",
        "sale_value", "category"})
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
        @JsonProperty("registration_date")
        Date registrationDate,
        @NotNull
        @PositiveOrZero
        @JsonProperty("stock_quantity")
        int stockQuantity,
        @NotNull
        @PositiveOrZero
        @JsonProperty("cost_value")
        double costValue,
        @NotNull
        @PositiveOrZero
        double icms,
        @NotNull
        @PositiveOrZero
        @JsonProperty("sale_value")
        double saleValue,
        @NotNull
        @PositiveOrZero
        @JsonProperty("category_id")
        Long categoryId
) { }
