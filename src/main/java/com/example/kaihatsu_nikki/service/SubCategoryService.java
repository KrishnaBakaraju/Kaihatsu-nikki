package com.example.kaihatsu_nikki.service;

import com.example.kaihatsu_nikki.model.SubCategory;
import com.example.kaihatsu_nikki.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryService(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    public SubCategory createSubCategory(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    public Optional<SubCategory> getSubCategoryById(Long id) {
        return subCategoryRepository.findById(id);
    }

    public void deleteSubCategory(Long id) {
        subCategoryRepository.deleteById(id);
    }
}
