package com.example.kaihatsu_nikki.controller;

import com.example.kaihatsu_nikki.model.Category;
//import com.example.kaihatsu_nikki.model.SubCategory;
import com.example.kaihatsu_nikki.service.CategoryService;
import com.example.kaihatsu_nikki.service.SubCategoryService;
import com.example.kaihatsu_nikki.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/categories")
public class UserCategoryController {

    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;
    private final UserService userService;

    public UserCategoryController(CategoryService categoryService,
                              SubCategoryService subCategoryService,
                              UserService userService) {
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
        this.userService = userService;
    }

    // 🟢 Get all categories for a user
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategoriesForUser(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(user -> ResponseEntity.ok(user.getCategories()))
                .orElse(ResponseEntity.notFound().build());
    }

    // 🟢 Get single category (user-scoped)
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryForUser(
            @PathVariable Long userId,
            @PathVariable Long categoryId) {

        return userService.getUserById(userId)
                .flatMap(user -> categoryService.getCategoryById(categoryId)
                        .filter(cat -> cat.getUser().getId().equals(user.getId())))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🟢 Create new category for a user
    @PostMapping
    public ResponseEntity<Category> createCategoryForUser(
            @PathVariable Long userId,
            @RequestBody Category category) {

        return userService.getUserById(userId)
                .map(user -> {
                    category.setUser(user);
                    Category saved = categoryService.createCategory(category);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 🟢 Delete category for a user
    @DeleteMapping("/{categoryId}")
public ResponseEntity<Void> deleteCategoryForUser(
        @PathVariable Long userId,
        @PathVariable Long categoryId) {

    return userService.getUserById(userId)
            .flatMap(user -> categoryService.getCategoryById(categoryId)
                    .filter(cat -> cat.getUser().getId().equals(user.getId())))
            .<ResponseEntity<Void>>map(cat -> {
                categoryService.deleteCategory(categoryId);
                return ResponseEntity.noContent().build();
            })
            .orElse(ResponseEntity.notFound().build());
}







    
   
}
