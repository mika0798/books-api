package com.project.books_api.service;

import com.project.books_api.exception.BookNotFoundException;
import com.project.books_api.repository.BookRepository;
import com.project.books_api.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository repository;

    @Autowired
    public void setRepository(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    @Override
    public Book getBookById(Long bookId) {
        return repository.findById(bookId)
                .orElseThrow(
                        () -> new BookNotFoundException("Book with id " + bookId + " not found")
                );
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        return repository.findByCategoryIgnoreCase(category);
    }

    @Override
    public Book saveBook(Book book) {
        return repository.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        repository.deleteById(id);
    }

}
