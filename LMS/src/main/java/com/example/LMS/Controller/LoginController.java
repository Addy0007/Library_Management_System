package com.example.LMS.Controller;

import com.example.LMS.Configurations.JwtService;
import com.example.LMS.Repository.UsersRepository;
import com.example.LMS.dto.JwtAuthResponse;
import com.example.LMS.dto.LoginRequestDTO;
import com.example.LMS.dto.LoginResponseDTO;
import com.example.LMS.entity.Role;
import com.example.LMS.entity.Users;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class LoginController {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            log.info("Login attempt for email: {}", loginRequest.getEmail());

            Optional<Users> userOpt = usersRepository.findByEmail(loginRequest.getEmail());

            if (userOpt.isEmpty()) {
                log.warn("No user found with email: {}", loginRequest.getEmail());
                return ResponseEntity
                        .badRequest()
                        .body(new JwtAuthResponse("No user found with this email."));
            }

            Users user = userOpt.get();
            log.info("User found: {}, Role: {}", user.getEmail(), user.getRole());

            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                log.warn("Incorrect password for user: {}", loginRequest.getEmail());
                return ResponseEntity
                        .badRequest()
                        .body(new JwtAuthResponse("Incorrect password."));
            }


            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
            );

            String jwtToken = jwtService.generateToken(userDetails);
            log.info("JWT token generated successfully for user: {}", user.getEmail());


            LoginResponseDTO response = new LoginResponseDTO(
                    jwtToken,
                    user.getEmail(),
                    user.getRole().name()
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Login error: {}", e.getMessage(), e);
            return ResponseEntity
                    .internalServerError()
                    .body(new JwtAuthResponse("Login failed: " + e.getMessage()));
        }
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
    @GetMapping("/debug-token")
    public ResponseEntity<Map<String, Object>> debugToken(@RequestParam String token) {
        try {
            String email = jwtService.extractEmail(token);
            Role role = jwtService.extractRole(token);

            Map<String, Object> response = new HashMap<>();
            response.put("email", email);
            response.put("role", role);
            response.put("roleWithPrefix", "ROLE_" + role.name());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}