package com.project.books_api.repository;

import com.project.books_api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> getBookById(Long bookId);
    List<Book> findByCategoryIgnoreCase(String category);
}
