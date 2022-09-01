package com.dinsaren.bbuappserver.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
				.info(new Info()
						.title("BBU API")
						.description("BBU Server for mobile")
						.version("v1.0")
						.contact(new Contact()
								.name("Arun")
								.url("https://asbnotebook.com")
								.email("asbnotebook@gmail.com"))
						.termsOfService("TOC")
						.license(new License().name("License").url("#"))
					 );
	}
}