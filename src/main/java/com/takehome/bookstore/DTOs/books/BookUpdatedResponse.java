package com.takehome.bookstore.DTOs.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdatedResponse {

    private String message;

    private Integer bookId;

}
