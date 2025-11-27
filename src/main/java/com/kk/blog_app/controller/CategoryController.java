package com.kk.blog_app.controller;


import com.kk.blog_app.domain.dtos.response.CategoryResponse;
import com.kk.blog_app.domain.entities.Category;
import com.kk.blog_app.domain.mappers.CategoryMapper;
import com.kk.blog_app.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories.stream()
                .map(categoryMapper::toDto)
                .toList()
        );
    }

}
