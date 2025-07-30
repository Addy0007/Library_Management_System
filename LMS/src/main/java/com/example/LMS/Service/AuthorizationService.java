package com.example.LMS.Service;

import com.example.LMS.dto.LoginDTO;
import com.example.LMS.dto.LoginRequestDTO;
import com.example.LMS.dto.UserProfileDTO;
import com.example.LMS.dto.UserRegisterDTO;
import com.example.LMS.entity.Role;


public interface AuthorizationService {
    UserRegisterDTO register(UserRegisterDTO registrationDTO);
    LoginRequestDTO login(LoginDTO loginDTO);
    boolean checkEmailExists(String email);
    boolean changePassword(String email, String oldPassword, String newPassword);
    boolean sendResetPasswordEmail(String email);
    boolean resetPassword(String email, String newPassword, String token);
    Role getUserRole(String email);
    boolean deleteUserByEmail(String email);
    UserProfileDTO getProfileByEmail(String email);
}
