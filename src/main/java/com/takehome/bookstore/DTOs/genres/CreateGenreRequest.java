package com.takehome.bookstore.DTOs.genres;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateGenreRequest {

    @Valid
    @NotBlank(message = "Name is required")
    @NotNull(message = "Name should not be null")
    private String name;

}
