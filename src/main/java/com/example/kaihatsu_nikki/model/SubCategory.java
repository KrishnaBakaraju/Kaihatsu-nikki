package com.example.kaihatsu_nikki.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubCategoryEntry> entries = new ArrayList<>();

    public SubCategory() {}
    public SubCategory(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public List<SubCategoryEntry> getEntries() { return entries; }
    public void setEntries(List<SubCategoryEntry> entries) { this.entries = entries; }
}
