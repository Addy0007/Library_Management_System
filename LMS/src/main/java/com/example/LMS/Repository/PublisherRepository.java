package com.example.LMS.Repository;


import com.example.LMS.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher,Long> {
    List<Publisher> findByNameIgnoreCase(String name);
    List<Publisher> findByNameContainingIgnoreCase(String keyword);
    boolean existsByName(String name);

    Publisher findByPublishedBooks_BookId(Long bookId);
    Publisher findByPublishedBooks_Title(String title);
    List<Publisher> findByPublishedBooks_TitleContainingIgnoreCase(String partialTitle);

}
