package br.com.cleonildo.productmanager.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionsConstants {
    public static final String CATEGORY_ID_NOT_FOUND = "Category not found!";
    public static final String PRODUCT_ID_NOT_FOUND = "Product not found!";

}
