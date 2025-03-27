package com.example.PruebaCRUD.Scraping;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ScrapingCredencial {

    private static final String IMAGE_DIR = "./src/main/java/com/example/PruebaCRUD/Scraping/images/";
    private static final String TESSDATA_PATH = "./src/main/resources/tessdata/"; // Ruta a los datos de Tesseract

    public static String capturarCredencial(String credencialUrl) throws IOException {
        System.out.println("Iniciando captura de credencial...");
        System.out.println("URL recibida: " + credencialUrl);

        String alumnoId = extractAlumnoId(credencialUrl);
        if (alumnoId == null) {
            throw new IOException("No se pudo extraer un identificador válido de la URL.");
        }
        System.out.println("ID del alumno extraído: " + alumnoId);

        File imageDir = new File(IMAGE_DIR);
        File[] existingFiles = imageDir.listFiles((dir, name) -> name.startsWith("credencial_" + alumnoId + "_"));

        if (existingFiles != null && existingFiles.length > 0) {
            System.out.println("Ya existe una imagen para este alumno. No se guardará una nueva.");
            return existingFiles[0].getAbsolutePath();
        }

        String imageName = "credencial_" + alumnoId + "_" + System.currentTimeMillis() + ".png";
        System.out.println("Nombre del archivo generado: " + imageName);

        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Modo sin interfaz gráfica
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);
        System.out.println("Navegador iniciado en modo headless.");

        try {
            driver.get(credencialUrl);
            System.out.println("Página cargada correctamente.");
            Thread.sleep(3000); // Esperar que cargue completamente

            JavascriptExecutor js = (JavascriptExecutor) driver;
            long scrollHeight = (long) js.executeScript("return document.body.scrollHeight");
            driver.manage().window().setSize(new Dimension(1920, (int) scrollHeight));
            System.out.println("Ventana redimensionada a: 1920x" + scrollHeight);

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            System.out.println("Captura de pantalla realizada.");

            File imageFile = new File(imageDir, imageName);
            Files.copy(screenshot.toPath(), imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Imagen guardada en: " + imageFile.getAbsolutePath());

            String textoExtraido = extraerTextoDeImagen(imageFile);
            System.out.println("Texto extraído de la imagen: " + textoExtraido);

            String boletaEncontrada = buscarBoletaEnTexto(textoExtraido);
            if (boletaEncontrada != null) {
                System.out.println("Boleta encontrada: " + boletaEncontrada);
            } else {
                System.out.println("No se encontró la boleta en el texto extraído.");
            }

            return imageFile.getAbsolutePath();

        } catch (IOException | InterruptedException e) {
            System.out.println("Error al capturar la credencial: " + e.getMessage());
            throw new IOException("Error al capturar la credencial: " + e.getMessage());
        } finally {
            driver.quit();
            System.out.println("Navegador cerrado.");
        }
    }

    public static String extraerTextoDeImagen(File imagen) {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(TESSDATA_PATH); // Ruta a los datos de Tesseract
        tesseract.setLanguage("spa"); // Idioma español

        try {
            return tesseract.doOCR(imagen);
        } catch (TesseractException e) {
            System.out.println("Error al extraer texto de la imagen: " + e.getMessage());
            return null;
        }
    }

    public static String buscarBoletaEnTexto(String texto) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\b\\d{10}\\b");
        java.util.regex.Matcher matcher = pattern.matcher(texto);

        if (matcher.find()) {
            return matcher.group();
        }
        return null;
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