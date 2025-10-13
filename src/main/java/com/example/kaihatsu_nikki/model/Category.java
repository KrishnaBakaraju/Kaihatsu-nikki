package com.example.kaihatsu_nikki.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // 🔙 Many categories belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    // 🟢 One category -> many subcategories
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "category-subcategory")
    private List<SubCategory> subCategories = new ArrayList<>();

    public Category() {}

    public Category(String name, User user) {
        this.name = name;
        this.user = user;
    }

    // 🧾 Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<SubCategory> getSubCategories() { return subCategories; }
    public void setSubCategories(List<SubCategory> subCategories) { this.subCategories = subCategories; }
}
