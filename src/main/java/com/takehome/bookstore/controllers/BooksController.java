package com.takehome.bookstore.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takehome.bookstore.DTOs.books.BookUpdatedResponse;
import com.takehome.bookstore.DTOs.books.CreateBookRequest;
import com.takehome.bookstore.services.BookService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BooksController {

    private final BookService service;

    // TODO: EndPoint to create book for Admins
    @PostMapping("/create")
    public ResponseEntity<BookUpdatedResponse> create(
            @Valid @RequestBody CreateBookRequest request) {
        return ResponseEntity.ok(service.create(request));
    }
    // TODO: EndPoint to update a book for Admins
    // TODO: Endpoint to get all books: Apply pagination

}
