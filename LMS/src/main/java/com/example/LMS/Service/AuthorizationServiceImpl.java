package com.example.LMS.Service;

import com.example.LMS.Configurations.SecurityBeansConfig;
import com.example.LMS.Repository.UsersRepository;
import com.example.LMS.dto.LoginDTO;
import com.example.LMS.dto.LoginRequestDTO;
import com.example.LMS.dto.UserRegisterDTO;
import com.example.LMS.dto.UserProfileDTO;
import com.example.LMS.entity.Role;
import com.example.LMS.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        user.setRole(Role.STUDENT); // default role
        user.setRegistrationDate(LocalDateTime.now());

        Users savedUser = usersRepository.save(user);

        UserRegisterDTO result = new UserRegisterDTO();
        result.setName(savedUser.getName());
        result.setEmail(savedUser.getEmail());
        result.setPhone(savedUser.getPhone());
        result.setPassword(null); // never return password

        return result;
    }


    @Override
    public LoginRequestDTO login(LoginDTO dto) {
        Users user = usersRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        LoginRequestDTO res = new LoginRequestDTO();
        res.setEmail(user.getEmail());
        res.setPassword(null); // or replace with JWT token later
        return res;
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

            user.setPassword(passwordEncoder.encode(newPassword));
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
            user.setPassword(passwordEncoder.encode(newPassword)); // ✅ Encrypt
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
    public UserProfileDTO getProfileByEmail(String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileDTO(
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }

}
