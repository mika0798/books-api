package com.project.books_api.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotFoundException(Throwable cause) {
        super(cause);
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
