package br.com.cleonildo.productmanager.dto;

import br.com.cleonildo.productmanager.entities.CategoryType;

public record CategoryRequest(String name, boolean active, CategoryType type) {
}
