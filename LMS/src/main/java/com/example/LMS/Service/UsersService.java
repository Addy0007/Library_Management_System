package com.example.LMS.Service;

import com.example.LMS.entity.Users;

import java.util.List;

public interface UsersService {
    Users saveUsers(Users users);
    List<Users> getAllUsers();
    Users getUsersById(Long id);
    void deleteUsersById(Long id);
    Users updateUsersById(Long id, Users updatedUsers);
    Users getUsersByEmail(String email);
    Users getUsersByName(String name);
    Users getUsersByPhone(Long phone);
    Users updateUserName(Long id, String newName);
    Users updateUserEmail(Long id, String newEmail);
    Users updateUserPhone(Long id, Long newPhone);
    Users updateUserPassword(Long id, String newPassword);
}
