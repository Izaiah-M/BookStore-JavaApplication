package com.takehome.bookstore.DTOs.books;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotNull(message = "Genre ID is required")
    private Integer genreId;

    @NotBlank(message = "Number of copies must be specified")
    @Positive(message = "Quantity must be a positive number")
    private Integer quantity;

    @NotBlank(message = "Price must be specified")
    @Positive(message = "Price must be a positive number")
    private Double price;

    @NotBlank(message = "Description must be specified")
    private String description;

    @NotBlank(message = "PublicationYear must be specified")
    private Integer publicationYear;

}
