package com.example.LMS.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("This is a public endpoint - no authentication required");
    }

    @GetMapping("/authenticated")
    public ResponseEntity<Map<String, Object>> authenticatedEndpoint() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", auth != null && auth.isAuthenticated());
        response.put("principal", auth != null ? auth.getName() : "null");
        response.put("authorities", auth != null ? auth.getAuthorities().toString() : "null");
        response.put("details", auth != null ? auth.getDetails() : "null");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin-only")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> adminOnlyEndpoint() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Success! You have ADMIN access");
        response.put("principal", auth.getName());
        response.put("authorities", auth.getAuthorities().toString());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/student-only")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Map<String, Object>> studentOnlyEndpoint() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Success! You have STUDENT access");
        response.put("principal", auth.getName());
        response.put("authorities", auth.getAuthorities().toString());

        return ResponseEntity.ok(response);
    }
}