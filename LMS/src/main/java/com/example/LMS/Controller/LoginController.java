package com.example.LMS.Controller;

import com.example.LMS.Repository.UsersRepository;
import com.example.LMS.dto.LoginRequestDTO;
import com.example.LMS.entity.Users;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class LoginController {


    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
        Optional<Users> userOpt = usersRepository.findByEmail(loginRequest.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("No user found with this email. Not a user? Visit: /api/auth/register");
        }

        Users user = userOpt.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body("Incorrect password. Forgot password? Visit: /api/auth/forgot-password");
        }

        return ResponseEntity.ok("Login successful!");
    }

    @GetMapping("/register")
    public ResponseEntity<String> goToRegisterPage() {
        return ResponseEntity.ok("Redirect to registration page or registration API.");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        Optional<Users> user = usersRepository.findByEmail(email);
        if (user.isPresent()) {
            return ResponseEntity.ok("Password reset link sent to your email.");
        } else {
            return ResponseEntity.badRequest().body("Email not registered.");
        }
    }
}
