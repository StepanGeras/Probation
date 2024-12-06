package com.example.controller;

import com.example.entity.Book;
import com.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> findAll() {
        List<Book> findAllBook = bookService.readBooks();
        return ResponseEntity.ok(findAllBook);
    }

    @PostMapping("/create")
    public ResponseEntity<Book> create(@RequestBody Book book) {
        bookService.createBook(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/update")
    public ResponseEntity<Book> update(@RequestBody Book book, @RequestParam Long id) {
        bookService.updateBook(id, book);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Book> delete(@RequestParam Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

}
