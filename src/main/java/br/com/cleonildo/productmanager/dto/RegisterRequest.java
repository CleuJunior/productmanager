package br.com.cleonildo.productmanager.dto;

import br.com.cleonildo.productmanager.entities.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotBlank
        @NotEmpty
        String login,
        @NotBlank
        @NotEmpty
        String password,
        @NotNull
        UserRole role
) { }
