package com.example.PruebaCRUD;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "com.example.PruebaCRUD")
public class PruebaCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaCrudApplication.class, args
		);
	}

}
