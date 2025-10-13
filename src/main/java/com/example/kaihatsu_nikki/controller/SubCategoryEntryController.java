package com.example.kaihatsu_nikki.controller;

import com.example.kaihatsu_nikki.model.SubCategory;
import com.example.kaihatsu_nikki.model.SubCategoryEntry;
import com.example.kaihatsu_nikki.service.SubCategoryEntryService;
import com.example.kaihatsu_nikki.service.SubCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubCategoryEntryController {

    private final SubCategoryEntryService entryService;
    private final SubCategoryService subCategoryService;

    public SubCategoryEntryController(SubCategoryEntryService entryService, SubCategoryService subCategoryService) {
        this.entryService = entryService;
        this.subCategoryService = subCategoryService;
    }

    @GetMapping("/entries")
    public List<SubCategoryEntry> getAllEntries() {
        return entryService.getAllEntries();
    }

    @GetMapping("/entries/{id}")
    public ResponseEntity<SubCategoryEntry> getEntryById(@PathVariable Long id) {
        return entryService.getEntryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ NEW ENDPOINT: Create entry under a specific subcategory
    @PostMapping("/subcategories/{subCategoryId}/entries")
    public ResponseEntity<SubCategoryEntry> createEntryUnderSubCategory(
            @PathVariable Long subCategoryId,
            @RequestBody SubCategoryEntry entry) {

        return subCategoryService.getSubCategoryById(subCategoryId)
                .map(subCategory -> {
                    entry.setSubCategory(subCategory);
                    SubCategoryEntry saved = entryService.createEntry(entry);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/entries/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        entryService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }
}
