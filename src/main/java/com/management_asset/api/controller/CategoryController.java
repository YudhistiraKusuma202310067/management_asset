package com.management_asset.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management_asset.api.Utils;
import com.management_asset.api.model.Category;
import com.management_asset.api.service.implement.CategoryService;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllCategories() {
        try {
            List<Category> categories = categoryService.findAll();
            if (categories == null || categories.isEmpty()) {
                return Utils.generateResponseEntity(HttpStatus.NOT_FOUND, "No Category Found", null);
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Data Found", categories);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching categories", null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Integer id) {
        try {
            Category category = categoryService.findById(id);
            if (category == null) {
                return Utils.generateResponseEntity(HttpStatus.NOT_FOUND, "Category Not Found", null);
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Data Found", category);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching category", null);
        }
    }

    @PostMapping
    public ResponseEntity<Object> saveAsset(@RequestBody Category category) {
        try {
            Category saved = categoryService.save(category);
            return Utils.generateResponseEntity(HttpStatus.OK, "Data has been updated", saved);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving category", null);
        }
    }
}