package com.example.dao;

import com.example.entity.Book;
import java.util.List;

public interface BookDao {
    List<Book> getAllBooks();
    void addBook(Book book);
    boolean existsBookById(int id);
    void updateBookById(int id, Book book);
    void deleteBook(int id);
}
