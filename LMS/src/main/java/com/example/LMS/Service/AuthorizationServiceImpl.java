package com.example.LMS.Service;

import com.example.LMS.dto.LoginDTO;
import com.example.LMS.dto.LoginResponseDTO;
import com.example.LMS.dto.UserRegistrationDTO;

public class AuthorizationServiceImpl implements AuthorizationService{

    @Override
    public UserRegistrationDTO register(UserRegistrationDTO registrationDTO) {
        return null;
    }

    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        return null;
    }

    @Override
    public boolean checkEmailExists(String email) {
        return false;
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        return false;
    }

    @Override
    public boolean sendResetPasswordEmail(String email) {
        return false;
    }

    @Override
    public boolean resetPassword(String email, String newPassword, String token) {
        return false;
    }

    @Override
    public String getUserRole(String email) {
        return "";
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        return false;
    }

    @Override
    public UserRegistrationDTO getProfileByEmail(String email) {
        return null;
    }
}
