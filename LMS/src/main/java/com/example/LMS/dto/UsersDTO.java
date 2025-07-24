package com.example.LMS.dto;

import com.example.LMS.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    private Long userId;
    private String name;
    private String email;
    private Long phone;

    private Role role;
    private LocalDateTime registrationDate;

    private List<Long> borrowedBookIds;
}
