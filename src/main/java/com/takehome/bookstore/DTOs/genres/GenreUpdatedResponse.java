package com.takehome.bookstore.DTOs.genres;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenreUpdatedResponse {

    private String message;

    private Integer genreId;
}
