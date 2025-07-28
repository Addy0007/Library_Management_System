package com.example.LMS.Repository;

import com.example.LMS.entity.Book;
import com.example.LMS.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCatId(Long catId);
    boolean existsByName(String name);
    Category findByName(String name);
    List<Category> findByNameContainingIgnoreCase(String keyword);

    Category findByBooks_BookId(Long bookId);
    Category findByBooks_Title(String title);
    List<Category> findByBooks_TitleContainingIgnoreCase(String partialTitle);
    Category findByNameIgnoreCase(String name);

}
