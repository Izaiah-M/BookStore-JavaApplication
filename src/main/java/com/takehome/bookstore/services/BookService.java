package com.takehome.bookstore.services;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.takehome.bookstore.DTOs.books.BookUpdatedResponse;
import com.takehome.bookstore.DTOs.books.CreateBookRequest;
import com.takehome.bookstore.DTOs.books.DeleteResponse;
import com.takehome.bookstore.DTOs.books.UpdateBookRequest;
import com.takehome.bookstore.models.Books.Author;
import com.takehome.bookstore.models.Books.AuthorRepository;
import com.takehome.bookstore.models.Books.Book;
import com.takehome.bookstore.models.Books.BookRepository;
import com.takehome.bookstore.models.Books.Genre;
import com.takehome.bookstore.models.Books.GenreRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    public BookUpdatedResponse create(@Valid CreateBookRequest request) {

        // Fetch the genre by ID from the database
        Genre genre = genreRepository.findById(request.getGenreId())
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Genre with ID " + request.getGenreId() + " not found");
                });

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Author with ID " + request.getGenreId() + " not found");
                });

        // Create the book entity
        Book book = Book.builder()
                .title(request.getTitle())
                .author(author)
                .genre(genre)
                .quantity(request.getQuantity())
                .description(request.getDescription())
                .publicationYear(request.getPublicationYear())
                .price(request.getPrice())
                .build();

        // Save the book
        Book savedBook = bookRepository.save(book);

        // Create and return the response
        return BookUpdatedResponse.builder()
                .message("Book created successfully")
                .bookId(savedBook.getId())
                .build();
    }

    public BookUpdatedResponse update(Integer bookId, @Valid UpdateBookRequest request) {
        // Fetch the existing book by ID
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book with ID " + bookId + " not found"));

        // Update the fields
        existingBook.setTitle(request.getTitle());
        existingBook.setAuthor(authorRepository.findById(request.getAuthorId())
                .orElseThrow(
                        () -> new NoSuchElementException("Author with ID " + request.getGenreId() + " not found")));
        existingBook.setGenre(genreRepository.findById(request.getGenreId())
                .orElseThrow(
                        () -> new NoSuchElementException("Genre with ID " + request.getGenreId() + " not found")));
        existingBook.setQuantity(request.getQuantity());
        existingBook.setPrice(request.getPrice());
        existingBook.setDescription(request.getDescription());
        existingBook.setPublicationYear(request.getPublicationYear());

        // Save the updated book
        Book updatedBook = bookRepository.save(existingBook);

        // Create and return the response
        return BookUpdatedResponse.builder()
                .message("Book updated successfully")
                .bookId(updatedBook.getId())
                .build();

    }

    public Page<Book> getAllBooks(PageRequest pageRequest) {
        return bookRepository.findAll(pageRequest);
    }

    public ResponseEntity<Book> getBookById(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElse(null);

        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public DeleteResponse delete(@NotNull Integer bookId) {

        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book with ID " + bookId + " not found"));

        bookRepository.delete(existingBook);

        return DeleteResponse.builder()
                .message("Book has been successfully deleted")
                .build();

    }

}
