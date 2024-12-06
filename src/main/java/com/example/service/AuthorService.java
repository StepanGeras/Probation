package com.example.service;

import com.example.entity.Author;
import com.example.entity.Book;
import com.example.exception.author.AuthorNotFoundException;
import com.example.repo.AuthorRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepo authorRepo;
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    @Autowired
    public AuthorService(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    public List<Author> findAll() {
        logger.info("Find all authors");
        List<Author> authors = authorRepo.findAll();
        logger.info("Found {} authors", authors.size());
        return authors;
    }

    public void save(Author author) {
        logger.info("Save author {}", author);
        authorRepo.save(author);
        logger.info("Saved author {}", author);
    }

    public void update(Author newAuthor) {
        logger.info("Updating author with id={}", newAuthor.getId());
        Author existingAuthor = findById(newAuthor.getId());

        existingAuthor.setFirstName(newAuthor.getFirstName());
        existingAuthor.setLastName(newAuthor.getLastName());

        authorRepo.save(existingAuthor);
        logger.info("Successfully updated author with id={}", newAuthor.getId());
    }

    public Author findById(Long id) {
        logger.info("Finding author by id={}", id);

        return authorRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Author with id={} not found", id);
                    return new AuthorNotFoundException("Author not found with id=" + id);
                });
    }

    @Transactional
    public void deleteById(Long id) {
        logger.info("Deleting author by id={}", id);

        Author author = findById(id);

        authorRepo.delete(author);
        logger.info("Successfully deleted author with id={}", id);
    }

    public List<Book> findAllBooksByAuthorId(Long authorId) {
        logger.info("Find all books by author id {}", authorId);
        List<Book> bookList = authorRepo.findAllBooksByAuthorId(authorId);
        logger.info("Found {} books", bookList.size());
        return bookList;
    }

    public void updateAuthorImage(Long id, String string) {
        Author author = findById(id);
        author.setImageId(string);
        authorRepo.save(author);
    }
}
