package com.takehome.bookstore.services;

import org.springframework.stereotype.Service;

import com.takehome.bookstore.DTOs.books.BookUpdatedResponse;
import com.takehome.bookstore.DTOs.books.CreateBookRequest;
import com.takehome.bookstore.models.Books.Book;
import com.takehome.bookstore.models.Books.BookRepository;
import com.takehome.bookstore.models.Books.Genre;
import com.takehome.bookstore.models.Books.GenreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookrepository;
    private final GenreRepository genreRepository;

    public BookUpdatedResponse create(CreateBookRequest request) {

        // Fetch the genre by ID from the database
        Genre genre = genreRepository.findById(request.getGenreId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Genre with ID " + request.getGenreId() + " not found"));

        // Create the book entity
        var book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .genre(genre)
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();

        // Save the book
        Book savedBook = bookrepository.save(book);

        // Create and return the response
        return BookUpdatedResponse.builder()
                .message("Book created successfully")
                .bookId(savedBook.getId())
                .build();
    }

}
