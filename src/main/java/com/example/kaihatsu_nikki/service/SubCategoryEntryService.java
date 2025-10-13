package com.example.kaihatsu_nikki.service;

import com.example.kaihatsu_nikki.model.SubCategoryEntry;
import com.example.kaihatsu_nikki.repository.SubCategoryEntryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryEntryService {
    private final SubCategoryEntryRepository entryRepository;

    public SubCategoryEntryService(SubCategoryEntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public List<SubCategoryEntry> getAllEntries() {
        return entryRepository.findAll();
    }

    public SubCategoryEntry createEntry(SubCategoryEntry entry) {
        return entryRepository.save(entry);
    }

    public Optional<SubCategoryEntry> getEntryById(Long id) {
        return entryRepository.findById(id);
    }

    public void deleteEntry(Long id) {
        entryRepository.deleteById(id);
    }
}
