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

import com.takehome.bookstore.DTOs.authors.CreateAuthorDTO;
import com.takehome.bookstore.DTOs.authors.UpdateAuthorDTO;
import com.takehome.bookstore.DTOs.authors.UpdateAuthorResponseDTO;
import com.takehome.bookstore.DTOs.books.DeleteResponse;
import com.takehome.bookstore.models.Books.Author;
import com.takehome.bookstore.services.AuthorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Authors")
public class AuthorController {

    private final AuthorService service;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<UpdateAuthorResponseDTO> create(
            @Valid @RequestBody CreateAuthorDTO request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping
    public ResponseEntity<Page<Author>> getAllAuthors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(service.getAllAuthors(pageRequest));
    }

    @PutMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<UpdateAuthorResponseDTO> update(
            @PathVariable @NotNull Integer authorId,
            @Valid @RequestBody UpdateAuthorDTO request) {

        return ResponseEntity.ok(service.update(authorId, request));
    }

    @DeleteMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public ResponseEntity<DeleteResponse> delete(@PathVariable @NotNull Integer authorId) {
        return ResponseEntity.ok(service.delete(authorId));
    }

}
