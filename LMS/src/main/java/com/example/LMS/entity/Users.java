package com.example.LMS.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String Name;
    private String Email;
    private String Phone;
    private String Password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime Registration_Date;
}
