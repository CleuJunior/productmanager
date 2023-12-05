package br.com.cleonildo.productmanager.controllers;

import br.com.cleonildo.productmanager.dto.CategoryRequest;
import br.com.cleonildo.productmanager.dto.CategoryResponse;
import br.com.cleonildo.productmanager.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getCategoryById(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getListCategory() {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getListCategory());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> updateBrand(@PathVariable Long id, @RequestBody CategoryRequest request) {
        CategoryResponse response = this.service.updateCategory(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
