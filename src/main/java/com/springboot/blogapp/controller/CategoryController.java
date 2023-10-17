package com.springboot.blogapp.controller;

import com.springboot.blogapp.dto.CategoryDto;
import com.springboot.blogapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto addedCategory = categoryService.addCategory(categoryDto);

        return new ResponseEntity<>(addedCategory, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable(name = "id") Long categoryId) {
        CategoryDto category = categoryService.getCategory(categoryId);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
