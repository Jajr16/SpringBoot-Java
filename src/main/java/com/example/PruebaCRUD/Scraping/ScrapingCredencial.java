package com.example.PruebaCRUD.Scraping;

import io.github.bonigarcia.wdm.WebDriverManager;
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

    public static Map<String, String> capturarCredencial(String credencialUrl) throws IOException {
        System.out.println("Iniciando captura de credencial...");
        Map<String, String> resultados = new HashMap<>();

        // Extraer ID del alumno
        String alumnoId = extractAlumnoId(credencialUrl);
        if (alumnoId == null) {
            throw new IOException("No se pudo extraer un identificador válido de la URL.");
        }
        resultados.put("alumnoId", alumnoId);

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--window-size=1920,1080");
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(credencialUrl);
            Thread.sleep(3000);

            // Extraer boleta directamente del HTML
            WebElement boletaElement = driver.findElement(By.cssSelector(".boleta"));
            String boleta = boletaElement.getText().replaceAll("[^0-9]", "");
            resultados.put("boleta", boleta);
//            System.out.println("Boleta extraída del HTML: " + boleta);

            // Capturar screenshot completo
            JavascriptExecutor js = (JavascriptExecutor) driver;
            long scrollHeight = (long) js.executeScript("return document.body.scrollHeight");
            driver.manage().window().setSize(new Dimension(1920, (int) scrollHeight));

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String imageName = "credencial_" + alumnoId + "_" + System.currentTimeMillis() + ".png";
            File imageFile = new File(IMAGE_DIR, imageName);
            Files.copy(screenshot.toPath(), imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            resultados.put("imagenPath", imageFile.getAbsolutePath());
            System.out.println("Screenshot guardado en: " + imageFile.getAbsolutePath());

            return resultados;

        } catch (Exception e) {
            throw new IOException("Error al procesar la credencial: " + e.getMessage());
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
}