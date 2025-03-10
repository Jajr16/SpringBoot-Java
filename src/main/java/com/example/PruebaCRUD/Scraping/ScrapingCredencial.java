package com.example.PruebaCRUD.Scraping;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScrapingCredencial {

    private static final String IMAGE_DIR = "./src/main/java/com/example/PruebaCRUD/Scraping/images/";
    private static final String IMAGE_NAME = "screenshot.png";
    private static final String TARGET_URL = "https://www.ubereats.com/mx-en?ps=1";

    public static void main(String[] args) {
        try {
            // Configuración del WebDriver de Selenium
            System.setProperty("webdriver.chrome.driver", "/ruta/al/chromedriver"); // Asegúrate de cambiar la ruta

            File imageDir = new File(IMAGE_DIR);
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }

            // Crear una instancia del WebDriver
            WebDriver driver = new ChromeDriver();
            driver.get(TARGET_URL);  // Abrir la URL

            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            driver.quit();  // Cerrar el WebDriver

            File imageFile = new File(imageDir, IMAGE_NAME);
            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                fos.write(screenshot);
            }

            System.out.println("Screenshot guardado en: " + imageFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
