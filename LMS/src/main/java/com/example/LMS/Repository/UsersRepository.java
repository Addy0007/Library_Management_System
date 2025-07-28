package com.example.LMS.Repository;

import com.example.LMS.entity.Role;
import com.example.LMS.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Users> findByPhone(Long phone);

    boolean existsByPhone(Long phone);
    Optional<Users> findByEmailAndPassword(String email, String password);

    List<Users> findByRole(Role role);
    List<Users> findByRegistrationDateAfter(LocalDateTime date);
    List<Users> findByNameContainingIgnoreCase(String name);
    List<Users> findByBorrowedBooks_BookId(Long bookId);
    List<Users> findByNameIgnoreCase(String name);
    List<Users> findByBorrowedBooks_TitleIgnoreCase(String title);
    List<Users> findByBorrowedBooks_TitleContainingIgnoreCase(String titlePart);
}
