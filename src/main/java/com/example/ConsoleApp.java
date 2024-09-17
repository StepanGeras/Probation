package com.example;

import com.example.localization.Localization;
import com.example.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.service.BookService;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleApp {

    private final BookService bookService;
    private final Localization localization;

    @Autowired
    public ConsoleApp(BookService bookService, Localization localization) {
        this.bookService = bookService;
        this.localization = localization;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose language / Выберите язык (en/ru):");
        String language = scanner.nextLine();
        localization.setLocale(language);

        while (true) {

            System.out.println(localization.getMessage("menu.options"));
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1":
                        List<Book> books = bookService.readBooks();
                        books.forEach(System.out::println);
                        break;
                    case "2":
                        System.out.println(localization.getMessage("menu.book.id"));
                        int id = scanner.nextInt();
                        scanner.nextLine();
//                        if (bookService.findBookById(id)) {
//                            System.out.printf(localization.getMessage("menu.book.id.exist"));
//                            break;
//                        }
                        System.out.println(localization.getMessage("menu.book.enter.title"));
                        String title = scanner.nextLine();
                        System.out.println(localization.getMessage("menu.book.enter.author"));
                        String author = scanner.nextLine();
                        System.out.println(localization.getMessage("menu.book.enter.description"));
                        String description = scanner.nextLine();

                        bookService.createBook(new Book(id, title, author, description));
                        System.out.println(localization.getMessage("menu.book.created"));
                        break;
                    case "3":
                        System.out.println(localization.getMessage("menu.book.id"));
                        int editId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println(localization.getMessage("menu.book.enter.title"));
                        String newTitle = scanner.nextLine();
                        System.out.println(localization.getMessage("menu.book.enter.author"));
                        String newAuthor = scanner.nextLine();
                        System.out.println(localization.getMessage("menu.book.enter.description"));
                        String newDescription = scanner.nextLine();

                        bookService.updateBook(editId, new Book(editId, newTitle, newAuthor, newDescription));
                        System.out.println(localization.getMessage("menu.book.updated"));
                        break;
                    case "4":
                        System.out.println(localization.getMessage("menu.book.id"));
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();

                        bookService.deleteBook(deleteId);
                        System.out.println(localization.getMessage("menu.book.deleted"));
                        break;
                    case "5":
                        return;
                    default:
                        System.out.println(localization.getMessage("menu.options"));
                        break;
                }
            } catch (IOException e) {
                System.out.println(localization.getMessage("menu.error"));
                System.out.println(e.getMessage());
            }

        }

    }

}
