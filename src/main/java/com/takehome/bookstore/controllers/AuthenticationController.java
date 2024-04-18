package com.takehome.bookstore.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takehome.bookstore.DTOs.auth.AuthenticationRequest;
import com.takehome.bookstore.DTOs.auth.AuthenticationResponse;
import com.takehome.bookstore.DTOs.auth.RegisterRequest;
import com.takehome.bookstore.services.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    @Operation(description = "This is for registering a user, the default role given is USER", summary = "Registration Endpoint")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    @Operation(description = "This is for loggin in a user", summary = "Login Endpoint")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.login(request));

    }
}
