package com.project.books_api.controller;

import com.project.books_api.entity.Book;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final List<Book> books = new ArrayList<>();

    @PostConstruct
    private void init() {
        books.addAll(List.of(
                new Book(1,"Computer Science Pro","Chad Darby","Computer Science",5),
                new Book(2,"Java Spring Master","Eric Roby","Computer Science",5),
                new Book(3,"Why 1+1 Rocks","Adil A.","Math",5),
                new Book(4,"How Bears Hibernate","Bob B.","Science",2),
                new Book(5,"A Pirate's Treasure","Curt C","History",3),
                new Book(6,"Why 2+2 Better","Dan D","Math",1),
                new Book(7,"Microservice with Spring Boot","Chad Darby","Computer Science",5)
        ));
    }

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String category) {
        List<Book> booksByCategory = new ArrayList<>();
        if (category == null) {
            return books;
        }
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @GetMapping("/{id}")
    public Book getBookByTitle(@PathVariable long id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public void addBook(@RequestBody Book addBook) {
        boolean isNewBook = books.stream()
                .noneMatch(b -> b.getTitle().equalsIgnoreCase(addBook.getTitle()));
        if (isNewBook) {
            books.add(addBook);
        }
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable long id, @RequestBody Book updateBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                books.set(i, updateBook);
                return;
            }
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id) {
        books.removeIf(book -> book.getId() == id);
    }

}
