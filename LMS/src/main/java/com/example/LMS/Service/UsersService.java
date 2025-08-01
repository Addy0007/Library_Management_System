package com.example.LMS.Service;

import com.example.LMS.dto.BookDTO;
import com.example.LMS.dto.UsersDTO;
import com.example.LMS.dto.PatchDTO.UsersPatchDTO;
import com.example.LMS.entity.Role;

import java.util.List;

public interface UsersService {

    UsersDTO saveUsers(UsersDTO usersDTO);

    List<UsersDTO> getAllUsers();

    UsersDTO getUserById(Long id);

    void deleteUserById(Long id);

    UsersDTO updateUserById(Long id, UsersDTO updatedUsersDTO);

    UsersDTO updateUserPartial(Long id, UsersPatchDTO patchDTO);

    UsersDTO getUserByEmail(String email);

    List<UsersDTO> getUsersByName(String name);

    UsersDTO getUserByPhone(Long phone);

    List<UsersDTO> getUsersByRole(Role role);

    List<BookDTO> getBorrowedBooksByUserId(Long userId);

    List<BookDTO> getBorrowedBooksByUserName(String name);
}
