package com.example.kaihatsu_nikki.controller;

import com.example.kaihatsu_nikki.model.DailyDiary;
import com.example.kaihatsu_nikki.service.DailyDiaryService;
import com.example.kaihatsu_nikki.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/diary")
public class DailyDiaryController {

    private final DailyDiaryService diaryService;
    private final UserService userService;

    public DailyDiaryController(DailyDiaryService diaryService, UserService userService) {
        this.diaryService = diaryService;
        this.userService = userService;
    }

    // 🟢 Get all diary entries for a user
    @GetMapping
    public ResponseEntity<List<DailyDiary>> getAllDiaries(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(user -> ResponseEntity.ok(user.getDiaryEntries()))
                .orElse(ResponseEntity.notFound().build());
    }

    // 🟢 Get diary entry for specific date
    @GetMapping("/{date}")
    public ResponseEntity<DailyDiary> getDiaryByDate(@PathVariable Long userId, @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return userService.getUserById(userId)
                .flatMap(user -> diaryService.getByDateAndUser(localDate, user))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🟢 Create a diary entry for a specific user
    @PostMapping("/de")
    public ResponseEntity<DailyDiary> createDiary(@PathVariable Long userId, @RequestBody DailyDiary diary) {
        return userService.getUserById(userId)
                .map(user -> {
                    diary.setUser(user);
                    DailyDiary saved = diaryService.createDiaryEntry(diary);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 🟢 Delete a diary entry by ID
    @DeleteMapping("/{diaryId}")
public ResponseEntity<Void> deleteDiary(@PathVariable Long userId, @PathVariable Long diaryId) {
    return userService.getUserById(userId)
            .flatMap(user -> diaryService.getById(diaryId)
                    .filter(entry -> entry.getUser().getId().equals(user.getId())))
            .<ResponseEntity<Void>>map(entry -> {
                diaryService.deleteEntry(diaryId);
                return ResponseEntity.noContent().build();
            })
            .orElse(ResponseEntity.notFound().build());
}

}
