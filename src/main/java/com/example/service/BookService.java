package com.example.service;

import com.example.dao.BookDao;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.example.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookDao bookDao;

    @Autowired
    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public List<Book> readBooks() throws IOException {
        return bookDao.getAllBooks();
    }

    public void createBook(Book book) throws IOException {
        bookDao.addBook(book);
    }

    public void updateBook(int id, Book updatedBook) throws IOException {
        bookDao.updateBook(id, updatedBook);
    }

    public void deleteBook(int id) throws IOException {
        bookDao.deleteBook(id);
    }

}
