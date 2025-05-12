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
import java.net.URI;
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
    public static final String PERSISTENT_STORAGE_BASE = "/app/";
    public static final String CREDENTIALS_IMAGE_SUBDIR = "images/credenciales/";
    public static final String CREDENTIALS_PDF_SUBDIR = "pdfs/credenciales/";
    public static final String FULL_IMAGE_STORAGE_DIR = PERSISTENT_STORAGE_BASE + CREDENTIALS_IMAGE_SUBDIR;
    public static final String FULL_PDF_STORAGE_DIR = PERSISTENT_STORAGE_BASE + CREDENTIALS_PDF_SUBDIR;
    public static final String FRONTEND_IMAGE_PATH_PREFIX = "/images/credenciales/";
    private static final int PDF_DPI = 300;

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

        // Preparar directorios
        Path imageDirPath = prepareDirectory(FULL_IMAGE_STORAGE_DIR);
        Path pdfDirPath = prepareDirectory(FULL_PDF_STORAGE_DIR);

        String pdfName = "credencial_" + safeAlumnoId + ".pdf";
        String imageName = "credencial_" + safeAlumnoId + ".png";

        Path pdfPath = pdfDirPath.resolve(pdfName);
        Path imagePath = imageDirPath.resolve(imageName);

        try {
            // Paso 1: Obtener el HTML de la página
            String htmlContent = fetchHtmlContent(credencialUrl);

            // Paso 2: Extraer datos del HTML
            Document doc = Jsoup.parse(htmlContent);
            resultados.putAll(extraerDatosAlumno(doc));

            if (!Files.exists(imagePath)) {
                // Paso 3: Generar PDF desde HTML
                generatePdfFromHtml(htmlContent, pdfPath);

                // Paso 4: Convertir PDF a imagen
                convertPDFToImage(pdfPath, imagePath);
            } else {
                System.out.println("Imagen existente, omitiendo captura: " + imagePath);
            }

            resultados.put("imagenPath", FRONTEND_IMAGE_PATH_PREFIX + imageName);
            return resultados;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Proceso interrumpido", e);
        }
    }

    private static String fetchHtmlContent(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Error al obtener la página. Código: " + response.statusCode());
        }

        return response.body();
    }

    private static void generatePdfFromHtml(String htmlContent, Path pdfPath) throws IOException {
        try (OutputStream os = new FileOutputStream(pdfPath.toFile())) {
            ITextRenderer renderer = new ITextRenderer();

            // Configurar el HTML (necesita ser XHTML compatible)
            String xhtml = convertToXhtml(htmlContent);
            renderer.setDocumentFromString(xhtml);

            renderer.layout();
            renderer.createPDF(os);
        }
        System.out.println("PDF generado: " + pdfPath.toAbsolutePath());
    }

    private static String convertToXhtml(String html) {
        // Jsoup puede ayudar a limpiar el HTML
        Document doc = Jsoup.parse(html);
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return doc.html();
    }

    private static void convertPDFToImage(Path pdfPath, Path imagePath) throws IOException {
        try (PDDocument document = PDDocument.load(pdfPath.toFile())) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage image = pdfRenderer.renderImageWithDPI(0, PDF_DPI);
            ImageIO.write(image, "PNG", imagePath.toFile());
            System.out.println("PDF convertido a imagen: " + imagePath.toAbsolutePath());
        }
    }

    private static Path prepareDirectory(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            System.out.println("Directorio creado: " + path.toAbsolutePath());
        }
        return path;
    }

    private static Map<String, String> extraerDatosAlumno(Document doc) {
        Map<String, String> datos = new HashMap<>();

        datos.put("boleta", getSafeTextContent(doc, ".boleta", "[^0-9]"));
        datos.put("curp", getSafeTextContent(doc, ".curp", "").trim().toUpperCase());
        datos.put("nombre", getSafeTextContent(doc, ".nombre", "").trim());
        datos.put("carrera", getSafeTextContent(doc, ".carrera", "").trim());
        datos.put("escuela", getSafeTextContent(doc, ".escuela", "").trim());

        if (!datos.get("curp").matches("^[A-Z]{4}[0-9]{6}[A-Z]{6}[0-9A-Z]{2}$")) {
            System.err.println("Advertencia: CURP con formato potencialmente inválido");
        }

        return datos;
    }

    private static String getSafeTextContent(Document doc, String selector, String regexToRemove) {
        try {
            Element element = doc.selectFirst(selector);
            String content = element != null ? element.text() : "";
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