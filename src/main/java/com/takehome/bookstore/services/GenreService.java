package com.takehome.bookstore.services;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.takehome.bookstore.DTOs.books.DeleteResponse;
import com.takehome.bookstore.DTOs.genres.CreateGenreRequest;
import com.takehome.bookstore.DTOs.genres.GenreUpdatedResponse;
import com.takehome.bookstore.DTOs.genres.UpdateGenreRequest;
import com.takehome.bookstore.models.Books.Genre;
import com.takehome.bookstore.models.Books.GenreRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public ResponseEntity<Genre> getGenreById(Integer genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Genre with ID " + genreId + " not found");
                });

        return ResponseEntity.ok(genre);
    }

    public Page<Genre> getAllGenres(PageRequest pageRequest) {
        return genreRepository.findAll(pageRequest);
    }

    public GenreUpdatedResponse update(Integer genreId, @Valid UpdateGenreRequest request) {
        // Fetch the existing genre by ID
        Genre existingGenre = genreRepository.findById(genreId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Genre with ID " + genreId + " not found");
                });

        // Update the fields
        existingGenre.setName(request.getName());

        // Save the updated genre
        Genre updatedGenre = genreRepository.save(existingGenre);

        // Create and return the response
        return GenreUpdatedResponse.builder()
                .message("Genre updated successfully")
                .genreId(updatedGenre.getId())
                .build();

    }

    public GenreUpdatedResponse create(@Valid CreateGenreRequest request) {

        // Create the book entity
        Genre genre = Genre.builder()
                .name(request.getName())
                .build();

        // Save the genre
        Genre savedGenre = genreRepository.save(genre);

        // Create and return the response
        return GenreUpdatedResponse.builder()
                .message("Genre created successfully")
                .genreId(savedGenre.getId())
                .build();
    }

    public DeleteResponse delete(@NotNull Integer genreId) {
        Genre existingGenre = genreRepository.findById(genreId)
                .orElseThrow(() -> new NoSuchElementException("Genre with ID " + genreId + " not found"));

        genreRepository.delete(existingGenre);

        return DeleteResponse.builder()
                .message("Genre has been successfully deleted")
                .build();
    }

}
