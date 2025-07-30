package com.example.LMS.Service;

import com.example.LMS.Repository.*;
import com.example.LMS.dto.BookDTO;
import com.example.LMS.dto.PatchDTO.BookPatchDTO;
import com.example.LMS.entity.*;
import com.example.LMS.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final UsersRepository usersRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           CategoryRepository categoryRepository,
                           PublisherRepository publisherRepository,
                           UsersRepository usersRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public BookDTO saveBook(BookDTO bookDTO) {
        Publisher publisher = Optional.ofNullable(bookDTO.getPublisherId())
                .flatMap(publisherRepository::findById)
                .orElse(null);

        List<Author> authors = Optional.ofNullable(bookDTO.getAuthorIds())
                .map(authorRepository::findAllById)
                .orElse(List.of());

        List<Category> categories = Optional.ofNullable(bookDTO.getCategoryIds())
                .map(categoryRepository::findAllById)
                .orElse(List.of());

        List<Users> borrowers = Optional.ofNullable(bookDTO.getBorrowerIds())
                .map(usersRepository::findAllById)
                .orElse(List.of());

        Book book = BookMapper.toEntity(bookDTO, publisher, authors, categories, borrowers);
        return BookMapper.toDTO(bookRepository.save(book));
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id) {
        return bookRepository.findById(id)
                .map(BookMapper::toDTO)
                .orElse(null);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDTO> getBooksByTitleContaining(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksByPublisherName(String publisherName) {
        return publisherRepository.findByNameIgnoreCase(publisherName).stream()
                .flatMap(pub -> pub.getPublishedBooks().stream())
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO updateBookPartial(Long bookId, BookPatchDTO patchDTO) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (patchDTO.getTitle() != null) book.setTitle(patchDTO.getTitle());
        if (patchDTO.getPublisherName() != null) book.setPublisherName(patchDTO.getPublisherName());
        if (patchDTO.getTotalBooks() != null) book.setTotalBooks(patchDTO.getTotalBooks());
        if (patchDTO.getAvailableBooks() != null) book.setAvailableBooks(patchDTO.getAvailableBooks());

        Book updated = bookRepository.save(book);
        return BookMapper.toDTO(updated);
    }

    @Override
    public BookDTO updateBookById(Long id, BookDTO updatedBookDTO) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(updatedBookDTO.getTitle());
            book.setPublisherName(updatedBookDTO.getPublisherName());
            book.setTotalBooks(updatedBookDTO.getTotalBooks());
            book.setAvailableBooks(updatedBookDTO.getAvailableBooks());
            return BookMapper.toDTO(bookRepository.save(book));
        }
        return null;
    }
}
