package com.example.LMS.dto;

import com.example.LMS.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    @Schema(hidden = true)
    private Long userId;
    private String name;
    private String email;
    private Long phone;

    private Role role;
    private LocalDateTime registrationDate;

    private List<Long> borrowedBookIds;
}
