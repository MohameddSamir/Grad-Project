package com.favtour.travel.trip.controller;

import com.favtour.travel.core.payload.ApiResponse;
import com.favtour.travel.trip.dto.CategoryRequest;
import com.favtour.travel.trip.dto.CategoryResponse;
import com.favtour.travel.trip.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "All Categories are ready", categoryService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategory(@PathVariable int id) {
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Category found", categoryService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body
                (new ApiResponse<>(true, "Category created successfully", categoryService.save(categoryRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@PathVariable int id,
                                                                        @Valid @RequestBody CategoryRequest categoryRequest) {

        return ResponseEntity.ok
                (new ApiResponse<>(true, "Category updated successfully", categoryService.update(id, categoryRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable int id) {
        categoryService.delete(id);
        return ResponseEntity.ok
                (new ApiResponse<>(true, "Category has been deleted successfully", null));
    }
}
