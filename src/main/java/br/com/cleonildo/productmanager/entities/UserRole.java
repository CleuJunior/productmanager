package br.com.cleonildo.productmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    ADMIN("admin"), STOCKIST("stockist");

    private final String role;
}
