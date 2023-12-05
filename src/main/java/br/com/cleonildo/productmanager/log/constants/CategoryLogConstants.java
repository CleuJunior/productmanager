package br.com.cleonildo.productmanager.log.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryLogConstants {
    public static final String LIST_CATEGORY = "List of category returns successfully.";
    public static final String FOUND_CATEGORY_BY_ID = "Category id {} found.";
    public static final String CATEGORY_ID_NOT_FOUND_LOG = "Category with id {} not found!";
    public static final String CATEGORY_UPDATE_SUCCESSFULLY = "Category update successfully.";

}
