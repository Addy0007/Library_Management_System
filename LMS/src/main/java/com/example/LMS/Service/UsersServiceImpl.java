package com.example.LMS.Service;

import com.example.LMS.Repository.BookRepository;
import com.example.LMS.Repository.UsersRepository;
import com.example.LMS.dto.BookDTO;
import com.example.LMS.dto.UsersDTO;
import com.example.LMS.entity.Book;
import com.example.LMS.entity.Role;
import com.example.LMS.entity.Users;
import com.example.LMS.mapper.BookMapper;
import com.example.LMS.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final BookRepository bookRepository;

    @Override
    public UsersDTO saveUsers(UsersDTO usersDTO) {
        List<Book> borrowedBooks = usersDTO.getBorrowedBookIds() != null
                ? bookRepository.findAllById(usersDTO.getBorrowedBookIds())
                : List.of();

        Users user = UsersMapper.toEntity(usersDTO, borrowedBooks);
        return UsersMapper.toDTO(usersRepository.save(user));
    }

    @Override
    public List<UsersDTO> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(UsersMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsersDTO getUserById(Long id) {
        return usersRepository.findById(id)
                .map(UsersMapper::toDTO)
                .orElse(null);
    }

    @Override
    public void deleteUserById(Long id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UsersDTO updateUserById(Long id, UsersDTO updatedUsersDTO) {
        return usersRepository.findById(id).map(user -> {
            user.setName(updatedUsersDTO.getName());
            user.setEmail(updatedUsersDTO.getEmail());
            user.setPhone(updatedUsersDTO.getPhone());

            List<Book> updatedBooks = updatedUsersDTO.getBorrowedBookIds() != null
                    ? bookRepository.findAllById(updatedUsersDTO.getBorrowedBookIds())
                    : List.of();

            user.setBorrowedBooks(updatedBooks);

            return UsersMapper.toDTO(usersRepository.save(user));
        }).orElse(null);
    }

    @Override
    public UsersDTO getUserByEmail(String email) {
        return usersRepository.findByEmail(email)
                .map(UsersMapper::toDTO)
                .orElse(null);
    }

    @Override
    public List<UsersDTO> getUsersByName(String name) {
        return usersRepository.findByNameContainingIgnoreCase(name).stream()
                .map(UsersMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsersDTO getUserByPhone(Long phone) {
        return usersRepository.findByPhone(phone)
                .map(UsersMapper::toDTO)
                .orElse(null);
    }

    @Override
    public UsersDTO updateUserName(Long id, String newName) {
        return usersRepository.findById(id).map(user -> {
            user.setName(newName);
            return UsersMapper.toDTO(usersRepository.save(user));
        }).orElse(null);
    }

    @Override
    public UsersDTO updateUserEmail(Long id, String newEmail) {
        return usersRepository.findById(id).map(user -> {
            user.setEmail(newEmail);
            return UsersMapper.toDTO(usersRepository.save(user));
        }).orElse(null);
    }

    @Override
    public UsersDTO updateUserPhone(Long id, Long newPhone) {
        return usersRepository.findById(id).map(user -> {
            user.setPhone(newPhone);
            return UsersMapper.toDTO(usersRepository.save(user));
        }).orElse(null);
    }

    @Override
    public UsersDTO updateUserPassword(Long id, String newPassword) {
        return usersRepository.findById(id).map(user -> {
            user.setPassword(newPassword); // In production, hash this!
            return UsersMapper.toDTO(usersRepository.save(user));
        }).orElse(null);
    }

    @Override
    public List<UsersDTO> getUsersByRole(Role role) {
        return usersRepository.findByRole(role).stream()
                .map(UsersMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBorrowedBooksByUserId(Long userId) {
        return usersRepository.findById(userId)
                .map(user -> user.getBorrowedBooks().stream()
                        .map(BookMapper::toDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    @Override
    public List<BookDTO> getBorrowedBooksByUserName(String name) {
        return usersRepository.findByNameIgnoreCase(name).stream()
                .flatMap(user -> user.getBorrowedBooks().stream())
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }
}
