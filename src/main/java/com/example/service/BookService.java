package com.example.service;

import com.example.dao.BookDaoImpl;
import com.example.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BookService {

    private final BookDaoImpl bookDaoImpl;

    @Autowired
    public BookService(BookDaoImpl bookDaoImpl) {
        this.bookDaoImpl = bookDaoImpl;
    }

    public List<Book> readBooks() throws IOException {
        return bookDaoImpl.getAllBooks();
    }

    public void createBook(Book book) throws IOException {
        bookDaoImpl.addBook(book);
    }

    public void updateBook(int id, Book updatedBook) throws IOException {
        bookDaoImpl.updateBookById(id, updatedBook);
    }

    public void deleteBook(int id) throws IOException {
        bookDaoImpl.deleteBook(id);
    }

    public boolean existsBookById(int id) {
        return !bookDaoImpl.existsBookById(id);
    }

}
