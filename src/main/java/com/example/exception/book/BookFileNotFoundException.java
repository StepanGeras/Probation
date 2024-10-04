package com.example.exception.book;

public class BookFileNotFoundException extends RuntimeException {

    public BookFileNotFoundException(String message) {
        super(message);
    }

    public BookFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
