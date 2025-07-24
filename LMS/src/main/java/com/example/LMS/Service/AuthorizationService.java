package com.example.LMS.Service;

import com.example.LMS.dto.LoginDTO;
import com.example.LMS.dto.LoginResponseDTO;
import com.example.LMS.dto.UserRegistrationDTO;

public interface AuthorizationService {
    UserRegistrationDTO register(UserRegistrationDTO registrationDTO);
    LoginResponseDTO login(LoginDTO loginDTO);
    boolean checkEmailExists(String email);
    boolean changePassword(String email, String oldPassword, String newPassword);
    boolean sendResetPasswordEmail(String email);
    boolean resetPassword(String email, String newPassword, String token);
    String getUserRole(String email);
    boolean deleteUserByEmail(String email);
    UserRegistrationDTO getProfileByEmail(String email);
}
