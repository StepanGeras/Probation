package com.example.service;

import com.example.dto.UserDto;
import com.example.entity.User;
import com.example.entity.Book;
import com.example.exception.user.UserNotFoundException;
import com.example.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepo userRepo;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> findAll() {
        logger.info("Find all authors");
        List<User> users = userRepo.findAll();
        logger.info("Found {} authors", users.size());
        return users;
    }

    public void save(UserDto userDto) {
        User user = new User();
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
        user.setRoles(Set.of(User.Role.valueOf(userDto.getRole())));
        logger.info("Save author {}", user);
        userRepo.save(user);
        logger.info("Saved author {}", user);
    }

    public User findByUsername(String username) {
        logger.info("Finding author by username = {}", username);

        return userRepo.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("Author with username = {} not found", username);
                    return new UserNotFoundException("Author not found with username = " + username);
                });
    }

    public void update(User newUser) {
        logger.info("Updating author with id={}", newUser.getId());
        User existingUser = findById(newUser.getId());

        existingUser.setUsername(newUser.getUsername());

        userRepo.save(existingUser);
        logger.info("Successfully updated author with id={}", newUser.getId());
    }

    public User findById(Long id) {
        logger.info("Finding author by id={}", id);

        return userRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Author with id={} not found", id);
                    return new UserNotFoundException("Author not found with id=" + id);
                });
    }

    @Transactional
    public void deleteById(Long id) {
        logger.info("Deleting author by id={}", id);

        User user = findById(id);

        userRepo.delete(user);
        logger.info("Successfully deleted author with id={}", id);
    }

    public List<Book> findAllBooksByUserId(Long authorId) {
        logger.info("Find all books by author id {}", authorId);
        List<Book> bookList = userRepo.findAllBooksByUserId(authorId);
        logger.info("Found {} books", bookList.size());
        return bookList;
    }

    public void updateAuthorImage(Long id, String string) {
        User user = findById(id);
        user.setImageId(string);
        userRepo.save(user);
    }
}
