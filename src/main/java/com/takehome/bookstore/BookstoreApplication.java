package com.takehome.bookstore;

import static com.takehome.bookstore.models.User.Role.ADMIN;
import static com.takehome.bookstore.models.User.Role.MANAGER;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.takehome.bookstore.DTOs.auth.RegisterRequest;
import com.takehome.bookstore.models.User.UserRepository;
import com.takehome.bookstore.services.AuthenticationService;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AuthenticationService service, UserRepository userRepository) {
		return args -> {
			if (!userRepository.existsByRole(ADMIN)) {
				var admin = RegisterRequest.builder()
						.firstname("Admin")
						.lastname("Admin")
						.email("admin@mail.com")
						.password("#Admin123")
						.role(ADMIN)
						.build();

				System.out.println("Admin token: " + service.register(admin).getToken());
			} else {
				System.out.println("Admin role already exists");
			}

			if (!userRepository.existsByRole(MANAGER)) {
				var manager = RegisterRequest.builder()
						.firstname("Manager")
						.lastname("Manager")
						.email("manager@mail.com")
						.password("#Admin123")
						.role(MANAGER)
						.build();

				System.out.println("Manager token: " + service.register(manager).getToken());
			} else {
				System.out.println("Manager role already exists");
			}
		};
	}

}
