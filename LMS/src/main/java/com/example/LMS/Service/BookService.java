package com.example.LMS.Service;

import com.example.LMS.dto.BookDTO;

import java.util.List;

public interface BookService {

    BookDTO saveBook(BookDTO bookDTO);
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    void deleteBookById(Long id);
    BookDTO updateBookById(Long id, BookDTO updatedBookDTO);
    BookDTO updateBookTitle(Long id, String newTitle);
    BookDTO updateAvailableBooks(Long id, Integer availableCount);
    BookDTO updateTotalBooks(Long id, Integer totalCount);
    BookDTO updatePublisherName(Long id, String newPublisherName);
    List<BookDTO> getBooksByTitleContaining(String title);
    List<BookDTO> getBooksByPublisherName(String publisherName);
}
