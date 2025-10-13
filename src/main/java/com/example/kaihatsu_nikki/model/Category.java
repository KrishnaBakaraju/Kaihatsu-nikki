package com.example.kaihatsu_nikki.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // 👇 Key change: use JsonIgnoreProperties on the list side too
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("category")
    private List<SubCategory> subCategories = new ArrayList<>();

    public Category() {}
    public Category(String name) { this.name = name; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<SubCategory> getSubCategories() { return subCategories; }
    public void setSubCategories(List<SubCategory> subCategories) { this.subCategories = subCategories; }
}
