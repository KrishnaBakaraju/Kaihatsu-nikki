package com.example.kaihatsu_nikki.controller;

import com.example.kaihatsu_nikki.model.SubCategory;
import com.example.kaihatsu_nikki.model.SubCategoryEntry;
import com.example.kaihatsu_nikki.service.SubCategoryEntryService;
import com.example.kaihatsu_nikki.service.SubCategoryService;
import com.example.kaihatsu_nikki.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/subcategories/{subCategoryId}/entries")
public class SubCategoryEntryController {

    private final SubCategoryEntryService entryService;
    private final SubCategoryService subCategoryService;
    private final UserService userService;

    public SubCategoryEntryController(SubCategoryEntryService entryService,
                                      SubCategoryService subCategoryService,
                                      UserService userService) {
        this.entryService = entryService;
        this.subCategoryService = subCategoryService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<SubCategoryEntry>> getEntriesForUserSubCategory(
            @PathVariable Long userId,
            @PathVariable Long subCategoryId) {

        return userService.getUserById(userId)
                .flatMap(user -> subCategoryService.getSubCategoryById(subCategoryId)
                        .filter(subCat -> subCat.getCategory().getUser().getId().equals(user.getId())))
                .map(subCategory -> ResponseEntity.ok(subCategory.getEntries()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SubCategoryEntry> createEntryForUserSubCategory(
            @PathVariable Long userId,
            @PathVariable Long subCategoryId,
            @RequestBody SubCategoryEntry entry) {

        return userService.getUserById(userId)
                .flatMap(user -> subCategoryService.getSubCategoryById(subCategoryId)
                        .filter(subCat -> subCat.getCategory().getUser().getId().equals(user.getId())))
                .map(subCategory -> {
                    entry.setSubCategory(subCategory);
                    SubCategoryEntry saved = entryService.createEntry(entry);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
