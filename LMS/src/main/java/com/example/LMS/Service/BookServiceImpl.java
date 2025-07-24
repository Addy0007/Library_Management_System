package com.example.LMS.Service;

import com.example.LMS.dto.BookDTO;

import java.util.List;

public class BookServiceImpl implements  BookService{
    @Override
    public BookDTO saveBook(BookDTO bookDTO) {
        return null;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return List.of();
    }

    @Override
    public BookDTO getBookById(Long id) {
        return null;
    }

    @Override
    public void deleteBookById(Long id) {

    }

    @Override
    public BookDTO updateBookById(Long id, BookDTO updatedBookDTO) {
        return null;
    }

    @Override
    public BookDTO updateBookTitle(Long id, String newTitle) {
        return null;
    }

    @Override
    public BookDTO updateAvailableBooks(Long id, Integer availableCount) {
        return null;
    }

    @Override
    public BookDTO updateTotalBooks(Long id, Integer totalCount) {
        return null;
    }

    @Override
    public BookDTO updatePublisherName(Long id, String newPublisherName) {
        return null;
    }

    @Override
    public List<BookDTO> getBooksByTitleContaining(String title) {
        return List.of();
    }

    @Override
    public List<BookDTO> getBooksByPublisherName(String publisherName) {
        return List.of();
    }
}
