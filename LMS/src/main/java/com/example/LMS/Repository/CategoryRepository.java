package com.example.LMS.Repository;

import com.example.LMS.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("SELECT c.name FROM Category c WHERE c.name = :name")
    String findNameIfExists(@Param("name") String name);

    boolean existsByName(String name);

}
