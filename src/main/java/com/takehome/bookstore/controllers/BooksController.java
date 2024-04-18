package com.takehome.bookstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.takehome.bookstore.DTOs.books.BookUpdatedResponse;
import com.takehome.bookstore.DTOs.books.CreateBookRequest;
import com.takehome.bookstore.DTOs.books.DeleteResponse;
import com.takehome.bookstore.DTOs.books.UpdateBookRequest;
import com.takehome.bookstore.models.Books.Book;
import com.takehome.bookstore.services.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Books")
public class BooksController {

    private final BookService service;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(description = "This is for creating a Book, Access level required is ADMIN or MANAGER", summary = "Create Book")
    public ResponseEntity<BookUpdatedResponse> create(
            @Valid @RequestBody CreateBookRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(description = "This is for updating a Book, Access level required is ADMIN or MANAGER", summary = "Update Book")
    public ResponseEntity<BookUpdatedResponse> update(
            @PathVariable @NotNull Integer bookId,
            @Valid @RequestBody UpdateBookRequest request) {
        return ResponseEntity.ok(service.update(bookId, request));
    }

    @GetMapping
    @Operation(description = "This is for getting all books, Access level required is USER, ADMIN, MANAGER", summary = "Get all Books")
    public ResponseEntity<Page<Book>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(service.getAllBooks(pageRequest));
    }

    @GetMapping("/{bookId}")
    @Operation(description = "This is for getting a single book, Access level required is USER, ADMIN, MANAGER", summary = "Get Book By Id")
    public ResponseEntity<Book> getBookById(@PathVariable @NotNull Integer bookId) {
        return service.getBookById(bookId);
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(description = "This is for deleting a Book, Access level required is ADMIN or MANAGER", summary = "Delete Book")
    public ResponseEntity<DeleteResponse> delete(@PathVariable @NotNull Integer bookId) {
        return ResponseEntity.ok(service.delete(bookId));
    }

}
