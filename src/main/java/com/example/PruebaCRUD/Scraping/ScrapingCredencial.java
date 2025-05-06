package com.example.PruebaCRUD.Scraping;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class ScrapingCredencial {

    private static String BASE_DIR = System.getProperty("user.home") + "/pruebacrud";
    private static String IMAGE_DIR = BASE_DIR + "/images/";
    private static final long UN_MES_EN_MILLIS = 30L * 24 * 60 * 60 * 1000;

    static {
        // Configurar WebDriverManager al cargar la clase
        WebDriverManager.chromedriver().setup();
        crearDirectoriosSiNoExisten();
    }

    public static Map<String, String> capturarCredencial(String credencialUrl) throws IOException {
        System.out.println("Iniciando captura de credencial...");
        Map<String, String> resultados = new HashMap<>();

        // Extraer ID del alumno
        String alumnoId = extractAlumnoId(credencialUrl);
        if (alumnoId == null) {
            throw new IOException("No se pudo extraer un identificador válido de la URL.");
        }
        resultados.put("alumnoId", alumnoId);

        // Verificar si ya existe una imagen para este ID y si no ha expirado
        String existingImagePath = findExistingImage(alumnoId);
        if (existingImagePath != null && !isCacheExpired(new File(existingImagePath))) {
            System.out.println("Usando imagen en caché (menos de un mes de antigüedad): " + existingImagePath);
            resultados.put("imagenPath", existingImagePath);

            // Aún así extraemos los datos por si han cambiado
            resultados.putAll(extraerDatosAlumno(credencialUrl));
            return resultados;
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-setuid-sandbox");
        options.addArguments("--disable-infobars");

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        } else {
            options.setBinary("/usr/bin/chromium");
        }

        System.setProperty("webdriver.chrome.whitelistedIps", "");
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(credencialUrl);
            Thread.sleep(3000);

            // Extraer datos del HTML
            resultados.putAll(extraerDatosAlumno(driver));

            // Tomar screenshot
            JavascriptExecutor js = (JavascriptExecutor) driver;
            long scrollHeight = (long) js.executeScript("return document.body.scrollHeight");
            driver.manage().window().setSize(new Dimension(1920, (int) scrollHeight));

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String imageName = "credencial_" + alumnoId + ".png";
            File imageFile = new File(IMAGE_DIR, imageName);
            Files.copy(screenshot.toPath(), imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            resultados.put("imagenPath", imageFile.getAbsolutePath());
            System.out.println("Nueva imagen de credencial guardada en: " + imageFile.getAbsolutePath());

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

    private static String findExistingImage(String alumnoId) {
        File dir = new File(IMAGE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
            return null;
        }

        // Buscar archivos que comiencen con "credencial_[ID]"
        String prefix = "credencial_" + alumnoId;
        File[] matchingFiles = dir.listFiles((dir1, name) -> name.startsWith(prefix) && name.endsWith(".png"));

        if (matchingFiles != null && matchingFiles.length > 0) {
            return matchingFiles[0].getAbsolutePath();
        }
        return null;
    }

    private static boolean isCacheExpired(File file) {
        long tiempoActual = System.currentTimeMillis();
        long tiempoArchivo = file.lastModified();
        return (tiempoActual - tiempoArchivo) > UN_MES_EN_MILLIS;
    }

    private static void crearDirectoriosSiNoExisten() {
        File baseDir = new File(BASE_DIR);
        File imageDir = new File(IMAGE_DIR);

        try {
            if (!baseDir.exists()) baseDir.mkdirs();
            if (!imageDir.exists()) imageDir.mkdirs();
        } catch (Exception e) {
            // Si falla, usar directorio temporal
            System.err.println("No se pudo crear directorio en ubicación principal, usando temporal: " + e.getMessage());
            String tmpDir = System.getProperty("java.io.tmpdir") + "/pruebacrud";
            File tmpImageDir = new File(tmpDir + "/images");
            tmpImageDir.mkdirs();
            IMAGE_DIR = tmpImageDir.getAbsolutePath() + "/";
            BASE_DIR = tmpDir;
        }
    }
}