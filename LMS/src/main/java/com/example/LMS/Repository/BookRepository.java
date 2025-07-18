package com.example.LMS.Repository;

import com.example.LMS.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByPublisherNameIgnoreCase(String publisherName);
    List<Book> findByAvailableBooksGreaterThan(int minAvailable);
}
