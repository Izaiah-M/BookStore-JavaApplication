package com.takehome.bookstore.services;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.takehome.bookstore.DTOs.authors.CreateAuthorDTO;
import com.takehome.bookstore.DTOs.authors.UpdateAuthorDTO;
import com.takehome.bookstore.DTOs.authors.UpdateAuthorResponseDTO;
import com.takehome.bookstore.DTOs.books.DeleteResponse;
import com.takehome.bookstore.models.Books.Author;
import com.takehome.bookstore.models.Books.AuthorRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public UpdateAuthorResponseDTO create(@Valid CreateAuthorDTO request) {

        Author author = Author.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();

        Author savedAuthor = authorRepository.save(author);

        return UpdateAuthorResponseDTO.builder()
                .message("Author created Successfully")
                .authorId(savedAuthor.getId())
                .build();

    }

    public Page<Author> getAllAuthors(PageRequest pageRequest) {
        return authorRepository.findAll(pageRequest);
    }

    public UpdateAuthorResponseDTO update(@NotNull Integer authorId, @Valid UpdateAuthorDTO request) {
        // Fetch the existing book by ID
        Author existingAuthor = authorRepository.findById(authorId)
                .orElseThrow(() -> new NoSuchElementException("Author with ID " + authorId + " not found"));

        // Update the fields
        existingAuthor.setName(request.getName());
        existingAuthor.setEmail(request.getEmail());

        // Save the updated book
        Author updatedAuthor = authorRepository.save(existingAuthor);

        // Create and return the response
        return UpdateAuthorResponseDTO.builder()
                .message("Author updated successfully")
                .authorId(updatedAuthor.getId())
                .build();
    }

    public DeleteResponse delete(@NotNull Integer authorId) {
        Author existingAuthor = authorRepository.findById(authorId)
                .orElseThrow(() -> new NoSuchElementException("Book with ID " + authorId + " not found"));

        authorRepository.delete(existingAuthor);

        return DeleteResponse.builder()
                .message("Author has been successfully deleted")
                .build();
    }

}
