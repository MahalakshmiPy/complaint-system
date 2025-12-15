package com.example.complainSystem.config;

import com.example.complainSystem.model.User;
import com.example.complainSystem.model.Role;
import com.example.complainSystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("Lakshmi").isEmpty()) {
            User itUser = new User();
            itUser.setUsername("Lakshmi");
            itUser.setPassword("12345");
            itUser.setRole(Role.IT);
            userRepository.save(itUser);
            System.out.println("IT user created: Lakshmi / 12345");
        }
    }
}
