package com.kk.blog_app.domain.mappers;


import com.kk.blog_app.domain.dtos.response.CategoryResponse;
import com.kk.blog_app.domain.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toDto(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }

}
