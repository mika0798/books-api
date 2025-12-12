package com.project.books_api.service;

import com.project.books_api.dao.BookRepository;
import com.project.books_api.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository repository;

    @Autowired
    public void setRepository(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book getBookById(Long bookId) {
        return repository.findById(bookId).orElse(null);
    }

    public Book saveBook(Book book) {
        return repository.save(book);
    }

    public void deleteBookById(Long bookId) {
        repository.deleteById(bookId);
    }

}
