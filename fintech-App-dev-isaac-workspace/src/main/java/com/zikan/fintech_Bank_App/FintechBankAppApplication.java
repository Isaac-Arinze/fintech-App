package com.zikan.fintech_Bank_App;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	info = @Info(
		title = "Fintech Bank App API",
		version = "1.0",
		description = "API documentation for the Fintech Bank Application"
	)
)
@SpringBootApplication
public class FintechBankAppApplication {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(FintechBankAppApplication.class, args);
	}

}
