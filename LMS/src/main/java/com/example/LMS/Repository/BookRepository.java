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
    List<Book> findByAuthors_AuthId(Long authId);        // NEW: optional helper
    List<Book> findByCategories_CatId(Long catId);

    List<Book> findByAuthors_NameContainingIgnoreCase(String namePart);
    List<Book> findByAuthors_NameIgnoreCase(String name);

    List<Book> findByPublisher_NameIgnoreCase(String name);
    List<Book> findByPublisher_NameContainingIgnoreCase(String namePart);

    List<Book> findByCategories_NameIgnoreCase(String name);
    List<Book> findByCategories_NameContainingIgnoreCase(String namePart);

    List<Book> findByBorrowers_UserId(Long userId);
    List<Book> findByBorrowers_NameIgnoreCase(String name);
    List<Book> findByBorrowers_NameContainingIgnoreCase(String namePart);


}
