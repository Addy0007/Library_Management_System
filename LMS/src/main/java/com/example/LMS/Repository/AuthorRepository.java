package com.example.LMS.Repository;

import com.example.LMS.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {


    List<Author> findByNameIgnoreCase(String name);
    List<Author> findByNameContainingIgnoreCase(String keyword);
    boolean existsByName(String name);
}