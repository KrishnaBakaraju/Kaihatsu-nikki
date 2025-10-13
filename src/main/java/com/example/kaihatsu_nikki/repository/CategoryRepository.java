package com.example.kaihatsu_nikki.repository;

import com.example.kaihatsu_nikki.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
