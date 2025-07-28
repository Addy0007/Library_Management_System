package com.example.LMS.mapper;

import com.example.LMS.dto.AuthorDTO;
import com.example.LMS.entity.Author;

public class AuthorMapper {

    public static AuthorDTO toDTO(Author author) {
        return new AuthorDTO(author.getAuthId(), author.getName());
    }

    public static Author toEntity(AuthorDTO dto) {
        Author author = new Author();
        author.setAuthId(dto.getAuthId());
        author.setName(dto.getName());
        return author;
    }
}
