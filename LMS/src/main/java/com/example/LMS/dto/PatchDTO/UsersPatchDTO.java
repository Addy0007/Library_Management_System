package com.example.LMS.dto.PatchDTO;

import com.example.LMS.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersPatchDTO {
    private String name;
    private String email;
    private Long phone;
    private String password;
    private Role role;
}
