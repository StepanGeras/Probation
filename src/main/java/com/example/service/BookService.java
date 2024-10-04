package com.example.service;

import com.example.entity.Author;
import com.example.entity.Book;
import com.example.exception.book.BookNotFoundException;
import com.example.repo.BookRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepo bookRepo;
    private final AuthorService authorService;
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    public BookService(BookRepo bookRepo, AuthorService authorService) {
        this.bookRepo = bookRepo;
        this.authorService = authorService;
    }

    public List<Book> readBooks() {
        logger.info("Reading all books");
        List<Book> books = bookRepo.findAll();
        logger.info("Found {} books", bookRepo.count());
        return books;
    }

    public void createBook(Book book, Long id) {
        logger.info("Creating a book");
        Author author = authorService.findById(id);
        book.setAuthor(author);
        bookRepo.save(book);
        logger.info("Book created");
    }

    public void updateBook(long id, Book updatedBook) {
        logger.info("Updating a book");
        Book book = findBookById(id);
        book.setAuthor(updatedBook.getAuthor());
        book.setTitle(updatedBook.getTitle());
        book.setDescription(updatedBook.getDescription());
        bookRepo.save(book);
        logger.info("Book updated");

    }

    public void deleteBook(long id) {
        logger.info("Deleting a book");
        bookRepo.deleteById(id);
        logger.info("Book deleted");
    }

    public Book findBookById(long id) {
        logger.info("Finding a book by id: {}", id);
        Optional<Book> book = bookRepo.findById(id);
        if (book.isPresent()) {
            logger.info("Book found");
            return book.get();
        }
        throw new BookNotFoundException("Book not found");

    }

    public void updateBookImage(Long id, String imageId) {
        Book book = findBookById(id);
        book.setImageId(imageId);
        bookRepo.save(book);
    }

}
