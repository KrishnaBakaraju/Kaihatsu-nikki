package com.example.kaihatsu_nikki.service;

import com.example.kaihatsu_nikki.model.DailyDiary;
import com.example.kaihatsu_nikki.model.User;
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

    public List<DailyDiary> getAllDiaries() {
        return diaryRepository.findAll();
    }

    public Optional<DailyDiary> getById(Long id) {
        return diaryRepository.findById(id);
    }

    // ✅ Correct method signature
    public Optional<DailyDiary> getByDateAndUser(LocalDate date, User user) {
        return diaryRepository.findByDateAndUser(date, user);
    }

    public DailyDiary createDiaryEntry(DailyDiary diary) {
        return diaryRepository.save(diary);
    }

    public void deleteEntry(Long id) {
        diaryRepository.deleteById(id);
    }
}
