package com.example.PruebaCRUD.Scraping;

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

    private static final String IMAGE_DIR = "/app/images/";

    public static Map<String, String> capturarCredencial(String credencialUrl) throws IOException {
        System.out.println("[Scraping] Iniciando captura de credencial...");
        System.out.println("[Scraping] URL recibida: " + credencialUrl);
        Map<String, String> resultados = new HashMap<>();

        // Extraer ID del alumno
        String alumnoId = extractAlumnoId(credencialUrl);
        if (alumnoId == null) {
            System.err.println("[Scraping] Error: No se pudo extraer ID de alumno de la URL");
            throw new IOException("No se pudo extraer un identificador válido de la URL.");
        }
        System.out.println("[Scraping] ID de alumno extraído: " + alumnoId);
        resultados.put("alumnoId", alumnoId);

        // Verificar si ya existe una imagen
        String existingImagePath = findExistingImage(alumnoId);
        if (existingImagePath != null) {
            System.out.println("[Scraping] Imagen existente encontrada: " + existingImagePath);
            resultados.put("imagenPath", existingImagePath);
            return resultados;
        }

        // Configurar ChromeOptions con logs detallados
        System.out.println("[Scraping] Configurando ChromeOptions...");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-gpu");
        options.setBinary("/usr/bin/google-chrome-stable");

        // Log de la configuración
        System.out.println("[Scraping] Configuración de Chrome:");
        System.out.println("  - Headless: true");
        // System.out.println("  - Binary: " + options.getBinary()); // Comentado o eliminado
        // System.out.println("  - Args: " + options.getArguments()); // Comentado o eliminado
        System.out.println("  - Binary configurado.");
        System.out.println("  - Argumentos configurados: --headless=new, --no-sandbox, --disable-dev-shm-usage, --remote-allow-origins=*, --disable-gpu");

        WebDriver driver = null;
        try {
            System.out.println("[Scraping] Inicializando ChromeDriver...");
            driver = new ChromeDriver(options);
            System.out.println("[Scraping] ChromeDriver iniciado correctamente");

            System.out.println("[Scraping] Navegando a URL: " + credencialUrl);
            driver.get(credencialUrl);
            System.out.println("[Scraping] Página cargada, esperando 3 segundos...");
            Thread.sleep(3000);

            // Extraer datos del HTML
            System.out.println("[Scraping] Extrayendo datos del alumno...");
            resultados.putAll(extraerDatosAlumno(driver));

            // Tomar screenshot
            System.out.println("[Scraping] Preparando para tomar screenshot...");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            long scrollHeight = (long) js.executeScript("return document.body.scrollHeight");
            System.out.println("[Scraping] Altura de la página: " + scrollHeight + "px");

            driver.manage().window().setSize(new Dimension(1920, (int) scrollHeight));
            System.out.println("[Scraping] Tamaño de ventana configurado");

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String imageName = "credencial_" + alumnoId + ".png";
            Path imageDirPath = Paths.get(IMAGE_DIR);

            if (!Files.exists(imageDirPath)) {
                System.out.println("[Scraping] Creando directorio para imágenes...");
                Files.createDirectories(imageDirPath);
            }

            File imageFile = new File(IMAGE_DIR, imageName);
            System.out.println("[Scraping] Guardando screenshot en: " + imageFile.getAbsolutePath());
            Files.copy(screenshot.toPath(), imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            resultados.put("imagenPath", imageFile.getAbsolutePath());
            System.out.println("[Scraping] Screenshot guardado exitosamente");

            return resultados;

        } catch (SessionNotCreatedException e) {
            System.err.println("[Scraping] ERROR: No se pudo crear sesión de ChromeDriver");
            System.err.println("  - Mensaje: " + e.getMessage());
            if (e.getMessage().contains("This version of ChromeDriver")) {
                System.err.println("  - Posible causa: Versión incompatible entre Chrome y ChromeDriver");
            }
            throw new IOException("Error al iniciar ChromeDriver: " + e.getMessage());
        } catch (WebDriverException e) {
            System.err.println("[Scraping] ERROR de WebDriver");
            System.err.println("  - Tipo: " + e.getClass().getName());
            System.err.println("  - Mensaje: " + e.getMessage());
            throw new IOException("Error de WebDriver: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[Scraping] ERROR inesperado");
            e.printStackTrace();
            throw new IOException("Error al procesar la credencial: " + e.getMessage());
        } finally {
            if (driver != null) {
                System.out.println("[Scraping] Cerrando ChromeDriver...");
                driver.quit();
            }
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
                System.err.println("[EXTRACCIÓN] ADVERTENCIA: CURP con formato inválido: " + curp);
            }
            datos.put("curp", curp);
            System.out.println("[EXTRACCIÓN] CURP obtenida: " + curp);

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
            System.err.println("[EXTRACCIÓN] ERROR: Elemento no encontrado - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[EXTRACCIÓN] ERROR inesperado: " + e.getMessage());
        }

        System.out.println("=== FIN DE EXTRACCIÓN ===\n");
        return datos;
    }

    private static String extractAlumnoId(String url) {
        System.out.println("[Scraping] Extrayendo ID de alumno de URL");
        int index = url.indexOf("?h=");
        if (index != -1) {
            String id = url.substring(index + 3, Math.min(url.length(), index + 150));
            System.out.println("[Scraping] ID extraído: " + id);
            return id;
        }
        System.err.println("[Scraping] No se encontró parámetro 'h' en la URL");
        return null;
    }

    private static String findExistingImage(String alumnoId) {
        System.out.println("[Scraping] Buscando imagen existente para ID: " + alumnoId);
        File dir = new File(IMAGE_DIR);
        if (!dir.exists()) {
            System.out.println("[Scraping] Directorio de imágenes no existe: " + IMAGE_DIR);
            return null;
        }

        String prefix = "credencial_" + alumnoId;
        File[] matchingFiles = dir.listFiles((dir1, name) -> name.startsWith(prefix));

        if (matchingFiles != null && matchingFiles.length > 0) {
            System.out.println("[Scraping] Imagen encontrada: " + matchingFiles[0].getAbsolutePath());
            return matchingFiles[0].getAbsolutePath();
        }
        System.out.println("[Scraping] No se encontraron imágenes para el ID");
        return null;
    }
}