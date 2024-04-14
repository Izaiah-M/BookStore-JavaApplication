package com.takehome.bookstore.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(info = @Info(contact = @Contact(name = "Izaiah", email = "imukisa024@gmail.com"), description = "OpenApi Documentation for a Book store application", title = "Book Store ~ izaiah", version = "1.0"))
@SecurityScheme(name = "bearerAuth", description = "Bearer {token}", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {

}
