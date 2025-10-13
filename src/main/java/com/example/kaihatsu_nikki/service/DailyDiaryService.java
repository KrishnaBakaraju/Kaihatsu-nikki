package com.example.kaihatsu_nikki.service;

import com.example.kaihatsu_nikki.model.DailyDiary;
import com.example.kaihatsu_nikki.repository.DailyDiaryRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DailyDiaryService {
    private final DailyDiaryRepository diaryRepository;

    public DailyDiaryService(DailyDiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public List<DailyDiary> getAllEntries() {
        return diaryRepository.findAll();
    }

    public DailyDiary createDiaryEntry(DailyDiary diary) {
        return diaryRepository.save(diary);
    }

    public Optional<DailyDiary> getByDate(LocalDate date) {
        return diaryRepository.findByDate(date);
    }

    public void deleteEntry(Long id) {
        diaryRepository.deleteById(id);
    }
}
