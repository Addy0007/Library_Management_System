package com.example.LMS.mapper;

import com.example.LMS.dto.UsersDTO;
import com.example.LMS.entity.Book;
import com.example.LMS.entity.Users;

import java.util.List;
import java.util.stream.Collectors;

public class UsersMapper {

    public static UsersDTO toDTO(Users user) {
        List<Long> bookIds = user.getBorrowedBooks() != null
                ? user.getBorrowedBooks().stream().map(Book::getBookId).collect(Collectors.toList())
                : List.of();

        return new UsersDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getRegistrationDate(),
                bookIds
        );
    }

    public static Users toEntity(UsersDTO dto, List<Book> borrowedBooks) {
        Users user = new Users();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setRegistrationDate(dto.getRegistrationDate());
        user.setBorrowedBooks(borrowedBooks);
        return user;
    }
}
