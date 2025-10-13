package com.example.kaihatsu_nikki.controller;

import com.example.kaihatsu_nikki.model.Category;
import com.example.kaihatsu_nikki.model.SubCategory;
import com.example.kaihatsu_nikki.service.CategoryService;
import com.example.kaihatsu_nikki.service.SubCategoryService;
import com.example.kaihatsu_nikki.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/categories/{categoryId}/subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;
    private final CategoryService categoryService;
    private final UserService userService;

    public SubCategoryController(SubCategoryService subCategoryService,
                                 CategoryService categoryService,
                                 UserService userService) {
        this.subCategoryService = subCategoryService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<SubCategory>> getSubCategories(
            @PathVariable Long userId,
            @PathVariable Long categoryId) {

        return userService.getUserById(userId)
                .flatMap(user -> categoryService.getCategoryById(categoryId)
                        .filter(cat -> cat.getUser().getId().equals(user.getId())))
                .map(category -> ResponseEntity.ok(category.getSubCategories()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SubCategory> createSubCategory(
            @PathVariable Long userId,
            @PathVariable Long categoryId,
            @RequestBody SubCategory subCategory) {

        return userService.getUserById(userId)
                .flatMap(user -> categoryService.getCategoryById(categoryId)
                        .filter(cat -> cat.getUser().getId().equals(user.getId())))
                .map(category -> {
                    subCategory.setCategory(category);
                    SubCategory saved = subCategoryService.createSubCategory(subCategory);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
