package com.example.LMS.Service;

import com.example.LMS.entity.Book;
import java.util.List;

public interface BookService {

    Book saveBook(Book book);
    List<Book> getAllBooks();
    Book getBookById(Long id);
    void deleteBookById(Long id);
    Book updateBookById(Long id, Book updatedBook);
    Book updateBookTitle(Long id, String newTitle);
    Book updateAvailableBooks(Long id, Integer availableCount);
    Book updateTotalBooks(Long id, Integer totalCount);
    Book updatePublisherName(Long id, String newPublisherName);
    List<Book> getBooksByTitleContaining(String title);
    List<Book> getBooksByPublisherName(String publisherName);
}
