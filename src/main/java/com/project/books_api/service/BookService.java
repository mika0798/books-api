package com.project.books_api.service;

import com.project.books_api.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long bookId);
    List<Book> getBooksByCategory(String category);
    Book saveBook(Book book);
    Book updateBook(Long id, Book book);
    void deleteBookById(Long bookId);
}
