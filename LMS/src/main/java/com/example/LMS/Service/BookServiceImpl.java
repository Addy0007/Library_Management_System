package com.example.LMS.Service;

import com.example.LMS.Repository.*;
import com.example.LMS.dto.BookDTO;
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
        Publisher publisher = bookDTO.getPublisherId() != null
                ? publisherRepository.findById(bookDTO.getPublisherId()).orElse(null)
                : null;

        List<Author> authors = bookDTO.getAuthorIds() != null
                ? authorRepository.findAllById(bookDTO.getAuthorIds())
                : List.of();

        List<Category> categories = bookDTO.getCategoryIds() != null
                ? categoryRepository.findAllById(bookDTO.getCategoryIds())
                : List.of();

        List<Users> borrowers = bookDTO.getBorrowerIds() != null
                ? usersRepository.findAllById(bookDTO.getBorrowerIds())
                : List.of();

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
    public BookDTO updateBookById(Long id, BookDTO updatedBookDTO) {
        Optional<Book> existingBookOpt = bookRepository.findById(id);
        if (existingBookOpt.isEmpty()) return null;

        Publisher publisher = updatedBookDTO.getPublisherId() != null
                ? publisherRepository.findById(updatedBookDTO.getPublisherId()).orElse(null)
                : null;

        List<Author> authors = updatedBookDTO.getAuthorIds() != null
                ? authorRepository.findAllById(updatedBookDTO.getAuthorIds())
                : List.of();

        List<Category> categories = updatedBookDTO.getCategoryIds() != null
                ? categoryRepository.findAllById(updatedBookDTO.getCategoryIds())
                : List.of();

        List<Users> borrowers = updatedBookDTO.getBorrowerIds() != null
                ? usersRepository.findAllById(updatedBookDTO.getBorrowerIds())
                : List.of();

        Book updatedBook = BookMapper.toEntity(updatedBookDTO, publisher, authors, categories, borrowers);
        updatedBook.setBookId(id);
        return BookMapper.toDTO(bookRepository.save(updatedBook));
    }

    @Override
    public BookDTO updateBookTitle(Long id, String newTitle) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isEmpty()) return null;

        Book book = bookOpt.get();
        book.setTitle(newTitle);
        return BookMapper.toDTO(bookRepository.save(book));
    }

    @Override
    public BookDTO updateAvailableBooks(Long id, Integer availableCount) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isEmpty()) return null;

        Book book = bookOpt.get();
        book.setAvailableBooks(availableCount);
        return BookMapper.toDTO(bookRepository.save(book));
    }

    @Override
    public BookDTO updateTotalBooks(Long id, Integer totalCount) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isEmpty()) return null;

        Book book = bookOpt.get();
        book.setTotalBooks(totalCount);
        return BookMapper.toDTO(bookRepository.save(book));
    }

    @Override
    public BookDTO updatePublisherName(Long id, String newPublisherName) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isEmpty()) return null;

        Book book = bookOpt.get();
        Publisher newPublisher = publisherRepository.findByNameIgnoreCase(newPublisherName)
                .stream().findFirst().orElse(null);

        if (newPublisher == null) return null;

        book.setPublisher(newPublisher);
        return BookMapper.toDTO(bookRepository.save(book));
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
}
