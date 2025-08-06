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


        Publisher publisher = null;
        if (bookDTO.getPublisherName() != null) {
            publisher = publisherRepository.findByNameIgnoreCase(bookDTO.getPublisherName())
                    .orElseGet(() -> {
                        Publisher newPub = new Publisher();
                        newPub.setName(bookDTO.getPublisherName());
                        return publisherRepository.save(newPub); // 🆕 create if not found
                    });
        }

        List<Author> authors = Optional.ofNullable(bookDTO.getAuthorNames())
                .orElse(List.of())
                .stream()
                .map(name -> authorRepository.findByNameIgnoreCase(name)
                        .orElseGet(() -> {
                            Author newAuthor = new Author();
                            newAuthor.setName(name);
                            return authorRepository.save(newAuthor); // 🆕 create
                        }))
                .collect(Collectors.toList());


        List<Category> categories = Optional.ofNullable(bookDTO.getCategoryNames())
                .orElse(List.of())
                .stream()
                .map(name -> categoryRepository.findByNameIgnoreCase(name)
                        .orElseGet(() -> {
                            Category newCat = new Category();
                            newCat.setName(name);
                            return categoryRepository.save(newCat);
                        }))
                .collect(Collectors.toList());


        List<Users> borrowers = Optional.ofNullable(bookDTO.getBorrowerEmails())
                .orElse(List.of())
                .stream()
                .map(email -> usersRepository.findByEmailIgnoreCase(email)
                        .orElseThrow(() -> new RuntimeException("Borrower not found: " + email)))
                .collect(Collectors.toList());


        Book book = BookMapper.toEntity(bookDTO, publisher, authors, categories, borrowers);
        Book savedBook = bookRepository.save(book);


        for (Users user : borrowers) {
            user.getBorrowedBooks().add(savedBook);
            usersRepository.save(user);
        }

        return BookMapper.toDTO(savedBook);
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
            book.setTotalBooks(updatedBookDTO.getTotalBooks());
            book.setAvailableBooks(updatedBookDTO.getAvailableBooks());
            return BookMapper.toDTO(bookRepository.save(book));
        }
        return null;
    }
}
