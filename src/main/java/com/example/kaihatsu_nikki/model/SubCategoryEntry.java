package com.example.kaihatsu_nikki.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class SubCategoryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String details;
    private int progressValue;

    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    public SubCategoryEntry() {}
    public SubCategoryEntry(LocalDate date, String details, int progressValue, SubCategory subCategory) {
        this.date = date;
        this.details = details;
        this.progressValue = progressValue;
        this.subCategory = subCategory;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public int getProgressValue() { return progressValue; }
    public void setProgressValue(int progressValue) { this.progressValue = progressValue; }

    public SubCategory getSubCategory() { return subCategory; }
    public void setSubCategory(SubCategory subCategory) { this.subCategory = subCategory; }
}
