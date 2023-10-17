package com.springboot.blogapp.controller;

import com.springboot.blogapp.dto.CategoryDto;
import com.springboot.blogapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable(name = "id") Long categoryId) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);

        return new ResponseEntity<>(updatedCategory, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "id") Long categoryId) {
        categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>("Category deleted", HttpStatus.NO_CONTENT);
    }
}

