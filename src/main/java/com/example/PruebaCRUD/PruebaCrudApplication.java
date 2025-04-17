package com.example.PruebaCRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.PruebaCRUD.Repositories")
@ComponentScan(basePackages = "com.example.PruebaCRUD")
@EntityScan(basePackages = "com.example.PruebaCRUD.BD")
@RestController // Notación que hace que esto se vuelva un controlador.
//@EnableScheduling
public class PruebaCrudApplication {

	public static void main(String[] args) {
		System.out.println("Java version: " + System.getProperty("java.version"));
		SpringApplication.run(PruebaCrudApplication.class, args
		);
	}

	@PostConstruct
	public void checkFileStoragePath() {
		System.out.println("Entró a checkFileStoragePath");
		String fileStoragePath = "/data/EntrenamientoIMG";
		File dir = new File(fileStoragePath);
		if (!dir.exists()) {
			boolean created = dir.mkdirs();
			System.out.println("Creado dir?: " + created);
		}

		try {
			File testFile = new File(fileStoragePath + "/test.txt");
			boolean success = testFile.createNewFile();
			System.out.println("¿Pudo crear archivo de prueba?: " + success);
		} catch (IOException e) {
			e.printStackTrace(); // Aquí verás si hay permisos denegados o no existe el path
		}
	}

}
