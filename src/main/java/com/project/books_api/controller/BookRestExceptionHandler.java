package com.project.books_api.controller;

import com.project.books_api.dto.BookErrorResponse;
import com.project.books_api.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookRestExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<BookErrorResponse> handleBookNotFoundException(BookNotFoundException e) {

        BookErrorResponse bookErrorResponse = new BookErrorResponse();

        bookErrorResponse.setMessage(e.getMessage());
        bookErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        bookErrorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(bookErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BookErrorResponse> handleException(Exception e) {

        BookErrorResponse bookErrorResponse = new BookErrorResponse();

        bookErrorResponse.setMessage(e.getMessage());
        bookErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        bookErrorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(bookErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
