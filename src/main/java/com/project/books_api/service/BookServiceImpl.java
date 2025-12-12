package com.project.books_api.service;

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
    public Optional<Book> getBookById(Long bookId) {
        return repository.findById(bookId);
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
    public Optional<Book> updateBook(Long id, Book book) {
        Optional<Book> optionalBook = repository.findById(id);
        if (optionalBook.isPresent()) {
            Book updatedBook = optionalBook.get();

            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setCategory(book.getCategory());
            updatedBook.setRating(book.getRating());

            return Optional.of(repository.save(updatedBook));
        }

        return Optional.empty();
    }

    @Override
    public void deleteBookById(Long bookId) {
        repository.deleteById(bookId);
    }

}
