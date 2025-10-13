package com.example.kaihatsu_nikki.controller;

import com.example.kaihatsu_nikki.model.DailyDiary;
import com.example.kaihatsu_nikki.service.DailyDiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/diary")
public class DailyDiaryController {

    private final DailyDiaryService diaryService;

    public DailyDiaryController(DailyDiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @GetMapping
    public List<DailyDiary> getAllDiaries() {
        return diaryService.getAllEntries();
    }

    @GetMapping("/{date}")
    public ResponseEntity<DailyDiary> getDiaryByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return diaryService.getByDate(localDate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DailyDiary createDiary(@RequestBody DailyDiary diary) {
        return diaryService.createDiaryEntry(diary);
    }

    @PutMapping
    public DailyDiary updateDiary(@RequestBody DailyDiary diary) {
        return diaryService.createDiaryEntry(diary);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable Long id) {
        diaryService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }
}
