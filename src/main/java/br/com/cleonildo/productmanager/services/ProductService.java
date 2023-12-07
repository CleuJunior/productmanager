package br.com.cleonildo.productmanager.services;

import br.com.cleonildo.productmanager.dto.ProductRequest;
import br.com.cleonildo.productmanager.dto.ProductResponse;
import br.com.cleonildo.productmanager.entities.Product;
import br.com.cleonildo.productmanager.exceptions.NotFoundException;
import br.com.cleonildo.productmanager.repositories.ICategoryRepository;
import br.com.cleonildo.productmanager.repositories.IProductRepository;
import br.com.cleonildo.productmanager.utils.mapper.ProductMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cleonildo.productmanager.exceptions.ExceptionsConstants.CATEGORY_ID_NOT_FOUND;
import static br.com.cleonildo.productmanager.exceptions.ExceptionsConstants.PRODUCT_ID_NOT_FOUND;
import static br.com.cleonildo.productmanager.log.constants.CategoryLogConstants.CATEGORY_ID_NOT_FOUND_LOG;
import static br.com.cleonildo.productmanager.log.constants.ProductLogConstants.FOUND_PRODUCT_BY_ID;
import static br.com.cleonildo.productmanager.log.constants.ProductLogConstants.LIST_PRODUCT;
import static br.com.cleonildo.productmanager.log.constants.ProductLogConstants.PRODUCT_DELETED_SUCCESSFULLY;
import static br.com.cleonildo.productmanager.log.constants.ProductLogConstants.PRODUCT_ID_NOT_FOUND_LOG;
import static br.com.cleonildo.productmanager.log.constants.ProductLogConstants.PRODUCT_SAVED_SUCCESSFULLY;
import static br.com.cleonildo.productmanager.log.constants.ProductLogConstants.PRODUCT_UPDATE_SUCCESSFULLY;
import static br.com.cleonildo.productmanager.utils.mapper.ProductMapperUtil.toProductResponse;

@Service
@Transactional
public class ProductService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);
    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;

    public ProductService(IProductRepository productRepository, ICategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getListProduct(int page, int size, String sortBy, String sortOrder) {
        var direction = Sort.Direction.ASC;

        if ("desc".equalsIgnoreCase(sortOrder)) {
            direction = Sort.Direction.DESC;
        }

        var pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        var productPage = productRepository.findAll(pageable);
        var responsePage = productPage.map(ProductMapperUtil::toProductResponse);

        LOG.info(LIST_PRODUCT);
        return responsePage;
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        var categoryOptional = productRepository.findById(id);

        if (categoryOptional.isEmpty()) {
            LOG.warn(PRODUCT_ID_NOT_FOUND_LOG, id);
            throw new NotFoundException(PRODUCT_ID_NOT_FOUND);
        }

        LOG.info(FOUND_PRODUCT_BY_ID, id);
        return toProductResponse(categoryOptional.get());
    }

    public ProductResponse saveProduct(ProductRequest request) {
        var category = categoryRepository.findById(request.categoryId());

        if (category.isEmpty()) {
            LOG.warn(CATEGORY_ID_NOT_FOUND_LOG, request.categoryId());
            throw new NotFoundException(CATEGORY_ID_NOT_FOUND);
        }

        var product = Product
                .builder()
                .name(request.name())
                .active(request.active())
                .sku(request.sku())
                .image(request.image())
                .registrationDate(request.registrationDate())
                .stockQuantity(request.stockQuantity())
                .costValue(request.costValue())
                .icms(request.icms())
                .saleValue(request.saleValue())
                .category(category.get())
                .build();

        var response = toProductResponse(productRepository.save(product));
        LOG.info(PRODUCT_SAVED_SUCCESSFULLY);
        return response;
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        var productOptional = productRepository.findById(id);

        if (productOptional.isEmpty())  {
            LOG.warn(PRODUCT_ID_NOT_FOUND_LOG, id);
            throw new NotFoundException(PRODUCT_ID_NOT_FOUND);
        }

        this.updateProduct(productOptional.get(), request);

        ProductResponse response = toProductResponse(productRepository.save(productOptional.get()));
        LOG.info(PRODUCT_UPDATE_SUCCESSFULLY);
        return response;
    }

    private void updateProduct(Product product, ProductRequest request) {
        var category = categoryRepository.findById(request.categoryId());

        if (category.isEmpty()) {
            LOG.warn(CATEGORY_ID_NOT_FOUND_LOG, request.categoryId());
            throw new NotFoundException(CATEGORY_ID_NOT_FOUND);
        }

        product.setName(request.name());
        product.setActive(request.active());
        product.setSku(request.sku());
        product.setImage(request.image());
        product.setRegistrationDate(request.registrationDate());
        product.setStockQuantity(request.stockQuantity());
        product.setCostValue(request.costValue());
        product.setIcms(request.icms());
        product.setSaleValue(request.saleValue());
        product.setCategory(category.get());
    }

    public void deleteProductById(Long id) {
        var categoryOptional = productRepository.findById(id);

        if (categoryOptional.isEmpty()) {
            LOG.warn(PRODUCT_ID_NOT_FOUND_LOG, id);
            throw new NotFoundException(PRODUCT_ID_NOT_FOUND);
        }

        this.productRepository.deleteById(id);
        LOG.info(PRODUCT_DELETED_SUCCESSFULLY);
    }

}
