package com.example.PruebaCRUD.Scraping;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ScrapingCredencial {

    // Ruta local para almacenamiento de imágenes

//    private static final String LOCAL_STORAGE_DIR = "src/main/resources/static/pdfs/credenciales/";
//    private static final String FRONTEND_IMAGE_PATH_PREFIX = "/pdfs/credenciales/";
    public static final String PERSISTENT_STORAGE_BASE = "/home/site/wwwroot/";
    public static final String CREDENTIALS_IMAGE_SUBDIR = "images/credenciales/";
    public static final String FULL_IMAGE_STORAGE_DIR = PERSISTENT_STORAGE_BASE + CREDENTIALS_IMAGE_SUBDIR;
    public static final String FRONTEND_IMAGE_PATH_PREFIX = "/images/credenciales/";


    public static Map<String, String> capturarCredencial(String credencialUrl) throws IOException {
        System.out.println("Iniciando captura de credencial para URL: " + credencialUrl);
        Map<String, String> resultados = new HashMap<>();

        // Extraer ID del alumno
        String alumnoId = extractAlumnoId(credencialUrl);
        if (alumnoId == null || alumnoId.trim().isEmpty()) {
            System.err.println("No se pudo extraer un identificador de alumno válido de la URL: " + credencialUrl);
            throw new IOException("No se pudo extraer un identificador válido de la URL.");
        }

        String safeAlumnoId = alumnoId.replaceAll("[^a-zA-Z0-9_.-]", "_");
        resultados.put("alumnoId", safeAlumnoId);
        System.out.println("Alumno ID extraído: " + safeAlumnoId);

        // Crear directorio de imágenes si no existe
        Path imageDirPath = Paths.get(FULL_IMAGE_STORAGE_DIR);
        if (!Files.exists(imageDirPath)) {
            try {
                Files.createDirectories(imageDirPath);
                System.out.println("Directorio de almacenamiento de imágenes creado en: " + imageDirPath.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error: No se pudo crear el directorio de almacenamiento de imágenes en: " + imageDirPath);
                throw new IOException("No se pudo crear el directorio para guardar imágenes.", e);
            }
        }

        // Nombre del archivo de imagen
        String imageName = "credencial_" + safeAlumnoId + ".png";
        Path imagePath = Paths.get(FULL_IMAGE_STORAGE_DIR, imageName);

        // Configuración de Playwright
        try (Playwright playwright = Playwright.create()) {
            BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                    .setHeadless(true)
                    .setArgs(java.util.List.of(
                            "--no-sandbox",
                            "--disable-dev-shm-usage",
                            "--disable-gpu"
                    ));

            Browser browser = playwright.chromium().launch(launchOptions);
            Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                    .setViewportSize(1920, 1080);

            BrowserContext context = browser.newContext(contextOptions);
            Page page = context.newPage();

            try {
                System.out.println("Navegando a la URL: " + credencialUrl);
                page.navigate(credencialUrl, new Page.NavigateOptions()
                        .setWaitUntil(WaitUntilState.NETWORKIDLE));

                // Esperar elemento clave
                System.out.println("Esperando elemento .boleta...");
                page.waitForSelector(".boleta", new Page.WaitForSelectorOptions()
                        .setTimeout(20000));

                // SIEMPRE extraer datos del alumno, aunque ya exista la imagen
                System.out.println("Extrayendo datos del alumno...");
                resultados.putAll(extraerDatosAlumno(page));

                // Solo tomar screenshot si no existe la imagen
                if (!Files.exists(imagePath)) {
                    System.out.println("Tomando screenshot de la página...");
                    byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions()
                            .setPath(imagePath)
                            .setFullPage(true));
                    Files.write(imagePath, screenshotBytes);
                    System.out.println("Screenshot guardado en: " + imagePath.toAbsolutePath());
                } else {
                    System.out.println("La imagen ya existe, omitiendo captura: " + imagePath);
                }

                resultados.put("imagenPath", FRONTEND_IMAGE_PATH_PREFIX + imageName);
                return resultados;

            } catch (PlaywrightException e) {
                System.err.println("Error durante el proceso de scraping con Playwright: " + e.getMessage());
                throw new IOException("Error al procesar la credencial con Playwright: " + e.getMessage(), e);
            } finally {
                System.out.println("Cerrando el contexto de Playwright...");
                context.close();
                browser.close();
            }
        }
    }

    private static Map<String, String> extraerDatosAlumno(Page page) {
        Map<String, String> datos = new HashMap<>();
        System.out.println("\n=== INICIO DE EXTRACCIÓN DE DATOS ===");

        try {
            // Boleta
            String boleta = page.textContent(".boleta").replaceAll("[^0-9]", "");
            datos.put("boleta", boleta);
            System.out.println("[EXTRACCIÓN] Boleta obtenida: " + boleta);

            // CURP
            String curp = page.textContent(".curp").trim().toUpperCase();
            if (!curp.matches("^[A-Z]{4}[0-9]{6}[A-Z]{6}[0-9A-Z]{2}$")) {
                System.err.println("[ADVERTENCIA] CURP con formato potencialmente inválido: " + curp);
            }
            datos.put("curp", curp);
            System.out.println("[OK] CURP: " + curp);

            // Nombre
            String nombre = page.textContent(".nombre").trim();
            datos.put("nombre", nombre);
            System.out.println("[EXTRACCIÓN] Nombre obtenido: " + nombre);

            // Carrera
            String carrera = page.textContent(".carrera").trim();
            datos.put("carrera", carrera);
            System.out.println("[EXTRACCIÓN] Carrera obtenida: " + carrera);

            // Escuela
            String escuela = page.textContent(".escuela").trim();
            datos.put("escuela", escuela);
            System.out.println("[EXTRACCIÓN] Escuela obtenida: " + escuela);

        } catch (PlaywrightException e) {
            System.err.println("[ERROR DE EXTRACCIÓN] Elemento no encontrado: " + e.getMessage());
            // Asegurarse de que los campos requeridos estén en el mapa incluso si hay error
            datos.putIfAbsent("boleta", "");
            datos.putIfAbsent("curp", "");
            datos.putIfAbsent("nombre", "");
            datos.putIfAbsent("carrera", "");
            datos.putIfAbsent("escuela", "");
        }

        System.out.println("=== FIN DE EXTRACCIÓN DE DATOS ===\n");
        return datos;
    }

    private static String extractAlumnoId(String url) {
        if (url == null || !url.contains("?h=")) {
            return null;
        }
        return url.substring(url.indexOf("?h=") + 3);
    }
}