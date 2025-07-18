package com.example.LMS.Repository;

import com.example.LMS.entity.BookPub;
import com.example.LMS.entity.BookPubID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookPubRepository extends JpaRepository<BookPub, BookPubID> {

    List<BookPub> findByBook_BookId(Long bookId);

    List<BookPub> findByPublisher_PublisherId(Long publisherId);

    void deleteByBook_BookId(Long bookId);

    void deleteByPublisher_PublisherId(Long publisherId);
}
