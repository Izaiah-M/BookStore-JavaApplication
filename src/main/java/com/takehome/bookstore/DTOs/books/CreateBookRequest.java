package com.takehome.bookstore.DTOs.books;

import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @NotNull
    private Integer genreId;

    @NotNull
    @Positive(message = "Quantity must be a positive number")
    private Integer quantity;

    @NotNull
    @Positive(message = "Price must be a positive number")
    private Double price;

    @NotEmpty
    private String description;

    @NotNull
    private Integer publicationYear;

}
