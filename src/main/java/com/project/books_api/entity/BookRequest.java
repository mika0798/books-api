package com.project.books_api.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @Size(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
    private String title;

    @Size(min = 1, max = 30, message = "Author name must be between 1 and 30 characters")
    private String author;

    @Size(min = 1, max = 30, message = "Category must be between 1 and 30 characters")
    private String category;

    @Min(value = 1, message= "Rating must be larger than 0")
    @Max(value = 5, message= "Rating cannot go pass 5")
    private int rating;

}
