package com.example.exception.user;

public class UserNotDeleteException extends RuntimeException{

    public UserNotDeleteException(String message) {
        super(message);
    }
}
