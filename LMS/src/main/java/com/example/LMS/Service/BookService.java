package com.example.LMS.Service;

import com.example.LMS.dto.BookDTO;
import com.example.LMS.dto.PatchDTO.BookPatchDTO;

import java.util.List;

public interface BookService {

    BookDTO saveBook(BookDTO bookDTO);
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    void deleteBookById(Long id);
    BookDTO updateBookById(Long id, BookDTO updatedBookDTO);
    List<BookDTO> getBooksByTitleContaining(String title);
    List<BookDTO> getBooksByPublisherName(String publisherName);
    BookDTO updateBookPartial(Long bookId, BookPatchDTO patchDTO);
}
