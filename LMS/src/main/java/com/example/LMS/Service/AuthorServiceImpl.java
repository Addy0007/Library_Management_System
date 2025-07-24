package com.example.LMS.Service;

import com.example.LMS.Repository.AuthorRepository;
import com.example.LMS.dto.AuthorDTO;
import com.example.LMS.entity.Author;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Convert Entity to DTO
    private AuthorDTO convertToDTO(Author author) {
        return new AuthorDTO(author.getAuthId(), author.getName());
    }

    // Convert DTO to Entity
    private Author convertToEntity(AuthorDTO dto) {
        Author author = new Author();
        author.setAuthId(dto.getAuthId()); // for update cases
        author.setName(dto.getName());
        return author;
    }

    @Override
    public AuthorDTO saveAuthor(AuthorDTO authorDTO) {
        Author author = convertToEntity(authorDTO);
        Author saved = authorRepository.save(author);
        return convertToDTO(saved);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(this::convertToDTO)
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
            return convertToDTO(updated);
        }
        return null; // or throw custom exception
    }
}
