package com.example.LMS.Controller;

import com.example.LMS.Repository.UsersRepository;
import com.example.LMS.dto.UserRegisterDTO;
import com.example.LMS.entity.Role;
import com.example.LMS.entity.Users;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class RegisterController {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterDTO dto) {
        if (usersRepository.existsByEmail(dto.getEmail())) {
            return ResponseEntity.badRequest().body("Email already registered.");
        }

        Users user = new Users();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // ✅ Corrected
        user.setPhone(dto.getPhone());
        user.setRegistrationDate(LocalDateTime.now());
        user.setRole(Role.STUDENT);
        usersRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }
}
