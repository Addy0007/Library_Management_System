package com.example.LMS.mapper;

import com.example.LMS.dto.BookDTO;
import com.example.LMS.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    public static BookDTO toDTO(Book book) {
        List<Long> authorIds = book.getAuthors() != null
                ? book.getAuthors().stream().map(Author::getAuthId).collect(Collectors.toList())
                : null;

        List<Long> categoryIds = book.getCategories() != null
                ? book.getCategories().stream().map(Category::getCatId).collect(Collectors.toList())
                : null;

        List<Long> borrowerIds = book.getBorrowers() != null
                ? book.getBorrowers().stream().map(Users::getUserId).collect(Collectors.toList())
                : null;

        Long publisherId = book.getPublisher() != null ? book.getPublisher().getPublisherId() : null;

        return new BookDTO(
                book.getBookId(),
                book.getTitle(),
                book.getPublisher() != null ? book.getPublisher().getName() : null,
                book.getTotalBooks(),
                book.getAvailableBooks(),
                authorIds,
                categoryIds,
                publisherId,
                borrowerIds
        );
    }

    public static Book toEntity(BookDTO dto,
                                Publisher publisher,
                                List<Author> authors,
                                List<Category> categories,
                                List<Users> borrowers) {

        Book book = new Book();
        book.setBookId(dto.getBookId());
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
