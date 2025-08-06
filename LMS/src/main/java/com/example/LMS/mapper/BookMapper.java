package com.example.LMS.mapper;

import com.example.LMS.dto.BookDTO;
import com.example.LMS.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    public static BookDTO toDTO(Book book) {
        List<String> authorNames = book.getAuthors() != null
                ? book.getAuthors().stream().map(Author::getName).collect(Collectors.toList())
                : List.of();

        List<String> categoryNames = book.getCategories() != null
                ? book.getCategories().stream().map(Category::getName).collect(Collectors.toList())
                : List.of();

        List<String> borrowerEmails = book.getBorrowers() != null
                ? book.getBorrowers().stream().map(Users::getEmail).collect(Collectors.toList())
                : List.of();

        String publisherName = book.getPublisher() != null
                ? book.getPublisher().getName()
                : null;

        return new BookDTO(
                book.getBookId(),
                book.getTitle(),
                publisherName,
                book.getTotalBooks(),
                book.getAvailableBooks(),
                authorNames,
                categoryNames,
                borrowerEmails
        );
    }

    public static Book toEntity(BookDTO dto,
                                Publisher publisher,
                                List<Author> authors,
                                List<Category> categories,
                                List<Users> borrowers) {

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setPublisher(publisher);
        book.setTotalBooks(dto.getTotalBooks());
        book.setAvailableBooks(dto.getAvailableBooks());
        book.setAuthors(authors);
        book.setCategories(categories);
        book.setBorrowers(borrowers);

        return book;
    }
}
