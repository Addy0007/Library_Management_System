package com.example.LMS.Service;

import com.example.LMS.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    AuthorDTO saveAuthor(AuthorDTO authorDTO);
    List<AuthorDTO> getAllAuthors();
    AuthorDTO getAuthorById(Long id);
    void deleteAuthorById(Long id);
    AuthorDTO updateAuthor(Long id, AuthorDTO updatedAuthorDTO);
}
