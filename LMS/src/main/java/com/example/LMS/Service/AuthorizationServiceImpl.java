package com.example.LMS.Service;

import com.example.LMS.Repository.UsersRepository;
import com.example.LMS.dto.LoginDTO;
import com.example.LMS.dto.LoginRequestDTO;
import com.example.LMS.dto.UserRegisterDTO;
import com.example.LMS.dto.UserProfileDTO;
import com.example.LMS.entity.Role;
import com.example.LMS.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UsersRepository usersRepository;

    @Autowired
    public AuthorizationServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserRegisterDTO register(UserRegisterDTO registrationDTO) {
        if (usersRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Users user = new Users();
        user.setName(registrationDTO.getName());
        user.setEmail(registrationDTO.getEmail());
        user.setPhone(registrationDTO.getPhone());
        user.setPassword(registrationDTO.getPassword()); // bcrypt to be added later
        user.setRole(Role.valueOf("STUDENT"));
        user.setRegistrationDate(LocalDateTime.now());

        Users savedUser = usersRepository.save(user);

        UserRegisterDTO result = new UserRegisterDTO();
        result.setName(savedUser.getName());
        result.setEmail(savedUser.getEmail());
        result.setPhone(savedUser.getPhone());
        result.setPassword(null); // do not return password

        return result;
    }

    @Override
    public LoginRequestDTO login(LoginDTO loginDTO) {
        Optional<Users> userOpt = usersRepository.findByEmail(loginDTO.getEmail());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found.");
        }

        Users user = userOpt.get();

        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("Incorrect password.");
        }

        LoginRequestDTO loginResponse = new LoginRequestDTO();
        loginResponse.setEmail(user.getEmail());
        loginResponse.setPassword("Login successful!"); // repurposed, ideally not for message

        return loginResponse;
    }

    @Override
    public boolean checkEmailExists(String email) {
        return usersRepository.existsByEmail(email);
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        Optional<Users> userOpt = usersRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            Users user = userOpt.get();

            if (!user.getPassword().equals(oldPassword)) {
                return false;
            }

            user.setPassword(newPassword);
            usersRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean sendResetPasswordEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    @Override
    public boolean resetPassword(String email, String newPassword, String token) {
        Optional<Users> userOpt = usersRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            user.setPassword(newPassword);
            usersRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public Role getUserRole(String email) {
        return usersRepository.findByEmail(email)
                .map(Users::getRole)
                .orElse(null); // Or throw a custom exception if user not found
    }


    @Override
    public boolean deleteUserByEmail(String email) {
        Optional<Users> userOpt = usersRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            usersRepository.delete(userOpt.get());
            return true;
        }
        return false;
    }

    @Override
    public UserRegisterDTO getProfileByEmail(String email) {
        Optional<Users> userOpt = usersRepository.findByEmail(email);
        if (userOpt.isEmpty()) return null;

        Users user = userOpt.get();

        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setPassword(null);

        return dto;
    }
}
