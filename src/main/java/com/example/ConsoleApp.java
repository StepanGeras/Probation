package com.example;

import com.example.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.service.BookService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleApp {

    private BookService bookService;

    @Autowired
    public ConsoleApp(BookService bookService) {
        this.bookService = bookService;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. List books");
            System.out.println("2. Create a new book");
            System.out.println("3. Edit a book");
            System.out.println("4. Delete book");
            System.out.println("5. Log out");

            int option = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (option) {
                    case 1:
                        List<Book> books = bookService.readBooks();
                        books.forEach(System.out::println);
                        break;
                    case 2:
                        System.out.println("Enter book ID:");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        if (bookService.findBookById(id)) {
                            System.out.printf("id - %d exists %n", id);
                            break;
                        }
                        System.out.println("Enter book title:");
                        String title = scanner.nextLine();
                        System.out.println("Enter the author of the book:");
                        String author = scanner.nextLine();
                        System.out.println("Enter a short description of the book:");
                        String description = scanner.nextLine();

                        bookService.createBook(new Book(id, title, author, description));
                        System.out.println("Book added.");
                        break;
                    case 3:
                        System.out.println("Enter the book ID to edit:");
                        int editId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter a new book title:");
                        String newTitle = scanner.nextLine();
                        System.out.println("Enter the new author of the book:");
                        String newAuthor = scanner.nextLine();
                        System.out.println("Enter a new short description of the book:");
                        String newDescription = scanner.nextLine();

                        bookService.updateBook(editId, new Book(editId, newTitle, newAuthor, newDescription));
                        System.out.println("The book has been updated.");
                        break;
                    case 4:
                        System.out.println("Enter the book ID to delete:");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();

                        bookService.deleteBook(deleteId);
                        System.out.println("Book deleted.");
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid input. Please select the correct option.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error processing file: " + e.getMessage());
            }
        }
    }

}
