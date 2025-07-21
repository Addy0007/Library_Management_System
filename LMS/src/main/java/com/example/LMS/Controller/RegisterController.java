package com.example.LMS.Controller;

import com.example.LMS.entity.Users;
import com.example.LMS.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class RegisterController {

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users user) {
        if (usersRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already registered.");
        }

        user.setRegistrationDate(LocalDateTime.now());
        usersRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }
}
