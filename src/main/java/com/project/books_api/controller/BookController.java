package com.project.books_api.controller;

import com.project.books_api.entity.Book;
import com.project.books_api.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

//    @Operation(summary="Get a book by Id", description = "Retrieve a book by its Id")
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/{id}")
//    public Book getBookByTitle(@Parameter(description = "Id of book to be retrieve")
//                                @PathVariable @Min(value = 1) long id) {
//
//        return books.stream()
//                .filter(book -> book.getId() == id)
//                .findFirst()
//                .orElseThrow(() -> new BookNotFoundException("Book not found"));
//    }
//
//    @Operation(summary="Create new book", description = "Add a book to a list")
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    public void createBook(@Valid @RequestBody BookRequest bookRequest) {
//        long newId = books.isEmpty() ? 1 : (books.getLast().getId() + 1);
//        Book newBook = convertBook(newId, bookRequest);
//        books.add(newBook);
//    }
//
//    private Book convertBook(long id, BookRequest bookRequest) {
//        return new Book(
//                id,
//                bookRequest.getTitle(),
//                bookRequest.getAuthor(),
//                bookRequest.getCategory(),
//                bookRequest.getRating()
//        );
//    }
//
//    @Operation(summary="Update a book", description = "Update a details of a book")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PutMapping("/{id}")
//    public Book updateBook(@Parameter(description = "Id of the book to update")
//                               @PathVariable @Min(value = 1) long id,@Valid @RequestBody BookRequest bookRequest) {
//        for (int i = 0; i < books.size(); i++) {
//            if (books.get(i).getId() == id) {
//                Book updatedBook = convertBook(id, bookRequest);
//                books.set(i, updatedBook);
//                return updatedBook;
//            }
//        }
//        throw new BookNotFoundException("Book not found");
//    }
//
//    @Operation(summary="Delete a book", description = "Remove a book from the list")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping("/{id}")
//    public void deleteBook(@Parameter(description = "Id of the book to delete")
//                               @PathVariable @Min(value = 1) long id) {
//        books.stream()
//                .filter(book -> book.getId() == id)
//                .findFirst()
//                .orElseThrow(() -> new BookNotFoundException("Book not found " + id));
//
//        books.removeIf(book -> book.getId() == id);
//    }

}
