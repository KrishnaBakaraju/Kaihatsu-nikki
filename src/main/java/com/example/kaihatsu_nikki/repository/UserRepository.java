package com.example.kaihatsu_nikki.repository;

import com.example.kaihatsu_nikki.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
