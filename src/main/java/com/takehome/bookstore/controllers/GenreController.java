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

import com.takehome.bookstore.DTOs.books.DeleteResponse;
import com.takehome.bookstore.DTOs.genres.CreateGenreRequest;
import com.takehome.bookstore.DTOs.genres.GenreUpdatedResponse;
import com.takehome.bookstore.DTOs.genres.UpdateGenreRequest;
import com.takehome.bookstore.models.Books.Genre;
import com.takehome.bookstore.services.GenreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Genres")
public class GenreController {

    private final GenreService service;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(description = "This is for creating a Genre, Access level required is ADMIN or MANAGER", summary = "Create Genre")
    public ResponseEntity<GenreUpdatedResponse> create(
            @Valid @RequestBody CreateGenreRequest request) {

        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{genreId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(description = "This is for updating a Genre, Access level required is ADMIN or MANAGER", summary = "Update Genre")
    public ResponseEntity<GenreUpdatedResponse> update(
            @PathVariable Integer genreId,
            @Valid @RequestBody UpdateGenreRequest request) {
        return ResponseEntity.ok(service.update(genreId, request));
    }

    @GetMapping
    @Operation(description = "This is for getting all genres, Access level required is USER, ADMIN, MANAGER", summary = "Get all Genres")
    public ResponseEntity<Page<Genre>> getAllGenres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(service.getAllGenres(pageRequest));
    }

    @GetMapping("/{genreId}")
    @Operation(description = "This is for getting  a single genre, Access level required is USER, ADMIN, MANAGER", summary = "Get Genre By Id")
    public ResponseEntity<Genre> getGenreById(@PathVariable Integer genreId) {
        return service.getGenreById(genreId);
    }

    @DeleteMapping("/{genreId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(description = "This is for deleting a Genre, Access level required is ADMIN or MANAGER", summary = "Delete Genre")
    public ResponseEntity<DeleteResponse> delete(@PathVariable @NotNull Integer genreId) {
        return ResponseEntity.ok(service.delete(genreId));
    }
}
