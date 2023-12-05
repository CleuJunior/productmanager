package br.com.cleonildo.productmanager.utils.factory;

import br.com.cleonildo.productmanager.dto.CategoryRequest;
import br.com.cleonildo.productmanager.dto.CategoryResponse;
import br.com.cleonildo.productmanager.entities.Category;
import br.com.cleonildo.productmanager.entities.CategoryType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryFactoryUtil {

    public static Category buildCategory(Long id, String name, boolean active, CategoryType type) {
        return new Category(id, name, active, type);
    }

    public static CategoryResponse buildCategoryResponse(Long id, String name, boolean active, CategoryType type) {
        return new CategoryResponse(id, name, active, type.getTypeName());
    }

    public static CategoryRequest buildCategoryRequest(String name, boolean active, CategoryType type) {
        return new CategoryRequest(name, active, type);
    }
}
