package com.zikan.fintech_Bank_App;

import com.twilio.Twilio;
import com.zikan.fintech_Bank_App.config.TwilloConfig;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(TwilloConfig.class)
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

	@Autowired
	private TwilloConfig twilloConfig;

	@PostConstruct
	public void setup(){
		Twilio.init(twilloConfig.getAccountSid(), twilloConfig.getAuthToken());
	}

	

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(FintechBankAppApplication.class, args);
	}

}
