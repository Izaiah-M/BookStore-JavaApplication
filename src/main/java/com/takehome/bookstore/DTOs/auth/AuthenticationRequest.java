package com.takehome.bookstore.DTOs.auth;

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
public class AuthenticationRequest {

    @NotBlank(message = "Email is required")
    @NotNull(message = "Email cannot be null")
    private String email;

    @NotBlank(message = "Password is required")
    @NotNull(message = "Password cannot be null")
    private String password;

}
