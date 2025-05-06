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

    private static final String IMAGE_DIR = "src/main/resources/static/images/credenciales/";
    private static final long UN_MES_EN_MILLIS = 30L * 24 * 60 * 60 * 1000;

    static {
        WebDriverManager.chromedriver().setup();
        crearDirectoriosSiNoExisten();
    }

    public static Map<String, String> capturarCredencial(String credencialUrl) throws IOException {
        System.out.println("Iniciando captura de credencial...");
        Map<String, String> resultados = new HashMap<>();

        String alumnoId = extractAlumnoId(credencialUrl);
        if (alumnoId == null) {
            throw new IOException("No se pudo extraer un identificador válido de la URL.");
        }
        resultados.put("alumnoId", alumnoId);

        // Verificar imagen existente
        String imageName = "credencial_" + alumnoId + ".png";
        Path imagePath = Paths.get(IMAGE_DIR, imageName);

        if (Files.exists(imagePath) && !isCacheExpired(imagePath.toFile())) {
            System.out.println("Usando imagen en caché: " + imagePath);
            // Guardar ruta relativa para acceso web
            resultados.put("imagenPath", "/images/credenciales/" + imageName);
            resultados.putAll(extraerDatosAlumno(credencialUrl));
            return resultados;
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--headless",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--remote-allow-origins=*",
                "--disable-gpu",
                "--window-size=1920,1080"
        );

        // Configuración específica del sistema operativo
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            System.out.println("Usando Chrome en Windows");
            options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        } else {
            System.out.println("Usando Chrome en Linux");
            options.setBinary("/usr/bin/chromium");
        }

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(credencialUrl);
            Thread.sleep(3000);

            resultados.putAll(extraerDatosAlumno(driver));

            // Tomar screenshot
            JavascriptExecutor js = (JavascriptExecutor) driver;
            long scrollHeight = (long) js.executeScript("return document.body.scrollHeight");
            driver.manage().window().setSize(new Dimension(1920, (int) scrollHeight));

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            // Guardar ruta relativa para acceso web
            resultados.put("imagenPath", "/images/credenciales/" + imageName);
            System.out.println("Nueva imagen guardada en: " + imagePath);

            return resultados;

        } catch (Exception e) {
            throw new IOException("Error al procesar la credencial: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

    private static void crearDirectoriosSiNoExisten() {
        try {
            Path dirPath = Paths.get(IMAGE_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                System.out.println("Directorio creado: " + dirPath);
            }
        } catch (IOException e) {
            System.err.println("Error al crear directorio de imágenes: " + e.getMessage());
            throw new RuntimeException("No se pudo crear el directorio de imágenes", e);
        }
    }

    // El resto de los métodos permanecen igual, excepto findExistingImage que necesita ser actualizado:
    private static String findExistingImage(String alumnoId) {
        Path imagePath = Paths.get(IMAGE_DIR, "credencial_" + alumnoId + ".png");
        if (Files.exists(imagePath)) {
            return imagePath.toString();
        }
        return null;
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

    private static Map<String, String> extraerDatosAlumno(String credencialUrl) throws IOException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        try {
            driver.get(credencialUrl);
            return extraerDatosAlumno(driver);
        } finally {
            driver.quit();
        }
    }

    private static String extractAlumnoId(String url) {
        int index = url.indexOf("?h=");
        if (index != -1) {
            return url.substring(index + 3, Math.min(url.length(), index + 150));
        }
        return null;
    }
    private static boolean isCacheExpired(File file) {
        long tiempoActual = System.currentTimeMillis();
        long tiempoArchivo = file.lastModified();
        return (tiempoActual - tiempoArchivo) > UN_MES_EN_MILLIS;
    }
}