package com.takehome.bookstore.services;

import org.springframework.stereotype.Service;

import com.takehome.bookstore.DTOs.books.BookUpdatedResponse;
import com.takehome.bookstore.DTOs.books.CreateRequest;
import com.takehome.bookstore.models.Books.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public BookUpdatedResponse create(CreateRequest request) {
        return null;
    }

}
