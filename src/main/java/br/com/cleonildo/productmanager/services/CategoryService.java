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

import java.util.List;
import java.util.Optional;

import static br.com.cleonildo.productmanager.exceptions.ExceptionsConstants.CATEGORY_ID_NOT_FOUND;
import static br.com.cleonildo.productmanager.log.constants.CategoryLogConstants.CATEGORY_ID_NOT_FOUND_LOG;
import static br.com.cleonildo.productmanager.log.constants.CategoryLogConstants.CATEGORY_UPDATE_SUCCESSFULLY;
import static br.com.cleonildo.productmanager.log.constants.CategoryLogConstants.FOUND_CATEGORY_BY_ID;
import static br.com.cleonildo.productmanager.log.constants.CategoryLogConstants.LIST_CATEGORY;
import static br.com.cleonildo.productmanager.utils.mapper.CategoryMapperUtil.toCategoryResponse;

@Service
public class CategoryService {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);
    private final ICategoryRepository repository;

    public CategoryService(ICategoryRepository repository) {
        this.repository = repository;
    }

    public List<CategoryResponse> getListCategory() {
        List<CategoryResponse> responseList = repository
                .findAll()
                .stream()
                .map(CategoryMapperUtil::toCategoryResponse)
                .toList();

        LOG.info(LIST_CATEGORY);
        return responseList;
    }

    public CategoryResponse getCategoryById(Long id) {
        Optional<Category> categoryOptional = repository.findById(id);

        if (categoryOptional.isEmpty()) {
            LOG.warn(CATEGORY_ID_NOT_FOUND_LOG, id);
            throw new NotFoundException(CATEGORY_ID_NOT_FOUND);
        }

        LOG.info(FOUND_CATEGORY_BY_ID, id);
        return toCategoryResponse(categoryOptional.get());
    }

    public CategoryResponse updateCategory(CategoryRequest request, Long id) {
        Optional<Category> categoryOptional = repository.findById(id);

        if (categoryOptional.isEmpty()) {
            LOG.warn(CATEGORY_ID_NOT_FOUND_LOG, id);
            throw new NotFoundException(CATEGORY_ID_NOT_FOUND);
        }

        categoryOptional.get().setName(request.name());
        categoryOptional.get().setActive(request.active());
        categoryOptional.get().setType(request.type());

        this.repository.save(categoryOptional.get());
        LOG.info(CATEGORY_UPDATE_SUCCESSFULLY);
        return toCategoryResponse(categoryOptional.get());
    }

}
