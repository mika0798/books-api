package com.project.books_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="author")
    private String author;

    @Column(name="category")
    private String category;

    @Column(name="price")
    private double price;

    @Column(name="rating")
    private int rating;

}
