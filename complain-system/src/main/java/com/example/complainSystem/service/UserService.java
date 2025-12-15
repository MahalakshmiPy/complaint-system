package com.example.complainSystem.service;

import com.example.complainSystem.model.User;
import com.example.complainSystem.model.Role;
import com.example.complainSystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register new user
    public User registerUser(String username, String password, Role role) {
        User user = new User(username, password, role);
        return userRepository.save(user);
    }

    // Find user by username (for login)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
