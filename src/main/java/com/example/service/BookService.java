package com.example.service;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.example.model.Book;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private static final String CSV_FILE_PATH = "src/main/resources/books.csv";
    private final CsvMapper csvMapper = new CsvMapper();
    private final CsvSchema csvSchema = CsvSchema.builder()
            .addColumn("id", CsvSchema.ColumnType.NUMBER)
            .addColumn("title")
            .addColumn("author")
            .addColumn("description")
            .build()
            .withHeader();

    public List<Book> readBooks() throws IOException {
        File csvFile = new File(CSV_FILE_PATH);

        return csvMapper.readerFor(Book.class)
                .with(csvSchema)
                .<Book>readValues(csvFile)
                .readAll();
    }

    public void writeBooks(List<Book> books) throws IOException {
        csvMapper.writerFor(Book.class)
                .with(csvSchema)
                .writeValues(new File(CSV_FILE_PATH))
                .writeAll(books);
    }

    public void createBook(Book book) throws IOException {
        List<Book> books = readBooks();
        books.add(book);
        writeBooks(books);
    }

//    public boolean findBookById(int id) throws IOException {
//        List<Book> books = readBooks();
//        for (Book book : books) {
//            if (book.getId() == id) {
//                return true;
//            }
//        }
//        return false;
//    }

    public void updateBook(int id, Book updatedBook) throws IOException {
        List<Book> books = readBooks();
        for (Book book : books) {
            if (book.getId() == id) {
                book.setId(updatedBook.getId());
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setDescription(updatedBook.getDescription());
                break;
            }
        }
        writeBooks(books);
    }

    public void deleteBook(int id) throws IOException {
        List<Book> books = readBooks();
        books.removeIf(book -> book.getId() == id);
        writeBooks(books);
    }

}
