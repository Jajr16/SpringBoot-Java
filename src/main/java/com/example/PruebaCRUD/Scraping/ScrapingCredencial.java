package com.example.PruebaCRUD.Scraping;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class ScrapingCredencial {
    private static final String IMAGE_DIR = "./src/main/java/com/example/PruebaCRUD/Scraping/images/";
    private static WebDriver driver = null;

    static {
        File dir = new File(IMAGE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private static synchronized WebDriver getDriver() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments(
                    "--headless",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu",
                    "--remote-allow-origins=*",
                    "--disable-extensions",
                    "--window-size=1024,768"
            );
            options.setBinary("/usr/bin/chromium");
            driver = new ChromeDriver(options);
        }
        return driver;
    }

    public static Map<String, String> capturarCredencial(String credencialUrl) throws IOException {
        System.out.println("Iniciando captura de credencial...");
        Map<String, String> resultados = new HashMap<>();

        String alumnoId = extractAlumnoId(credencialUrl);
        if (alumnoId == null) {
            throw new IOException("No se pudo extraer un identificador válido de la URL.");
        }
        resultados.put("alumnoId", alumnoId);

        try {
            WebDriver driver = getDriver();
            driver.get(credencialUrl);
            Thread.sleep(1000); // Reducido a 1 segundo

            // Extraer datos
            resultados.putAll(extraerDatosAlumno(driver));

            // Capturar screenshot
            String imagePath = captureScreenshot(driver, alumnoId);
            resultados.put("imagenPath", imagePath);

            return resultados;

        } catch (Exception e) {
            throw new IOException("Error al procesar la credencial: " + e.getMessage());
        }
    }

    private static Map<String, String> extraerDatosAlumno(WebDriver driver) {
        Map<String, String> datos = new HashMap<>();
        System.out.println("\n=== INICIO DE EXTRACCIÓN DE DATOS ===");

        try {
            // Boleta
            WebElement boletaElement = driver.findElement(By.cssSelector(".boleta"));
            String boleta = boletaElement.getText().replaceAll("[^0-9]", "");
            datos.put("boleta", boleta);
            System.out.println("[EXTRACCIÓN] Boleta obtenida: " + boleta);

            // CURP
            WebElement curpElement = driver.findElement(By.cssSelector(".curp"));
            String curp = curpElement.getText().trim().toUpperCase();
            if (!curp.matches("^[A-Z]{4}[0-9]{6}[A-Z]{6}[0-9A-Z]{2}$")) {
                System.err.println("CURP con formato inválido: " + curp);
            }
            datos.put("curp", curp);
            System.out.println("[OK] CURP: " + curp);

            // Nombre
            WebElement nombreElement = driver.findElement(By.cssSelector(".nombre"));
            String nombre = nombreElement.getText().trim();
            datos.put("nombre", nombre);
            System.out.println("[EXTRACCIÓN] Nombre obtenido: " + nombre);

            // Carrera
            WebElement carreraElement = driver.findElement(By.cssSelector(".carrera"));
            String carrera = carreraElement.getText().trim();
            datos.put("carrera", carrera);
            System.out.println("[EXTRACCIÓN] Carrera obtenida: " + carrera);

            // Escuela
            WebElement escuelaElement = driver.findElement(By.cssSelector(".escuela"));
            String escuela = escuelaElement.getText().trim();
            datos.put("escuela", escuela);
            System.out.println("[EXTRACCIÓN] Escuela obtenida: " + escuela);

        } catch (NoSuchElementException e) {
            System.err.println("[ERROR] Elemento no encontrado: " + e.getMessage());
        }

        System.out.println("=== FIN DE EXTRACCIÓN ===\n");
        return datos;
    }

    private static String captureScreenshot(WebDriver driver, String alumnoId) throws IOException {
        // Optimizar la página para la captura
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.overflow='hidden'");

        // Tomar screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String imageName = "credencial_" + alumnoId + ".png";
        File imageFile = new File(IMAGE_DIR, imageName);
        Files.copy(screenshot.toPath(), imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return imageFile.getAbsolutePath();
    }

    private static String extractAlumnoId(String url) {
        int index = url.indexOf("?h=");
        if (index != -1) {
            return url.substring(index + 3, Math.min(url.length(), index + 150));
        }
        return null;
    }
}