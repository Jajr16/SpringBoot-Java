package com.example.PruebaCRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.PruebaCRUD.Repositories")
@ComponentScan(basePackages = "com.example.PruebaCRUD")
@EntityScan(basePackages = "com.example.PruebaCRUD.BD")
@RestController // Notación que hace que esto se vuelva un controlador.
//@EnableScheduling
public class PruebaCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaCrudApplication.class, args);
	}

}
