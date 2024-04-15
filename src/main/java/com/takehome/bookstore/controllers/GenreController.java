package com.takehome.bookstore.controllers;

import java.util.HashMap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.takehome.bookstore.DTOs.genres.CreateGenreRequest;
import com.takehome.bookstore.DTOs.genres.GenreUpdatedResponse;
import com.takehome.bookstore.DTOs.genres.UpdateGenreRequest;
import com.takehome.bookstore.models.Books.Genre;
import com.takehome.bookstore.services.GenreService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Genres")
public class GenreController {

    private final GenreService service;

    @PostMapping("/create")
    public ResponseEntity<GenreUpdatedResponse> create(
            @Valid @RequestBody CreateGenreRequest request) {

        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{genreId}")
    public ResponseEntity<GenreUpdatedResponse> update(
            @PathVariable Integer genreId,
            @Valid @RequestBody UpdateGenreRequest request) {
        return ResponseEntity.ok(service.update(genreId, request));
    }

    @GetMapping
    public ResponseEntity<Page<Genre>> getAllGenres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(service.getAllGenres(pageRequest));
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Integer genreId) {
        return service.getGenreById(genreId);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exp) {
        var errors = new HashMap<String, String>();

        exp.getBindingResult().getAllErrors()
                .forEach((error) -> {
                    var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
