package com.example.PruebaCRUD.Scraping;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ScrapingCredencial {

    private static final String IMAGE_DIR = "/app/src/main/resources/static/images/credenciales/";

    public static Map<String, String> capturarCredencial(String credencialUrl) throws IOException {
        System.out.println("Iniciando captura de credencial...");
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
        }

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--window-size=1920,1080", "--no-sandbox",
                "--disable-dev-shm-usage", "--disable-gpu", "--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);


        try {
            driver.get(credencialUrl);
            Thread.sleep(3000);

            // Extraer datos del HTML
            resultados.putAll(extraerDatosAlumno(driver));

            // Solo tomar screenshot si no existe la imagen
            if (existingImagePath == null) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                long scrollHeight = (long) js.executeScript("return document.body.scrollHeight");
                driver.manage().window().setSize(new Dimension(1920, (int) scrollHeight));

                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String imageName = "credencial_" + alumnoId + ".png";
                File imageFile = new File(IMAGE_DIR, imageName);
                Files.copy(screenshot.toPath(), imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                resultados.put("imagenPath", imageFile.getAbsolutePath());
                System.out.println("Screenshot guardado en: " + imageFile.getAbsolutePath());
            }

            return resultados;

        } catch (Exception e) {
            throw new IOException("Error al procesar la credencial: " + e.getMessage());
        } finally {
            driver.quit();
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

    private static String extractAlumnoId(String url) {
        int index = url.indexOf("?h=");
        if (index != -1) {
            return url.substring(index + 3, Math.min(url.length(), index + 150));
        }
        return null;
    }

    private static String findExistingImage(String alumnoId) {
        File dir = new File(IMAGE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
            return null;
        }

        // Buscar archivos que comiencen con "credencial_[ID]"
        String prefix = "credencial_" + alumnoId;
        File[] matchingFiles = dir.listFiles((dir1, name) -> name.startsWith(prefix));

        if (matchingFiles != null && matchingFiles.length > 0) {
            return matchingFiles[0].getAbsolutePath();
        }
        return null;
    }
}