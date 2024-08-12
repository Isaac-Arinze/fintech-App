package com.zikan.fintech_Bank_App;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Demo Bank APP",
				description = "Backend Rest APIs for Skytech Bank",
				version = "v1.0",
				contact = @Contact(
						name = "Isaac Arinze - CEO @Skytech ",
						email = "skytecomputer@gmail.com, isaac.arinze.dev@gmail.com",
						url = "https://github.com/Isaac-Arinze/fintech-App"
				),
				license = @License(
						name = "Skytech Technology",
						url = "https://github.com/Isaac-Arinze/fintech-App"
				)

		),
		externalDocs = @ExternalDocumentation(
				description = "The Fintech Bank App Documentation",
				url = "https://github.com/Isaac-Arinze/fintech-App"
		)
)
public class FintechBankAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintechBankAppApplication.class, args);
	}

}
