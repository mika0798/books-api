package com.project.books_api.service;

import com.project.books_api.dto.BookRequest;
import com.project.books_api.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long bookId);
    List<Book> getBooksByCategory(String category);
    Page<Book> getBooksByCategory(String category, Pageable pageable);
    Book saveBook(Book book);
    void deleteBookById(Long bookId);
    Book convertBook(long id, BookRequest bookRequest);
    Book patchBook(Map<String,Object> patchPayload, Book book);
}
