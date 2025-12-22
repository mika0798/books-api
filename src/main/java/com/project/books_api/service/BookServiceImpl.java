package com.project.books_api.service;

import com.project.books_api.dto.BookRequest;
import com.project.books_api.exception.BookNotFoundException;
import com.project.books_api.repository.BookRepository;
import com.project.books_api.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository repository;
    private JsonMapper jsonMapper;

    @Autowired
    public void setRepository(BookRepository repository, JsonMapper jsonMapper) {
        this.repository = repository;
        this.jsonMapper = jsonMapper;
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
        if (category == null || category.isEmpty()) {
            return repository.findAll();
        }
        return repository.findByCategoryIgnoreCase(category);
    }

    @Override
    public Page<Book> getBooksByCategory(String category, Pageable pageable) {
        if (category == null || category.isBlank()) {
            return repository.findAll(pageable);
        }
        return repository.findByCategoryIgnoreCase(category, pageable);
    }

    @Override
    public Book saveBook(Book book) {
        return repository.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        Book findBookById = repository
                .findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));
        repository.deleteById(id);
    }

    @Override
    public Book convertBook(long id, BookRequest bookRequest) {
        Book newBook = new Book();
        if (id > 0) newBook.setId(id);
        newBook.setTitle(bookRequest.getTitle());
        newBook.setAuthor(bookRequest.getAuthor());
        newBook.setCategory(bookRequest.getCategory());
        newBook.setPrice(bookRequest.getPrice());
        newBook.setRating(bookRequest.getRating());
        return newBook;
    }

    @Override
    public Book patchBook(Map<String,Object> patchPayload, Book book) {
        ObjectNode patchNode = jsonMapper.convertValue(patchPayload, ObjectNode.class);
        ObjectNode bookNode = jsonMapper.convertValue(book, ObjectNode.class);

        bookNode.setAll(patchNode);
        return  jsonMapper.convertValue(bookNode, Book.class);
    }

}
