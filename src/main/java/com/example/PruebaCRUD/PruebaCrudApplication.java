package com.example.PruebaCRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController // Notación que hace que esto se vuelva un controlador.
public class PruebaCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaCrudApplication.class, args);
	}

}
