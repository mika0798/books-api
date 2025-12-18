package com.project.books_api.controller;

import com.project.books_api.dto.BookRequestDto;
import com.project.books_api.entity.Book;
import com.project.books_api.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Books Rest API Endpoints", description = "Operations related to books")
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary="Get all books", description = "Retrieve a list of all available books")
    @GetMapping
    public ResponseEntity<List<Book>> getBooks(@Parameter(description = "Optional query parameter for book category")
                                   @RequestParam(required = false) String category) {
        List<Book> books;
        if (category == null || category.isBlank()) {
            books = bookService.getAllBooks();
        } else {
            books =  bookService.getBooksByCategory(category);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(summary="Get a book by Id", description = "Retrieve a book by its Id")
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@Parameter(description = "Id of book to be retrieve")
                                @PathVariable @Min(value = 1) long id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @Operation(summary="Create new book", description = "Add a book to a list")
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookRequestDto bookRequest) {
        Book newBook = convertBook(0L, bookRequest);
        Book savedBook = bookService.saveBook(newBook);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @Operation(summary="Update a book", description = "Update a details of a book")
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @Parameter(description = "Id of the book to update")
            @PathVariable @Min(value = 1) long id,
            @Valid @RequestBody BookRequestDto bookRequest) {

        Book updateBook = convertBook(id, bookRequest);
        Book savedBook = bookService.saveBook(updateBook);

        return new ResponseEntity<>(savedBook, HttpStatus.OK);
    }

    @Operation(summary="Delete a book", description = "Remove a book from the list")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@Parameter(description = "Id of the book to delete")
                               @PathVariable @Min(value = 1) long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    private Book convertBook(long id, BookRequestDto bookRequest) {
        Book newBook = new Book();
        if (id > 0) newBook.setId(id);
        newBook.setTitle(bookRequest.getTitle());
        newBook.setAuthor(bookRequest.getAuthor());
        newBook.setCategory(bookRequest.getCategory());
        newBook.setRating(bookRequest.getRating());
        return newBook;
    }
}
