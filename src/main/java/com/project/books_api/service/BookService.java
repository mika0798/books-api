package com.project.books_api.service;

import com.project.books_api.dao.BookRepository;
import com.project.books_api.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Book> getBookById(Long bookId) {
        return repository.findById(bookId);
    }

    public Book saveBook(Book book) {
        return repository.save(book);
    }

    public Optional<Book> updateBook(Long id, Book book) {
        Optional<Book> optionalBook = repository.findById(id);
        if (optionalBook.isPresent()) {
            Book updatedBook = optionalBook.get();

            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setCategory(book.getCategory());
            updatedBook.setRating(book.getRating());

            return Optional.of(repository.save(book));
        }

        return Optional.empty();
    }

    public void deleteBookById(Long bookId) {
        repository.deleteById(bookId);
    }

}
