package com.takehome.bookstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.takehome.bookstore.DTOs.books.BookUpdatedResponse;
import com.takehome.bookstore.DTOs.books.CreateBookRequest;
import com.takehome.bookstore.DTOs.books.UpdateBookRequest;
import com.takehome.bookstore.models.Books.Book;
import com.takehome.bookstore.services.BookService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Books")
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

    @GetMapping
    public ResponseEntity<Page<Book>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(service.getAllBooks(pageRequest));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer bookId) {
        return service.getBookById(bookId);
    }

}
