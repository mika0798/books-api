package com.project.books_api.repository;

import com.project.books_api.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByCategoryIgnoreCase(String category);
    Page<Book> findByCategoryIgnoreCase(String category, Pageable pageable);
}
