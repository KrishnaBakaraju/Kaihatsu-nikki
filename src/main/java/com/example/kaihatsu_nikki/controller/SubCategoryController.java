package com.example.kaihatsu_nikki.controller;

import com.example.kaihatsu_nikki.model.SubCategory;
import com.example.kaihatsu_nikki.service.SubCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @GetMapping
    public List<SubCategory> getAllSubCategories() {
        return subCategoryService.getAllSubCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable Long id) {
        return subCategoryService.getSubCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SubCategory createSubCategory(@RequestBody SubCategory subCategory) {
        return subCategoryService.createSubCategory(subCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long id) {
        subCategoryService.deleteSubCategory(id);
        return ResponseEntity.noContent().build();
    }
}
