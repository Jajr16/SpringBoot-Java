package com.example.PruebaCRUD.Scraping;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ScrapingCredencial {

    // Configuración de rutas
    public static final String PERSISTENT_STORAGE_BASE = "/app/";  // Cambiado de /home/site/wwwroot/
    public static final String CREDENTIALS_IMAGE_SUBDIR = "images/credenciales/";
    public static final String FULL_IMAGE_STORAGE_DIR = PERSISTENT_STORAGE_BASE + CREDENTIALS_IMAGE_SUBDIR;
    public static final String FRONTEND_IMAGE_PATH_PREFIX = "/images/credenciales/";

    // Configuración de Playwright (ahorrar memoria)
    private static final int VIEWPORT_WIDTH = 1200;
    private static final int VIEWPORT_HEIGHT = 800;
    private static final int NAVIGATION_TIMEOUT_MS = 30000;
    private static final int SELECTOR_TIMEOUT_MS = 15000;

    public static Map<String, String> capturarCredencial(String credencialUrl) throws IOException {
        System.out.println("Iniciando captura de credencial para URL: " + credencialUrl);
        Map<String, String> resultados = new HashMap<>();

        // Validación y extracción de ID
        String alumnoId = extractAlumnoId(credencialUrl);
        if (alumnoId == null || alumnoId.trim().isEmpty()) {
            throw new IOException("No se pudo extraer un identificador válido de la URL: " + credencialUrl);
        }

        String safeAlumnoId = alumnoId.replaceAll("[^a-zA-Z0-9_.-]", "_");
        resultados.put("alumnoId", safeAlumnoId);
        System.out.println("Alumno ID procesado: " + safeAlumnoId);

        // Preparar directorio de imágenes
        Path imageDirPath = prepareImageDirectory();
        String imageName = "credencial_" + safeAlumnoId + ".png";
        Path imagePath = imageDirPath.resolve(imageName);

        try (Playwright playwright = Playwright.create()) {
            Browser browser = launchBrowser(playwright);
            BrowserContext context = createBrowserContext(browser);
            Page page = context.newPage();

            try {
                navigateToCredentialPage(page, credencialUrl);
                resultados.putAll(extraerDatosAlumno(page));

                if (!Files.exists(imagePath)) {
                    captureScreenshot(page, imagePath);
                } else {
                    System.out.println("Imagen existente, omitiendo captura: " + imagePath);
                }

                resultados.put("imagenPath", FRONTEND_IMAGE_PATH_PREFIX + imageName);
                return resultados;

            } finally {
                closeResources(page, context, browser);
            }
        } catch (PlaywrightException e) {
            throw new IOException("Error en Playwright: " + e.getMessage(), e);
        }
    }

    private static Path prepareImageDirectory() throws IOException {
        Path imageDirPath = Paths.get(FULL_IMAGE_STORAGE_DIR);
        if (!Files.exists(imageDirPath)) {
            Files.createDirectories(imageDirPath);
            System.out.println("Directorio creado: " + imageDirPath.toAbsolutePath());
        }
        return imageDirPath;
    }

    private static Browser launchBrowser(Playwright playwright) {
        // Configuración robusta para Docker
        return playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(true)
                .setArgs(Arrays.asList(
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--disable-gpu",
                        "--single-process",
                        "--disable-software-rasterizer",
                        "--disable-setuid-sandbox"
                )));
    }

    private static BrowserContext createBrowserContext(Browser browser) {
        return browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)
                .setJavaScriptEnabled(true));
    }

    private static void navigateToCredentialPage(Page page, String url) {
        page.navigate(url, new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.NETWORKIDLE)
                .setTimeout(NAVIGATION_TIMEOUT_MS));

        page.waitForSelector(".boleta", new Page.WaitForSelectorOptions()
                .setTimeout(SELECTOR_TIMEOUT_MS));
    }

    private static void captureScreenshot(Page page, Path imagePath) throws IOException {
        byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions()
                .setPath(imagePath)
                .setFullPage(false)
                .setClip(0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT));

        Files.write(imagePath, screenshotBytes);
        System.out.println("Captura guardada: " + imagePath.toAbsolutePath());
    }

    private static void closeResources(Page page, BrowserContext context, Browser browser) {
        try {
            if (page != null) page.close();
        } catch (Exception e) {
            System.err.println("Error al cerrar página: " + e.getMessage());
        }
        try {
            if (context != null) context.close();
        } catch (Exception e) {
            System.err.println("Error al cerrar contexto: " + e.getMessage());
        }
        try {
            if (browser != null) browser.close();
        } catch (Exception e) {
            System.err.println("Error al cerrar navegador: " + e.getMessage());
        }
    }

    private static Map<String, String> extraerDatosAlumno(Page page) {
        Map<String, String> datos = new HashMap<>();

        datos.put("boleta", getSafeTextContent(page, ".boleta", "[^0-9]"));
        datos.put("curp", getSafeTextContent(page, ".curp", "").trim().toUpperCase());
        datos.put("nombre", getSafeTextContent(page, ".nombre", "").trim());
        datos.put("carrera", getSafeTextContent(page, ".carrera", "").trim());
        datos.put("escuela", getSafeTextContent(page, ".escuela", "").trim());

        // Validación básica de CURP
        if (!datos.get("curp").matches("^[A-Z]{4}[0-9]{6}[A-Z]{6}[0-9A-Z]{2}$")) {
            System.err.println("Advertencia: CURP con formato potencialmente inválido");
        }

        return datos;
    }

    private static String getSafeTextContent(Page page, String selector, String regexToRemove) {
        try {
            String content = page.textContent(selector);
            return regexToRemove.isEmpty() ? content : content.replaceAll(regexToRemove, "");
        } catch (Exception e) {
            System.err.println("Error obteniendo " + selector + ": " + e.getMessage());
            return "";
        }
    }

    private static String extractAlumnoId(String url) {
        if (url == null || !url.contains("?h=")) return null;
        return url.substring(url.indexOf("?h=") + 3);
    }
}