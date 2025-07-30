package com.example.LMS.Controller;

import com.example.LMS.Repository.UsersRepository;
import com.example.LMS.dto.UserRegisterDTO;
import com.example.LMS.entity.Users;
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
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterDTO dto) {
        if (usersRepository.existsByEmail(dto.getEmail())) {
            return ResponseEntity.badRequest().body("Email already registered.");
        }

        Users user = new Users();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // Will encode later
        user.setPhone(dto.getPhone());
        user.setRegistrationDate(LocalDateTime.now());

        usersRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }
}
