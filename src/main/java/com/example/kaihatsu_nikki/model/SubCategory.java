package com.example.kaihatsu_nikki.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "subcategories")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // 🔙 Many subcategories belong to one category
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference(value = "category-subcategory")
    private Category category;

    // 🟢 One subcategory -> many entries
    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "subcategory-entry")
    private List<SubCategoryEntry> entries = new ArrayList<>();

    public SubCategory() {}

    public SubCategory(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    // 🧾 Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public List<SubCategoryEntry> getEntries() { return entries; }
    public void setEntries(List<SubCategoryEntry> entries) { this.entries = entries; }
}
