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
                new Book("Title One","Author One","Geography"),
                new Book("Title Two","Author Two","Math"),
                new Book("Title Three","Author Four","Math"),
                new Book("Title Four","Author Four","History"),
                new Book("Title Five","Author Five","Literature"),
                new Book("Title Six","Author Six","Chemistry"),
                new Book("Title Seven","Author Seven","Physics")
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

    @GetMapping("/id/{id}")
    public Book getBookById(@PathVariable int id) {
        if (id < 0 || id >= books.size()) {
            return null;
        }
        return books.get(id);
    }

    @GetMapping("/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
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

    @PutMapping("/{title}")
    public void updateBook(@PathVariable String title, @RequestBody Book updateBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getTitle().equalsIgnoreCase(title)) {
                books.set(i, updateBook);
                return;
            }
        }
    }

    @DeleteMapping("/{title}")
    public void deleteBook(@PathVariable String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

}
