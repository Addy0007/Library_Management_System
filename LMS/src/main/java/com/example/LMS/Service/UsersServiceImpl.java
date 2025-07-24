package com.example.LMS.Service;

import com.example.LMS.dto.UsersDTO;
import com.example.LMS.entity.Role;

import java.util.List;

public class UsersServiceImpl implements UsersService{
    @Override
    public UsersDTO saveUsers(UsersDTO usersDTO) {
        return null;
    }

    @Override
    public List<UsersDTO> getAllUsers() {
        return List.of();
    }

    @Override
    public UsersDTO getUserById(Long id) {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public UsersDTO updateUserById(Long id, UsersDTO updatedUsersDTO) {
        return null;
    }

    @Override
    public UsersDTO getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<UsersDTO> getUsersByName(String name) {
        return List.of();
    }

    @Override
    public UsersDTO getUserByPhone(Long phone) {
        return null;
    }

    @Override
    public UsersDTO updateUserName(Long id, String newName) {
        return null;
    }

    @Override
    public UsersDTO updateUserEmail(Long id, String newEmail) {
        return null;
    }

    @Override
    public UsersDTO updateUserPhone(Long id, Long newPhone) {
        return null;
    }

    @Override
    public UsersDTO updateUserPassword(Long id, String newPassword) {
        return null;
    }

    @Override
    public List<UsersDTO> getUsersByRole(Role role) {
        return List.of();
    }
}
