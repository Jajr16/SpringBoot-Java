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

public class ScrapingCredencial {

    private static final String IMAGE_DIR = "/app/src/main/resources/static/images/credenciales/";
    private static final int MAX_RETRIES = 3;
    private static String chromeDriverPath;

    static {
        try {
            // Configurar WebDriver y obtener la ruta
            WebDriverManager.chromedriver().setup();
            chromeDriverPath = WebDriverManager.chromedriver().getDownloadedDriverPath();
            System.out.println("ChromeDriver path: " + chromeDriverPath);

            // Crear directorio si no existe
            Path dirPath = Paths.get(IMAGE_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                System.out.println("Directorio creado: " + dirPath);
            }
        } catch (Exception e) {
            System.err.println("Error en configuración inicial: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> capturarCredencial(String credencialUrl) throws IOException {
        System.out.println("Iniciando captura de credencial...");
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
            resultados.put("imagenPath", "/images/credenciales/" + imageName);
            resultados.putAll(extraerDatosAlumnoConReintentos(credencialUrl));
            return resultados;
        }

        WebDriver driver = null;
        Exception lastException = null;

        for (int intento = 1; intento <= MAX_RETRIES; intento++) {
            try {
                driver = createWebDriver();
                driver.get(credencialUrl);
                Thread.sleep(5000);

                resultados.putAll(extraerDatosAlumno(driver));

                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File imageFile = new File(IMAGE_DIR, imageName);
                Files.copy(screenshot.toPath(), imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                resultados.put("imagenPath", "/images/credenciales/" + imageName);
                System.out.println("Nueva imagen guardada en: " + imageFile.getAbsolutePath());

                return resultados;

            } catch (Exception e) {
                lastException = e;
                System.err.println("Intento " + intento + " fallido: " + e.getMessage());
                if (intento < MAX_RETRIES) {
                    try {
                        Thread.sleep(2000 * intento);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            } finally {
                if (driver != null) {
                    try {
                        driver.quit();
                    } catch (Exception e) {
                        System.err.println("Error al cerrar el driver: " + e.getMessage());
                    }
                }
            }
        }

        throw new IOException("Error después de " + MAX_RETRIES + " intentos", lastException);
    }

    private static WebDriver createWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--headless=new",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--window-size=1920,1080",
                "--remote-allow-origins=*",
                "--disable-extensions"
        );

        // Usar la ruta del ChromeDriver descargado por WebDriverManager
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        return new ChromeDriver(options);
    }

    private static Map<String, String> extraerDatosAlumnoConReintentos(String credencialUrl) throws IOException {
        WebDriver driver = null;
        Exception lastException = null;

        for (int i = 0; i < MAX_RETRIES; i++) {
            try {
                driver = createWebDriver();
                driver.get(credencialUrl);
                return extraerDatosAlumno(driver);
            } catch (Exception e) {
                lastException = e;
                try {
                    Thread.sleep(2000 * (i + 1));
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } finally {
                if (driver != null) {
                    try {
                        driver.quit();
                    } catch (Exception e) {
                        System.err.println("Error al cerrar el driver: " + e.getMessage());
                    }
                }
            }
        }

        throw new IOException("Error en la extracción de datos", lastException);
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
            return matchingFiles[0].getName();
        }
        return null;
    }
}