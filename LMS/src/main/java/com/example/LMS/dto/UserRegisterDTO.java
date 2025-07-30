package com.example.LMS.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {
    private String name;
    private String email;
    private String password;
    private Long phone;
}
