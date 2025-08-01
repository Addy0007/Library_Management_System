package com.example.LMS.Service;

import com.example.LMS.dto.AuthorDTO;
import com.example.LMS.dto.BookDTO;
import com.example.LMS.dto.PatchDTO.AuthorPatchDTO;
import com.example.LMS.dto.PatchDTO.UsersPatchDTO;
import com.example.LMS.dto.UsersDTO;

import java.util.List;

public interface AuthorService {
    AuthorDTO saveAuthor(AuthorDTO authorDTO);
    List<AuthorDTO> getAllAuthors();
    AuthorDTO getAuthorById(Long id);
    void deleteAuthorById(Long id);
    AuthorDTO updateAuthor(Long id, AuthorDTO updatedAuthorDTO);
    List<BookDTO> getBooksByAuthorId(Long authorId);
    List<BookDTO> getBooksByAuthorName(String name);

}
