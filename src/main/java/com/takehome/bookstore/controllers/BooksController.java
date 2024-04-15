package com.takehome.bookstore.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takehome.bookstore.DTOs.books.BookUpdatedResponse;
import com.takehome.bookstore.DTOs.books.CreateBookRequest;
import com.takehome.bookstore.DTOs.books.UpdateBookRequest;
import com.takehome.bookstore.services.BookService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BooksController {

    private final BookService service;

    @PostMapping("/create")
    public ResponseEntity<BookUpdatedResponse> create(
            @Valid @RequestBody CreateBookRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookUpdatedResponse> update(
            @PathVariable Integer bookId,
            @Valid @RequestBody UpdateBookRequest request) {
        return ResponseEntity.ok(service.update(bookId, request));
    }

    // TODO: Endpoint to get all books: Apply pagination
    // TODO: Endpoint to get one book
    // TODO: Add ADMIN Auth to the routes

}
