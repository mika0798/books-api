package com.project.books_api.controller;

import com.project.books_api.entity.Book;
import com.project.books_api.entity.BookRequest;
import com.project.books_api.exception.BookNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name="Books Rest API Endpoints", description = "Operations related to books")
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final List<Book> books = new ArrayList<>();

    public BookController() {
        init();
    };

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

    @Operation(summary="Get all books", description = "Retrieve a list of all available books")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Book> getBooks(@Parameter(description = "Optional query parameter for book category")
                                   @RequestParam(required = false) String category) {
        List<Book> booksByCategory = new ArrayList<>();
        if (category == null) {
            return books;
        }
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @Operation(summary="Get a book by Id", description = "Retrieve a book by its Id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookByTitle(@Parameter(description = "Id of book to be retrieve")
                                @PathVariable @Min(value = 1) long id) {

        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    @Operation(summary="Create new book", description = "Add a book to a list")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createBook(@Valid @RequestBody BookRequest bookRequest) {
        long newId = books.isEmpty() ? 1 : (books.getLast().getId() + 1);
        Book newBook = convertBook(newId, bookRequest);
        books.add(newBook);
    }

    private Book convertBook(long id, BookRequest bookRequest) {
        return new Book(
                id,
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getCategory(),
                bookRequest.getRating()
        );
    }

    @Operation(summary="Update a book", description = "Update a details of a book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public Book updateBook(@Parameter(description = "Id of the book to update")
                               @PathVariable @Min(value = 1) long id,@Valid @RequestBody BookRequest bookRequest) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                Book updatedBook = convertBook(id, bookRequest);
                books.set(i, updatedBook);
                return updatedBook;
            }
        }
        throw new BookNotFoundException("Book not found");
    }

    @Operation(summary="Delete a book", description = "Remove a book from the list")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@Parameter(description = "Id of the book to delete")
                               @PathVariable @Min(value = 1) long id) {
        books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book not found " + id));

        books.removeIf(book -> book.getId() == id);
    }

}
