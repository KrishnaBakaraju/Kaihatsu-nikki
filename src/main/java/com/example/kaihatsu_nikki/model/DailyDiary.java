package com.example.kaihatsu_nikki.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DailyDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(length = 2000)
    private String content;

    public DailyDiary() {}
    public DailyDiary(LocalDate date, String content) {
        this.date = date;
        this.content = content;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
