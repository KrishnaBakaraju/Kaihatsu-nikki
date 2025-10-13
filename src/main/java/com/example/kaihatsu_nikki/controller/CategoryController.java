package com.example.kaihatsu_nikki.controller;

import com.example.kaihatsu_nikki.model.Category;
import com.example.kaihatsu_nikki.model.SubCategory;
import com.example.kaihatsu_nikki.service.CategoryService;
import com.example.kaihatsu_nikki.service.SubCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;

    public CategoryController(CategoryService categoryService, SubCategoryService subCategoryService) {
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{categoryId}/subcategories")
    public ResponseEntity<SubCategory> createSubCategoryUnderCategory(
            @PathVariable Long categoryId,
            @RequestBody SubCategory subCategory) {

        return categoryService.getCategoryById(categoryId)
                .map(category -> {
                    subCategory.setCategory(category);
                    SubCategory saved = subCategoryService.createSubCategory(subCategory);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{categoryId}/subcategories")
    public ResponseEntity<List<SubCategory>> getSubCategoriesByCategory(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId)
                .map(category -> ResponseEntity.ok(category.getSubCategories()))
                .orElse(ResponseEntity.notFound().build());
    }
}
