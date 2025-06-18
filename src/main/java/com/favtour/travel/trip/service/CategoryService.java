package com.favtour.travel.trip.service;

import com.favtour.travel.shared.EntityNotFoundException;
import com.favtour.travel.trip.dto.CategoryRequest;
import com.favtour.travel.trip.dto.CategoryResponse;
import com.favtour.travel.trip.entity.Category;
import com.favtour.travel.trip.mapper.CategoryMapper;
import com.favtour.travel.trip.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper ::mapToCategoryResponse).collect(Collectors.toList());
    }

    public Category findByIdOrThrow(int id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    public CategoryResponse findById(int id) {
        return categoryMapper.mapToCategoryResponse(categoryRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("category not found")));
    }

    public CategoryResponse save(CategoryRequest categoryRequest) {
        return categoryMapper.mapToCategoryResponse(categoryRepository.save(categoryMapper.mapToCategory(categoryRequest)));
    }

    public CategoryResponse update(int categoryId, CategoryRequest categoryRequest) {

        Category category = findByIdOrThrow(categoryId);
        return categoryMapper.mapToCategoryResponse(categoryRepository.save(categoryMapper.mapToCategory(categoryRequest)));
    }

    public void delete(int categoryId) {
        Category category = findByIdOrThrow(categoryId);
        categoryRepository.delete(category);
    }
}
