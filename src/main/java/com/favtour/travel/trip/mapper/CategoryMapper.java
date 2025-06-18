package com.favtour.travel.trip.mapper;

import com.favtour.travel.trip.dto.CategoryRequest;
import com.favtour.travel.trip.dto.CategoryResponse;
import com.favtour.travel.trip.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse mapToCategoryResponse(Category category) {

        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }

    public Category mapToCategory(CategoryRequest categoryRequest) {

        return Category.builder()
                .categoryName(categoryRequest.getCategoryName())
                .build();
    }
}
