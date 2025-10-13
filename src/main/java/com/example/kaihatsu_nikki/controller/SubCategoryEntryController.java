package com.example.kaihatsu_nikki.controller;

import com.example.kaihatsu_nikki.model.SubCategoryEntry;
import com.example.kaihatsu_nikki.service.SubCategoryEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entries")
public class SubCategoryEntryController {

    private final SubCategoryEntryService entryService;

    public SubCategoryEntryController(SubCategoryEntryService entryService) {
        this.entryService = entryService;
    }

    @GetMapping
    public List<SubCategoryEntry> getAllEntries() {
        return entryService.getAllEntries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryEntry> getEntryById(@PathVariable Long id) {
        return entryService.getEntryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SubCategoryEntry createEntry(@RequestBody SubCategoryEntry entry) {
        return entryService.createEntry(entry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        entryService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }
}
