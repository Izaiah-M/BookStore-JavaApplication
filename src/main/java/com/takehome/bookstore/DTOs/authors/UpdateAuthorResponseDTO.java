package com.takehome.bookstore.DTOs.authors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAuthorResponseDTO {

    private String message;

    private Integer authorId;
}
