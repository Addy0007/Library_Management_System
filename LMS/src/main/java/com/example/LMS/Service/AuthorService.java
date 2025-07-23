package com.example.LMS.Service;

import com.example.LMS.entity.Author;

import java.util.List;

public interface AuthorService {
    Author saveAuthor(Author author);
    List<Author> getAllAuthors();
    Author getAuthorById(Long id);
    Void deleteAuthorById(Long id);
    Author updateAuthor(Long id, Author updatedAuthor);
}
