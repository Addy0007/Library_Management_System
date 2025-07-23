package com.example.LMS.Controller;

import com.example.LMS.Repository.UsersRepository;
import com.example.LMS.entity.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//DTO in this controller

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UsersRepository usersRepository;

    @Getter
    @Setter
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Optional<Users> existingUserOpt = usersRepository.findByEmail(loginRequest.getEmail());

        if (existingUserOpt.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("No user found with this email. Not a user? Visit: /api/auth/register");
        }

        Users existingUser = existingUserOpt.get();

        if (!existingUser.getPassword().equals(loginRequest.getPassword())) {
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
