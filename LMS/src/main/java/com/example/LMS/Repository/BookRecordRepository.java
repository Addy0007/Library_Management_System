package com.example.LMS.Repository;

import com.example.LMS.entity.BookRecord;
import com.example.LMS.entity.BookRecordID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRecordRepository extends JpaRepository<BookRecord, BookRecordID> {

    List<BookRecord> findByUsers_UserId(Long userId);

    List<BookRecord> findByBook_BookId(Long bookId);

    void deleteByUsers_UserId(Long userId);
    void deleteByBook_BookId(Long bookId);
}
