package com.takehome.bookstore.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Users")
public class UsersController {

    @GetMapping
    @Hidden // To hide it in the swagger docs
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello, user! this is a secure endpoint though, but is accessible to all roles");
    }

}
