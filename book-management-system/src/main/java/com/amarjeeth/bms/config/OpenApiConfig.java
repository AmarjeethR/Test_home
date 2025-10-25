package com.amarjeeth.bms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("Book Management System API")
				.description("RESTful API for managing library books, members, and borrowing operations")
				.version("v1.0.0").contact(new Contact().name("Banbana Support").email("support@banbana.com")));
	}
}
