package com.example.LMS.Repository;

import com.example.LMS.entity.BookCat;
import com.example.LMS.entity.BookCatId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookCatRepository extends JpaRepository<BookCat,Long> {
    List<BookCat> findByBook_BookId(Long bookId);

    List<BookCat> findByCategory_CatId(Long catId);

    boolean existsById(BookCatId id);

    void deleteByBook_BookId(Long bookId);

    void deleteByCategory_CatId(Long catId);

}
