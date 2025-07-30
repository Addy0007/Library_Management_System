package com.example.LMS.Controller;

import com.example.LMS.Service.UsersService;
import com.example.LMS.dto.BookDTO;
import com.example.LMS.dto.UsersDTO;
import com.example.LMS.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO usersDTO) {
        return ResponseEntity.ok(usersService.saveUsers(usersDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usersService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.getUserById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsersDTO> updateUser(@PathVariable Long id, @RequestBody UsersDTO updatedUsersDTO) {
        return ResponseEntity.ok(usersService.updateUserById(id, updatedUsersDTO));
    }

    @GetMapping("/by-email")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsersDTO> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(usersService.getUserByEmail(email));
    }

    @GetMapping("/by-name")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsersDTO>> getUsersByName(@RequestParam String name) {
        return ResponseEntity.ok(usersService.getUsersByName(name));
    }

    @GetMapping("/by-phone")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsersDTO> getUserByPhone(@RequestParam Long phone) {
        return ResponseEntity.ok(usersService.getUserByPhone(phone));
    }

    @GetMapping("/by-role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsersDTO>> getUsersByRole(@RequestParam Role role) {
        return ResponseEntity.ok(usersService.getUsersByRole(role));
    }

    @GetMapping("/{id}/borrowed-books")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<List<BookDTO>> getBorrowedBooksByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.getBorrowedBooksByUserId(id));
    }

    @GetMapping("/borrowed-books/by-name")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookDTO>> getBorrowedBooksByUserName(@RequestParam String name) {
        return ResponseEntity.ok(usersService.getBorrowedBooksByUserName(name));
    }
}


