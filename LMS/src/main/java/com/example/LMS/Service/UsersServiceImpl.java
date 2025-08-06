package com.example.LMS.Service;

import com.example.LMS.Repository.BookRepository;
import com.example.LMS.Repository.UsersRepository;
import com.example.LMS.dto.BookDTO;
import com.example.LMS.dto.CreateUserRequestDTO;
import com.example.LMS.dto.UsersDTO;
import com.example.LMS.dto.PatchDTO.UsersPatchDTO;
import com.example.LMS.entity.Book;
import com.example.LMS.entity.Role;
import com.example.LMS.entity.Users;
import com.example.LMS.mapper.BookMapper;
import com.example.LMS.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService, UserDetailsService {

    private final UsersRepository usersRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;

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
    public UsersDTO updateUserPartial(Long id, UsersPatchDTO patchDTO) {
        return usersRepository.findById(id).map(user -> {
            if (patchDTO.getName() != null) {
                user.setName(patchDTO.getName());
            }
            if (patchDTO.getEmail() != null) {
                user.setEmail(patchDTO.getEmail());
            }
            if (patchDTO.getPhone() != null) {
                user.setPhone(patchDTO.getPhone());
            }
            if (patchDTO.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(patchDTO.getPassword())); // ✅ encrypted
            }

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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));


        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

    @Override
    public UsersDTO createUser(CreateUserRequestDTO createUserRequest) {

        if (usersRepository.findByEmail(createUserRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        List<Book> borrowedBooks = createUserRequest.getBorrowedBookIds() != null
                ? bookRepository.findAllById(createUserRequest.getBorrowedBookIds())
                : List.of();


        Users user = new Users();
        user.setName(createUserRequest.getName());
        user.setEmail(createUserRequest.getEmail());
        user.setPhone(createUserRequest.getPhone());
        user.setRole(createUserRequest.getRole());
        user.setRegistrationDate(LocalDateTime.now());
        user.setBorrowedBooks(borrowedBooks);


        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        Users savedUser = usersRepository.save(user);
        return UsersMapper.toDTO(savedUser);
    }

}
