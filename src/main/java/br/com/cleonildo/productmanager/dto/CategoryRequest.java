package br.com.cleonildo.productmanager.dto;

import br.com.cleonildo.productmanager.entities.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotBlank
        @NotEmpty
        String name,
        @NotNull
        boolean active,
        @NotNull
        CategoryType type
) { }
