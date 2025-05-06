package com.example.PruebaCRUD.Scraping;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class ScrapingCredencial {
    private static final String IMAGE_DIR = "/app/src/main/resources/static/images/credenciales/"; // Ruta absoluta en el contenedor
    private static final int MAX_RETRIES = 3;
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    static {
        try {
            System.out.println("Verificando el directorio de imágenes...");
            Path dirPath = Paths.get(IMAGE_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                System.out.println("Directorio creado: " + dirPath);
            }
        } catch (Exception e) {
            System.err.println("Error en configuración inicial: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> capturarCredencial(String credencialUrl) throws IOException, InterruptedException {
        System.out.println("Iniciando captura de credencial para URL: " + credencialUrl);
        Map<String, String> resultados = new HashMap<>();

        String alumnoId = extractAlumnoId(credencialUrl);
        if (alumnoId == null) {
            throw new IOException("No se pudo extraer un identificador válido de la URL.");
        }
        resultados.put("alumnoId", alumnoId);

        String imageName = "credencial_" + alumnoId + ".png";
        String existingImagePath = findExistingImage(alumnoId);

        if (existingImagePath != null) {
            System.out.println("Usando imagen existente: " + existingImagePath);
            resultados.put("imagenPath", "/images/credenciales/" + imageName); // Ruta relativa para el frontend
            resultados.putAll(extraerDatosAlumnoConReintentos(credencialUrl));
            return resultados;
        }

        WebDriver driver = null;
        Exception lastException = null;

        for (int intento = 1; intento <= MAX_RETRIES; intento++) {
            try {
                System.out.println("Intento " + intento + " de captura de credencial");
                driver = createWebDriver();
                WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

                System.out.println("Navegando a la URL...");
                driver.get(credencialUrl);

                wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".boleta")));

                resultados.putAll(extraerDatosAlumno(driver, wait));

                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File imageFile = new File(IMAGE_DIR, imageName);
                Files.copy(screenshot.toPath(), imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                resultados.put("imagenPath", "/images/credenciales/" + imageName); // Ruta relativa para el frontend
                System.out.println("Captura exitosa. Imagen guardada en: " + imageFile.getAbsolutePath());

                return resultados;

            } catch (Exception e) {
                lastException = e;
                System.err.println("Error en intento " + intento + ": " + e.getMessage());
                e.printStackTrace();
                if (driver != null) {
                    try {
                        System.out.println("URL actual: " + driver.getCurrentUrl());
                        System.out.println("Título de la página: " + driver.getTitle());
                    } catch (Exception pageError) {
                        System.err.println("No se pudo obtener información de la página: " + pageError.getMessage());
                    }
                }
                if (intento < MAX_RETRIES) {
                    System.out.println("Esperando antes del siguiente intento...");
                    Thread.sleep(2000 * intento);
                }
            } finally {
                if (driver != null) {
                    try {
                        driver.quit();
                        System.out.println("WebDriver cerrado correctamente");
                    } catch (Exception e) {
                        System.err.println("Error al cerrar el driver: " + e.getMessage());
                    }
                }
            }
        }
        throw new IOException("Error después de " + MAX_RETRIES + " intentos", lastException);
    }

    private static WebDriver createWebDriver() {
        System.out.println("Creando nueva instancia de ChromeDriver...");
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--headless",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--window-size=1920,1080",
                "--remote-allow-origins=*"
        );
        return new ChromeDriver(options);
    }

    private static Map<String, String> extraerDatosAlumnoConReintentos(String credencialUrl) throws IOException, InterruptedException {
        WebDriver driver = null;
        Exception lastException = null;
        for (int i = 0; i < MAX_RETRIES; i++) {
            try {
                driver = createWebDriver();
                WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
                driver.get(credencialUrl);
                return extraerDatosAlumno(driver, wait);
            } catch (Exception e) {
                lastException = e;
                System.err.println("Error en extracción de datos (intento " + (i + 1) + "): " + e.getMessage());
                if (i < MAX_RETRIES - 1) {
                    Thread.sleep(2000 * (i + 1));
                }
            } finally {
                if (driver != null) {
                    driver.quit();
                }
            }
        }
        throw new IOException("Error en la extracción de datos después de " + MAX_RETRIES + " intentos", lastException);
    }

    private static Map<String, String> extraerDatosAlumno(WebDriver driver, WebDriverWait wait) {
        Map<String, String> datos = new HashMap<>();
        System.out.println("\n=== INICIO DE EXTRACCIÓN DE DATOS ===");
        try {
            WebElement boletaElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".boleta")));
            String boleta = boletaElement.getText().replaceAll("[^0-9]", "");
            datos.put("boleta", boleta);
            System.out.println("[OK] Boleta: " + boleta);

            WebElement curpElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".curp")));
            String curp = curpElement.getText().trim().toUpperCase();
            if (!curp.matches("^[A-Z]{4}[0-9]{6}[A-Z]{6}[0-9A-Z]{2}$")) {
                System.err.println("[ADVERTENCIA] CURP con formato inválido: " + curp);
            }

            datos.put("curp", curp);
            System.out.println("[OK] CURP: " + curp);

            datos.put("nombre", wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".nombre"))).getText().trim());
            datos.put("carrera", wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".carrera"))).getText().trim());
            datos.put("escuela", wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".escuela"))).getText().trim());

            System.out.println("[OK] Datos extraídos correctamente");
        } catch (TimeoutException e) {
            System.err.println("[ERROR] Timeout esperando elementos: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("[ERROR] Error inesperado en extracción: " + e.getMessage());
            throw e;
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
        String prefix = "credencial_" + alumnoId;
        File[] matchingFiles = dir.listFiles((dir1, name) -> name.startsWith(prefix) && name.endsWith(".png"));
        if (matchingFiles != null && matchingFiles.length > 0) {
            return matchingFiles[0].getName(); // Solo el nombre para usar con /images/...
        }
        return null;
    }
}