package com.example.LMS.Repository;

import com.example.LMS.entity.BookAuth;
import com.example.LMS.entity.BookAuthId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAuthRepository extends JpaRepository<BookAuth, BookAuthId> {

    List<BookAuth> findByAuthor_AuthId(Long authId);
    List<BookAuth> findByBook_BookId(Long bookId);
    long countByAuthor_AuthId(Long authId);
    long countByBook_BookId(Long bookId);
    void deleteByBook_BookId(Long bookId);
    void deleteByAuthor_AuthId(Long authId);
}
