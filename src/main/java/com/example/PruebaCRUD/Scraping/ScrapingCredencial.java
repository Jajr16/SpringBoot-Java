package com.example.PruebaCRUD.Scraping;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ScrapingCredencial {

    // Configuración de rutas
    public static final String ALMACENAMIENTO_PERSISTENTE = "/app/";
    public static final String RUTA_IMAGEN_CREDENCIAL = "images/credenciales/";
    public static final String RUTA_PDF_CREDENCIAL = "pdfs/credenciales/";
    public static final String ALMACENAMIENTO_RUTA_COMPLETA = ALMACENAMIENTO_PERSISTENTE + RUTA_IMAGEN_CREDENCIAL;
    public static final String RUTA_PDF_COMPLETA = ALMACENAMIENTO_PERSISTENTE + RUTA_PDF_CREDENCIAL;
    public static final String PREFIJO_FRONTEND_RUTA_IMAGEN = "/images/credenciales/";
    private static final int PDF_DPI = 300;

    // Configurar manejo de cookies global
    static {
        CookieHandler.setDefault(new CookieManager());
    }

    public static Map<String, String> capturarCredencial(String credencialUrl) throws IOException {
        System.out.println("Iniciando captura de credencial para URL: " + credencialUrl);
        Map<String, String> resultados = new HashMap<>();

        // Validación y extracción de ID
        String alumnoId = obtenerBoletaAlumno(credencialUrl);
        if (alumnoId == null || alumnoId.trim().isEmpty()) {
            throw new IOException("No se pudo extraer un identificador válido de la URL: " + credencialUrl);
        }

        String safeAlumnoId = alumnoId.replaceAll("[^a-zA-Z0-9_.-]", "_");
        resultados.put("alumnoId", safeAlumnoId);
        System.out.println("Alumno ID procesado: " + safeAlumnoId);

        // Preparar directorios
        Path imageDirPath = prepararDirectorio(ALMACENAMIENTO_RUTA_COMPLETA);
        Path pdfDirPath = prepararDirectorio(RUTA_PDF_COMPLETA);

        String pdfName = "credencial_" + safeAlumnoId + ".pdf";
        String imageName = "credencial_" + safeAlumnoId + ".png";

        Path pdfPath = pdfDirPath.resolve(pdfName);
        Path imagePath = imageDirPath.resolve(imageName);

        try {
            // Paso 1: Obtener el HTML de la página
            String htmlContent = buscarContenidoHtml(credencialUrl);

            // Paso 2: Extraer datos del HTML
            Document doc = Jsoup.parse(htmlContent);
            resultados.putAll(extraerDatosAlumno(doc));

            if (!Files.exists(imagePath)) {
                // Paso 3: Generar PDF desde HTML
                generarPDFDeHTML(htmlContent, pdfPath);

                // Paso 4: Convertir PDF a imagen
                convertirPDFaImagen(pdfPath, imagePath);
            } else {
                System.out.println("Imagen existente, omitiendo captura: " + imagePath);
            }

            resultados.put("imagenPath", PREFIJO_FRONTEND_RUTA_IMAGEN + imageName);
            return resultados;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Proceso interrumpido", e);
        }
    }

    private static String buscarContenidoHtml(String url) throws IOException, InterruptedException {
        // Pequeño retardo aleatorio entre 1-3 segundos
        Thread.sleep(1000 + (long)(Math.random() * 2000));

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)  // Seguir redirecciones
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Accept-Language", "es-MX,es;q=0.9,en;q=0.8")
                .header("Referer", "https://www.dae.ipn.mx/")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Verificar si hubo redirección
        if (response.statusCode() >= 300 && response.statusCode() < 400) {
            String newLocation = response.headers().firstValue("Location").orElse(null);
            if (newLocation != null) {
                System.out.println("Redirección detectada. Nueva ubicación: " + newLocation);
                return buscarContenidoHtml(newLocation);
            }
        }

        if (response.statusCode() != 200) {
            throw new IOException("Error al obtener la página. Código: " + response.statusCode() +
                    ", Ubicación: " + response.headers().firstValue("Location").orElse("N/A"));
        }

        return response.body();
    }

    // Resto de los métodos permanecen igual...
    private static void generarPDFDeHTML(String htmlContent, Path pdfPath) throws IOException {
        try (OutputStream os = new FileOutputStream(pdfPath.toFile())) {
            ITextRenderer renderer = new ITextRenderer();

            // Configurar el HTML (necesita ser XHTML compatible)
            String xhtml = convertirAXhtml(htmlContent);
            renderer.setDocumentFromString(xhtml);

            renderer.layout();
            renderer.createPDF(os);
        }
        System.out.println("PDF generado: " + pdfPath.toAbsolutePath());
    }

    private static String convertirAXhtml(String html) {
        // Jsoup puede ayudar a limpiar el HTML
        Document doc = Jsoup.parse(html);
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return doc.html();
    }

    private static void convertirPDFaImagen(Path pdfPath, Path imagePath) throws IOException {
        try (PDDocument document = PDDocument.load(pdfPath.toFile())) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage image = pdfRenderer.renderImageWithDPI(0, PDF_DPI);
            ImageIO.write(image, "PNG", imagePath.toFile());
            System.out.println("PDF convertido a imagen: " + imagePath.toAbsolutePath());
        }
    }

    private static Path prepararDirectorio(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            System.out.println("Directorio creado: " + path.toAbsolutePath());
        }
        return path;
    }

    private static Map<String, String> extraerDatosAlumno(Document doc) {
        Map<String, String> datos = new HashMap<>();

        datos.put("boleta", obtenerTexto(doc, ".boleta", "[^0-9]"));
        datos.put("curp", obtenerTexto(doc, ".curp", "").trim().toUpperCase());
        datos.put("nombre", obtenerTexto(doc, ".nombre", "").trim());
        datos.put("carrera", obtenerTexto(doc, ".carrera", "").trim());
        datos.put("escuela", obtenerTexto(doc, ".escuela", "").trim());

        if (!datos.get("curp").matches("^[A-Z]{4}[0-9]{6}[A-Z]{6}[0-9A-Z]{2}$")) {
            System.err.println("Advertencia: CURP con formato potencialmente inválido");
        }

        return datos;
    }

    private static String obtenerTexto(Document doc, String selector, String regexToRemove) {
        try {
            Element element = doc.selectFirst(selector);
            String content = element != null ? element.text() : "";
            return regexToRemove.isEmpty() ? content : content.replaceAll(regexToRemove, "");
        } catch (Exception e) {
            System.err.println("Error obteniendo " + selector + ": " + e.getMessage());
            return "";
        }
    }

    private static String obtenerBoletaAlumno(String url) {
        if (url == null || !url.contains("?h=")) return null;
        return url.substring(url.indexOf("?h=") + 3);
    }
}