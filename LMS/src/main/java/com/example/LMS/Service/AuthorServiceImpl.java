package com.example.LMS.Service;

import com.example.LMS.Repository.AuthorRepository;
import com.example.LMS.Repository.BookRepository;
import com.example.LMS.dto.AuthorDTO;
import com.example.LMS.dto.BookDTO;
import com.example.LMS.entity.Author;
import com.example.LMS.mapper.AuthorMapper;
import com.example.LMS.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public AuthorDTO saveAuthor(AuthorDTO authorDTO) {
        Author author = AuthorMapper.toEntity(authorDTO);
        Author saved = authorRepository.save(author);
        return AuthorMapper.toDTO(saved);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(AuthorMapper::toDTO)
                .orElse(null); // or throw exception
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO updatedDTO) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            Author existing = optionalAuthor.get();
            existing.setName(updatedDTO.getName());
            Author updated = authorRepository.save(existing);
            return AuthorMapper.toDTO(updated);
        }
        return null; // or throw custom exception
    }

    @Override
    public List<BookDTO> getBooksByAuthorId(Long authorId) {
        return authorRepository.findById(authorId)
                .map(author -> author.getBooks().stream()
                        .map(BookMapper::toDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public List<BookDTO> getBooksByAuthorName(String name) {
        List<Author> authors = authorRepository.findByNameIgnoreCase(name);

        return authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }
}
