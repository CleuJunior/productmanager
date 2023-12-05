package br.com.cleonildo.productmanager.utils.mapper;

import br.com.cleonildo.productmanager.dto.CategoryResponse;
import br.com.cleonildo.productmanager.entities.Category;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapperUtil {

    public static CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.isActive(), category.getType().getTypeName());
    }
}
