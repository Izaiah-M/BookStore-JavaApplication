package com.takehome.bookstore.DTOs.authors;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAuthorDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

}
