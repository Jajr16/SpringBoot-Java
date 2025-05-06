package com.example.PruebaCRUD.Scraping;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class ScrapingCredencial {

    private static final String IMAGE_DIR = "./src/main/java/com/example/PruebaCRUD/Scraping/images/";

    public static Map<String, String> capturarCredencial(String credencialUrl) throws IOException {
        System.out.println("Iniciando captura de credencial...");

        // Configura WebDriverManager para manejar automáticamente ChromeDriver
        WebDriverManager.chromedriver().setup();

        Map<String, String> resultados = new HashMap<>();

        // Extraer ID del alumno
        String alumnoId = extractAlumnoId(credencialUrl);
        if (alumnoId == null) {
            throw new IOException("No se pudo extraer un identificador válido de la URL.");
        }
        resultados.put("alumnoId", alumnoId);

        // Verificar si ya existe una imagen para este ID
        String existingImagePath = findExistingImage(alumnoId);
        if (existingImagePath != null) {
            System.out.println("Ya existe una imagen para este ID: " + existingImagePath);
            resultados.put("imagenPath", existingImagePath);
            return resultados;
        }

        // Configuración optimizada de Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--headless=new",         // Headless moderno
                "--no-sandbox",           // Necesario para CI/CD
                "--disable-dev-shm-usage"  // Para entornos con poca memoria
        );

        WebDriver driver = null;
        try {
            driver = new ChromeDriver(options);
            driver.get(credencialUrl);
            Thread.sleep(3000); // Espera para carga inicial

            // Extraer datos del HTML
            resultados.putAll(extraerDatosAlumno(driver));

            // Tomar screenshot solo si no existe la imagen
            if (!resultados.containsKey("imagenPath")) {
                // Ajustar tamaño de ventana para captura completa
                JavascriptExecutor js = (JavascriptExecutor) driver;
                long scrollHeight = (long) js.executeScript("return document.body.scrollHeight");
                driver.manage().window().setSize(new Dimension(1920, (int) scrollHeight));

                // Capturar y guardar imagen
                byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                String imageName = "credencial_" + alumnoId + ".png";
                Path destination = Paths.get(IMAGE_DIR, imageName);

                // Crear directorio si no existe
                Files.createDirectories(destination.getParent());
                Files.write(destination, screenshotBytes);

                resultados.put("imagenPath", destination.toAbsolutePath().toString());
                System.out.println("Screenshot guardado en: " + destination);
            }

            return resultados;

        } catch (Exception e) {
            throw new IOException("Error al procesar la credencial: " + e.getMessage(), e);
        } finally {
            if (driver != null) {
                driver.quit(); // Cierra el driver siempre
            }
        }
    }

    // Métodos auxiliares
    private static Map<String, String> extraerDatosAlumno(WebDriver driver) {
        Map<String, String> datos = new HashMap<>();
        System.out.println("\n=== INICIO DE EXTRACCIÓN DE DATOS ===");

        try {
            // Ejemplo: Extraer boleta (ajusta los selectores según tu HTML real)
            WebElement boletaElement = driver.findElement(By.cssSelector(".boleta"));
            String boleta = boletaElement.getText().replaceAll("[^0-9]", "");
            datos.put("boleta", boleta);
            System.out.println("[EXTRACCIÓN] Boleta obtenida: " + boleta);

            // Ejemplo: Extraer CURP (con validación básica)
            WebElement curpElement = driver.findElement(By.cssSelector(".curp"));
            String curp = curpElement.getText().trim().toUpperCase();
            if (!curp.matches("^[A-Z]{4}[0-9]{6}[A-Z]{6}[0-9A-Z]{2}$")) {
                System.err.println("CURP con formato inválido: " + curp);
            }
            datos.put("curp", curp);
            System.out.println("[OK] CURP: " + curp);

            // Agrega más extracciones según sea necesario (nombre, carrera, etc.)
            datos.put("nombre", driver.findElement(By.cssSelector(".nombre")).getText().trim());
            datos.put("carrera", driver.findElement(By.cssSelector(".carrera")).getText().trim());

        } catch (NoSuchElementException e) {
            System.err.println("[ERROR] Elemento no encontrado: " + e.getMessage());
        }

        System.out.println("=== FIN DE EXTRACCIÓN ===\n");
        return datos;
    }

    private static String extractAlumnoId(String url) {
        int index = url.indexOf("?h=");
        return (index != -1) ? url.substring(index + 3, Math.min(url.length(), index + 150)) : null;
    }

    private static String findExistingImage(String alumnoId) throws IOException {
        Path dirPath = Paths.get(IMAGE_DIR);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
            return null;
        }

        try (var stream = Files.list(dirPath)) {
            return stream
                    .filter(path -> path.getFileName().toString().startsWith("credencial_" + alumnoId))
                    .findFirst()
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)
                    .orElse(null);
        }
    }
}