package br.com.cleonildo.productmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder({"id", "name", "active", "sku", "image", "registration_date", "stock_quantity", "cost_value",
        "sale_value", "category"})
public record ProductResponse(
        Long id,
        String name,
        boolean active,
        String sku,
        String image,
        @JsonProperty("registration_date")
        Date registrationDate,
        @JsonProperty("stock_quantity")
        int stockQuantity,
        @JsonProperty("cost_value")
        double costValue,
        double icms,
        @JsonProperty("sale_value")
        double saleValue,
        CategoryResponse category
) { }
