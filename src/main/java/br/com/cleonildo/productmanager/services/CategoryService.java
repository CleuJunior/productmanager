package br.com.cleonildo.productmanager.services;

import br.com.cleonildo.productmanager.dto.CategoryRequest;
import br.com.cleonildo.productmanager.dto.CategoryResponse;
import br.com.cleonildo.productmanager.entities.Category;
import br.com.cleonildo.productmanager.exceptions.NotFoundException;
import br.com.cleonildo.productmanager.repositories.ICategoryRepository;
import br.com.cleonildo.productmanager.utils.mapper.CategoryMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.cleonildo.productmanager.exceptions.ExceptionsConstants.CATEGORY_ID_NOT_FOUND;
import static br.com.cleonildo.productmanager.log.constants.CategoryLogConstants.CATEGORY_DELETED_SUCCESSFULLY;
import static br.com.cleonildo.productmanager.log.constants.CategoryLogConstants.CATEGORY_FOUND_BY_ID;
import static br.com.cleonildo.productmanager.log.constants.CategoryLogConstants.CATEGORY_ID_NOT_FOUND_LOG;
import static br.com.cleonildo.productmanager.log.constants.CategoryLogConstants.CATEGORY_LIST;
import static br.com.cleonildo.productmanager.log.constants.CategoryLogConstants.CATEGORY_SAVED_SUCCESSFULLY;
import static br.com.cleonildo.productmanager.log.constants.CategoryLogConstants.CATEGORY_UPDATE_SUCCESSFULLY;
import static br.com.cleonildo.productmanager.utils.mapper.CategoryMapperUtil.toCategoryResponse;

@Service
@Transactional
public class CategoryService {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);
    private final ICategoryRepository repository;

    public CategoryService(ICategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getListCategory() {
        var responseList = repository
                .findAll()
                .stream()
                .map(CategoryMapperUtil::toCategoryResponse)
                .toList();

        LOG.info(CATEGORY_LIST);
        return responseList;
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
       var categoryOptional = repository.findById(id);

        if (categoryOptional.isEmpty()) {
            LOG.warn(CATEGORY_ID_NOT_FOUND_LOG, id);
            throw new NotFoundException(CATEGORY_ID_NOT_FOUND);
        }

        LOG.info(CATEGORY_FOUND_BY_ID, id);
        return toCategoryResponse(categoryOptional.get());
    }

    public CategoryResponse saveCategory(CategoryRequest request) {
        var category = new Category(request.name(), request.active(), request.type());
        var response = toCategoryResponse(repository.save(category));

        LOG.info(CATEGORY_SAVED_SUCCESSFULLY);
        return response;
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        var categoryOptional = repository.findById(id);

        if (categoryOptional.isEmpty()) {
            LOG.warn(CATEGORY_ID_NOT_FOUND_LOG, id);
            throw new NotFoundException(CATEGORY_ID_NOT_FOUND);
        }

        categoryOptional.get().setName(request.name());
        categoryOptional.get().setActive(request.active());
        categoryOptional.get().setType(request.type());

        var response = toCategoryResponse(repository.save(categoryOptional.get()));
        LOG.info(CATEGORY_UPDATE_SUCCESSFULLY);
        return response;
    }

    public void deleteCategoryById(Long id) {
        var categoryOptional = this.repository.findById(id);

        if (categoryOptional.isEmpty()) {
            LOG.warn(CATEGORY_ID_NOT_FOUND_LOG, id);
            throw new NotFoundException(CATEGORY_ID_NOT_FOUND);
        }

        LOG.info(CATEGORY_DELETED_SUCCESSFULLY);
        this.repository.delete(categoryOptional.get());
    }

}
