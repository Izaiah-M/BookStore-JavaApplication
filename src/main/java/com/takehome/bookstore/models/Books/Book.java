package com.takehome.bookstore.models.Books;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String author;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    private Integer quantity;

    private Double price;

    private String description;

    private Integer publicationYear;
}
