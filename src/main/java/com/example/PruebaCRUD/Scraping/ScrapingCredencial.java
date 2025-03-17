package com.example.PruebaCRUD.Scraping;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ScrapingCredencial {

    private static final String IMAGE_DIR = "./src/main/java/com/example/PruebaCRUD/Scraping/images/";

    public static String capturarCredencial(String credencialUrl) throws IOException {
        System.out.println("Iniciando captura de credencial...");
        System.out.println("URL recibida: " + credencialUrl);

        String alumnoId = extractAlumnoId(credencialUrl);
        if (alumnoId == null) {
            throw new IOException("No se pudo extraer un identificador válido de la URL.");
        }
        System.out.println("ID del alumno extraído: " + alumnoId);

        String imageName = "credencial_" + alumnoId + "_" + System.currentTimeMillis() + ".png";
        System.out.println("Nombre del archivo generado: " + imageName);

        // Crear directorio de imágenes si no existe
        File imageDir = new File(IMAGE_DIR);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // Modo sin interfaz gráfica
        options.addArguments("--window-size=1920,1080"); // Resolución de la captura

        WebDriver driver = new ChromeDriver(options);
        System.out.println("Navegador iniciado en modo headless.");

        try {
            driver.get(credencialUrl);
            System.out.println("Página cargada correctamente.");

            Thread.sleep(3000);

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            System.out.println("Captura de pantalla realizada.");

            File imageFile = new File(imageDir, imageName);
            Files.copy(screenshot.toPath(), imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Imagen guardada en: " + imageFile.getAbsolutePath());

            // Devuelve solo la ruta del archivo, sin texto adicional
            return imageFile.getAbsolutePath();

        } catch (IOException | InterruptedException e) {
            System.out.println("Error al capturar la credencial: " + e.getMessage());
            throw new IOException("Error al capturar la credencial: " + e.getMessage());
        } finally {
            driver.quit();
            System.out.println("Navegador cerrado.");
        }
    }

    private static String extractAlumnoId(String url) {
        System.out.println("Extrayendo ID del alumno de la URL...");
        int index = url.indexOf("?h=");
        if (index != -1) {
            String id = url.substring(index + 3, Math.min(url.length(), index + 150));
            System.out.println("ID extraído correctamente: " + id);
            return id;
        }
        System.out.println("No se encontró un ID válido en la URL.");
        return null;
    }
}