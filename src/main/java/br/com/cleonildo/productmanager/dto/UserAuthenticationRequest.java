package br.com.cleonildo.productmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UserAuthenticationRequest(
        @NotBlank
        @NotEmpty
        String login,
        @NotBlank
        @NotEmpty
        String password
) { }
