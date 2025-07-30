package com.example.LMS.Controller;

import com.example.LMS.Service.BookService;
import com.example.LMS.dto.BookDTO;
import com.example.LMS.entity.Book;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO){
        BookDTO savedBook=bookService.saveBook(bookDTO);
        return ResponseEntity.ok(savedBook);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @GetMapping()
    public ResponseEntity<List<BookDTO>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
    return ResponseEntity.ok(bookService.updateBookById(id, bookDTO));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
    bookService.deleteBookById(id);
    return ResponseEntity.noContent().build();
    }

}
