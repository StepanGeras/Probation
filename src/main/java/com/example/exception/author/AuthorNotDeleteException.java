package com.example.exception.author;

public class AuthorNotDeleteException extends RuntimeException{

    public AuthorNotDeleteException(String message) {
        super(message);
    }
}
