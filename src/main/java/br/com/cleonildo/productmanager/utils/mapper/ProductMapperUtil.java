package br.com.cleonildo.productmanager.utils.mapper;

import br.com.cleonildo.productmanager.dto.ProductResponse;
import br.com.cleonildo.productmanager.entities.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapperUtil {

    public static ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.isActive(),
                product.getSku(),
                product.getImage(),
                product.getRegistrationDate(),
                product.getStockQuantity(),
                product.getCostValue(),
                product.getIcms(),
                product.getSaleValue(),
                CategoryMapperUtil.toCategoryResponse(product.getCategory())
        );
    }
}
