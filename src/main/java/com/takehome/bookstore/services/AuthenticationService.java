package com.takehome.bookstore.services;

import static com.takehome.bookstore.models.User.Role.USER;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.takehome.bookstore.DTOs.auth.AdminAuthenticationRequest;
import com.takehome.bookstore.DTOs.auth.AuthenticationRequest;
import com.takehome.bookstore.DTOs.auth.AuthenticationResponse;
import com.takehome.bookstore.DTOs.auth.RegisterRequest;
import com.takehome.bookstore.config.JwtService;
import com.takehome.bookstore.models.User.User;
import com.takehome.bookstore.models.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

        private final UserRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;

        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) {

                var user = User.builder()
                                .firstname(request.getFirstname())
                                .lastname(request.getLastname())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(USER)
                                .build();

                repository.save(user);

                var jwtToken = jwtService.generateToken(user);

                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }

        // For registering admins at the start of the application
        public AuthenticationResponse registerAdminManager(AdminAuthenticationRequest request) {
                var user = User.builder()
                                .firstname(request.getFirstname())
                                .lastname(request.getLastname())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(request.getRole())
                                .build();

                repository.save(user);

                var jwtToken = jwtService.generateToken(user);

                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }

        public AuthenticationResponse login(AuthenticationRequest request) {

                authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                                                request.getPassword()));

                var user = repository.findByEmail(request.getEmail()).orElseThrow();

                var jwtToken = jwtService.generateToken(user);

                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }
}
