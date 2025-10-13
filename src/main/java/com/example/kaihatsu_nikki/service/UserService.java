package com.example.kaihatsu_nikki.service;

import com.example.kaihatsu_nikki.model.User;
import com.example.kaihatsu_nikki.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // ✅ Add this missing method
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
