package com.project.books_api.controller;

import com.project.books_api.dto.ApiResponse;
import com.project.books_api.dto.BookRequest;
import com.project.books_api.entity.Book;
import com.project.books_api.service.BookService;
import com.project.books_api.validator.BookValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<ApiResponse<List<Book>>> getBooks(@Parameter(description = "Optional query parameter for book category")
                                   @RequestParam(required = false) String category) {
        List<Book> books;
        if (category == null || category.isBlank()) {
            books = bookService.getAllBooks();
        } else {
            books =  bookService.getBooksByCategory(category);
        }

        ApiResponse<List<Book>> response = new ApiResponse<>("Success","Books found", books);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary="Get a book by Id", description = "Retrieve a book by its Id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@Parameter(description = "Id of book to be retrieve")
                                @PathVariable @Min(value = 1) long id) {
        Book  book = bookService.getBookById(id);
        ApiResponse<Book> response = new ApiResponse<>("Success","Book found", book);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary="Create new book", description = "Add a book to a list")
    @PostMapping
    public ResponseEntity<ApiResponse<Book>> createBook(@Valid @RequestBody BookRequest bookRequest) {
        Book newBook = bookService.convertBook(0L, bookRequest);
        Book savedBook = bookService.saveBook(newBook);
        ApiResponse<Book> response = new ApiResponse<>("Success","Book created successfully", savedBook);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary="Update a book", description = "Update a details of a book")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> updateBook(
            @Parameter(description = "Id of the book to update")
            @PathVariable @Min(value = 1) long id,
            @Valid @RequestBody BookRequest bookRequest) {

        Book updateBook = bookService.convertBook(id, bookRequest);
        Book savedBook = bookService.saveBook(updateBook);
        ApiResponse<Book> response = new ApiResponse<>("Success","Book updated successfully", savedBook);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary="Update a book partly",description="Update only some details of a book")
    public ResponseEntity<ApiResponse<Book>> updatePartly(@PathVariable @Min(value=1) Long id,
                                                 @RequestBody Map<String,Object> patchPayload) {
        if (patchPayload.containsKey("id")) {
            throw new RuntimeException("Id is not allowed in Request Body");
        }
        Book tempEmployee = bookService.getBookById(id);
        Book patchedEmployee = bookService.patchBook(patchPayload,tempEmployee);
        ApiResponse<Book> response = new ApiResponse<>("Success","Book updated successfully", patchedEmployee);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @Operation(summary="Delete a book", description = "Remove a book from the list")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@Parameter(description = "Id of the book to delete")
                               @PathVariable @Min(value = 1) long id) {

        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

}
