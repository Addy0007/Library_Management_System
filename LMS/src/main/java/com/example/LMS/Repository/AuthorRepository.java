package com.example.LMS.Repository;

import com.example.LMS.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByNameIgnoreCase(String name);

    List<Author> findByNameContainingIgnoreCase(String keyword);

    boolean existsByName(String name);

    Optional<Author> findByAuthId(Long authId);

    Author findByBooks_BookId(Long bookId);

    Author findByBooks_Title(String title);

    List<Author> findByBooks_TitleContainingIgnoreCase(String partialTitle);
}
