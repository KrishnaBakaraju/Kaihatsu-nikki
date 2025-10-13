package com.example.kaihatsu_nikki.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "daily_diary")
public class DailyDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(columnDefinition = "TEXT")
    private String content;

    // 🔙 Many diary entries belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-diary")
    private User user;

    public DailyDiary() {}

    public DailyDiary(LocalDate date, String content, User user) {
        this.date = date;
        this.content = content;
        this.user = user;
    }

    // 🧾 Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
