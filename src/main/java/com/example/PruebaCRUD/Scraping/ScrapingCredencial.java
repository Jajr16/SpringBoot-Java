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

    // Ruta base persistente en Azure App Service (dentro del contenedor)
    private static final String PERSISTENT_STORAGE_BASE = "/home/site/wwwroot/";
    // Subdirectorio específico para las imágenes de credenciales dentro de la carpeta de imágenes
    private static final String CREDENTIALS_IMAGE_SUBDIR = "images/credenciales/";
    // Directorio completo donde se guardarán las imágenes de credenciales
    private static final String FULL_IMAGE_STORAGE_DIR = PERSISTENT_STORAGE_BASE + CREDENTIALS_IMAGE_SUBDIR; // Ej: "/home/site/wwwroot/images/credenciales/"

    private static final String FRONTEND_IMAGE_PATH_PREFIX = "/images/credenciales/";


    public static Map<String, String> capturarCredencial(String credencialUrl) throws IOException {
        System.out.println("Iniciando captura de credencial para URL: " + credencialUrl);
        Map<String, String> resultados = new HashMap<>();

        // Extraer ID del alumno
        String alumnoId = extractAlumnoId(credencialUrl);
        if (alumnoId == null || alumnoId.trim().isEmpty()) {
            System.err.println("No se pudo extraer un identificador de alumno válido de la URL: " + credencialUrl);
            throw new IOException("No se pudo extraer un identificador válido de la URL.");
        }
        // Limpiar el ID para usarlo en nombres de archivo (ej. reemplazar caracteres no válidos)
        String safeAlumnoId = alumnoId.replaceAll("[^a-zA-Z0-9_.-]", "_");
        resultados.put("alumnoId", safeAlumnoId);
        System.out.println("Alumno ID extraído y sanitizado: " + safeAlumnoId);

        // Crear directorio de imágenes si no existe
        File imageDirFile = new File(FULL_IMAGE_STORAGE_DIR);
        if (!imageDirFile.exists()) {
            if (imageDirFile.mkdirs()) {
                System.out.println("Directorio de almacenamiento de imágenes creado en: " + imageDirFile.getAbsolutePath());
            } else {
                System.err.println("Error: No se pudo crear el directorio de almacenamiento de imágenes en: " + imageDirFile.getAbsolutePath());
                throw new IOException("No se pudo crear el directorio para guardar imágenes.");
            }
        }

        // Nombre del archivo de imagen
        String imageName = "credencial_" + safeAlumnoId + ".png";
        Path imagePath = Paths.get(FULL_IMAGE_STORAGE_DIR, imageName);

        // Verificar si ya existe una imagen para este ID
        if (Files.exists(imagePath)) {
            System.out.println("Ya existe una imagen para este ID: " + imagePath.toString());
            resultados.put("imagenPath", FRONTEND_IMAGE_PATH_PREFIX + imageName);
            // Opcionalmente, podrías extraer datos del HTML aquí también si necesitas actualizarlos,
            // pero por ahora, si la imagen existe, asumimos que es suficiente.
            return resultados;
        }

        System.out.println("Configurando opciones de Chrome...");
        ChromeOptions options = new ChromeOptions();
        // Usar el nuevo modo headless recomendado
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox"); // Esencial para contenedores
        options.addArguments("--disable-dev-shm-usage"); // Importante para contenedores
        options.addArguments("--disable-gpu"); // Generalmente bueno para headless
        options.addArguments("--remote-allow-origins=*"); // Para permitir conexiones remotas si es necesario

        // Opcional: especificar un directorio de datos de usuario escribible si Chrome lo necesita
        options.addArguments("--user-data-dir=" + PERSISTENT_STORAGE_BASE + "chrome_data"); // ej. /home/site/wwwroot/chrome_data

        // Asegurarse que la variable de entorno CHROME_BIN está configurada en el Dockerfile
        String chromeBinary = System.getenv("CHROME_BIN");
        if (chromeBinary != null && !chromeBinary.isEmpty()) {
            options.setBinary(chromeBinary);
            System.out.println("Usando binario de Chrome desde CHROME_BIN: " + chromeBinary);
        } else {
            System.err.println("Advertencia: La variable de entorno CHROME_BIN no está configurada. Selenium intentará encontrar Chrome en el PATH.");
        }

        WebDriver driver = null;
        try {
            System.out.println("Instanciando ChromeDriver...");
            driver = new ChromeDriver(options);
            System.out.println("ChromeDriver instanciado correctamente.");

            System.out.println("Navegando a la URL: " + credencialUrl);
            driver.get(credencialUrl);

            // Espera explícita para que la página cargue y un elemento clave sea visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Aumentar timeout si es necesario
            try {

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".boleta")));
                System.out.println("Página cargada y elemento clave (.boleta) visible.");
            } catch (TimeoutException e) {
                System.err.println("Timeout esperando el elemento clave de la página. La página podría no haberse cargado completamente o el elemento no existe.");

                throw new IOException("La página de credencial no cargó el contenido esperado a tiempo.", e);
            }

            // Extraer datos del HTML
            System.out.println("Extrayendo datos del alumno...");
            resultados.putAll(extraerDatosAlumno(driver));

            // Tomar screenshot
            System.out.println("Tomando screenshot de la página...");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // Ajustar el tamaño de la ventana para capturar toda la página si es necesario
            // Esto a veces es más fiable después de que todo el contenido dinámico ha cargado.
            long scrollHeight = (long) js.executeScript("return document.body.scrollHeight");
            driver.manage().window().setSize(new Dimension(1920, (int) scrollHeight));
            System.out.println("Ventana redimensionada a 1920x" + scrollHeight + " para screenshot completo.");

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            resultados.put("imagenPath", FRONTEND_IMAGE_PATH_PREFIX + imageName);
            System.out.println("Screenshot guardado en: " + imagePath.toString());

            return resultados;

        } catch (Exception e) {
            System.err.println("Error durante el proceso de scraping: " + e.getMessage());
            // e.printStackTrace(); // Útil para depuración más detallada en logs
            throw new IOException("Error al procesar la credencial: " + e.getMessage(), e);
        } finally {
            if (driver != null) {
                System.out.println("Cerrando el driver de Selenium...");
                driver.quit();
                System.out.println("Driver de Selenium cerrado.");
            }
        }
    }

    private static Map<String, String> extraerDatosAlumno(WebDriver driver) {
        Map<String, String> datos = new HashMap<>();
        System.out.println("\n=== INICIO DE EXTRACCIÓN DE DATOS ===");
        try {
            WebElement boletaElement = driver.findElement(By.cssSelector(".boleta"));
            String boleta = boletaElement.getText().replaceAll("[^0-9]", "");
            datos.put("boleta", boleta);
            System.out.println("[EXTRACCIÓN] Boleta obtenida: " + boleta);

            WebElement curpElement = driver.findElement(By.cssSelector(".curp"));
            String curp = curpElement.getText().trim().toUpperCase();
            if (!curp.matches("^[A-Z]{4}[0-9]{6}[A-Z]{6}[0-9A-Z]{2}$")) {
                System.err.println("[ADVERTENCIA] CURP con formato potencialmente inválido: " + curp + " (Se guardará de todas formas)");
            }
            datos.put("curp", curp);
            System.out.println("[OK] CURP: " + curp);

            WebElement nombreElement = driver.findElement(By.cssSelector(".nombre"));
            String nombre = nombreElement.getText().trim();
            datos.put("nombre", nombre);
            System.out.println("[EXTRACCIÓN] Nombre obtenido: " + nombre);

            WebElement carreraElement = driver.findElement(By.cssSelector(".carrera"));
            String carrera = carreraElement.getText().trim();
            datos.put("carrera", carrera);
            System.out.println("[EXTRACCIÓN] Carrera obtenida: " + carrera);

            WebElement escuelaElement = driver.findElement(By.cssSelector(".escuela"));
            String escuela = escuelaElement.getText().trim();
            datos.put("escuela", escuela);
            System.out.println("[EXTRACCIÓN] Escuela obtenida: " + escuela);

        } catch (NoSuchElementException e) {
            System.err.println("[ERROR DE EXTRACCIÓN] Elemento no encontrado: " + e.getMessage() + ". Es posible que la estructura de la página haya cambiado o el elemento no esté presente.");
            // Dependiendo de la criticidad, podrías querer lanzar una excepción aquí o simplemente devolver datos parciales.
        }
        System.out.println("=== FIN DE EXTRACCIÓN DE DATOS ===\n");
        return datos;
    }

    private static String extractAlumnoId(String url) {
        if (url == null || !url.contains("?h=")) {
            return null;
        }
        int index = url.indexOf("?h=");

        String potentialId = url.substring(index + 3);
        if (potentialId.length() > 100) { // Ejemplo: si el ID no debería ser tan largo
            // potentialId = potentialId.substring(0, 100); // Truncar si es necesario, o validar
        }
        return potentialId; // Devuelve la parte después de ?h=
    }
}