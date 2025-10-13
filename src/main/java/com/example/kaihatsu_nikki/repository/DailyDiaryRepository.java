package com.example.kaihatsu_nikki.repository;

import com.example.kaihatsu_nikki.model.DailyDiary;
import com.example.kaihatsu_nikki.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyDiaryRepository extends JpaRepository<DailyDiary, Long> {
    Optional<DailyDiary> findByDateAndUser(LocalDate date, User user);
}
