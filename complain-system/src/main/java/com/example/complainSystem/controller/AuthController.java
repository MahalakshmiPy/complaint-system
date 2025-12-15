package com.example.complainSystem.controller;

import com.example.complainSystem.model.User;
import com.example.complainSystem.model.Role;
import com.example.complainSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // LOGIN
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User loginUser) {
        Optional<User> userOpt = userRepository.findByUsername(loginUser.getUsername());
        Map<String, Object> response = new HashMap<>();

        if (userOpt.isEmpty()) {
            response.put("error", "User not found");
            return response;
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(loginUser.getPassword())) {
            response.put("error", "Invalid password");
            return response;
        }

        response.put("username", user.getUsername());
        response.put("role", user.getRole().name());
        return response;
    }

    // REGISTER
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User newUser) {
        Map<String, Object> response = new HashMap<>();

        // Check if username already exists
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            response.put("error", "Username already exists");
            return response;
        }

        // Set default role to USER
        newUser.setRole(Role.USER);

        userRepository.save(newUser);

        response.put("message", "User registered successfully");
        response.put("username", newUser.getUsername());
        response.put("role", newUser.getRole().name());
        return response;
    }
}
