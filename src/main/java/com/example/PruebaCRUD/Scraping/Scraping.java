package com.example.PruebaCRUD.Scraping;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class Scraping {
    private static final String BASE_PATH = System.getProperty("user.home") + "/pruebacrud";
    private static final String IMAGE_PATH = BASE_PATH + "/images/calendario.png";
    private static final String PDF_PATH = BASE_PATH + "/files/calendario.pdf";
    private static final long UN_MES_EN_MILLIS = 30L * 24 * 60 * 60 * 1000;

    public String processPdfToImage(String url, String cssQuery) {
        try {
            // Intentar crear directorios en la carpeta del usuario
            File baseDir = new File(BASE_PATH);
            File imageDir = new File(BASE_PATH + "/images");
            File pdfDir = new File(BASE_PATH + "/files");

            try {
                if (!baseDir.exists()) baseDir.mkdirs();
                if (!imageDir.exists()) imageDir.mkdirs();
                if (!pdfDir.exists()) pdfDir.mkdirs();
            } catch (Exception e) {
                // Si falla, usar directorio temporal
                String tmpDir = System.getProperty("java.io.tmpdir") + "/pruebacrud";
                imageDir = new File(tmpDir + "/images");
                pdfDir = new File(tmpDir + "/files");
                imageDir.mkdirs();
                pdfDir.mkdirs();
                return processPdfToImageWithPaths(
                    url, 
                    cssQuery, 
                    tmpDir + "/images/calendario.png",
                    tmpDir + "/files/calendario.pdf"
                );
            }

            return processPdfToImageWithPaths(url, cssQuery, IMAGE_PATH, PDF_PATH);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al procesar el PDF a imagen: " + e.getMessage());
        }
    }

    private String processPdfToImageWithPaths(String url, String cssQuery, String imagePath, String pdfPath) throws Exception {
        File imageFile = new File(imagePath);

        // Verificar si la imagen existe y tiene menos de un mes
        if (imageFile.exists() && !isCacheExpired(imageFile)) {
            System.out.println("Usando imagen en caché (menos de un mes de antigüedad)");
            return imagePath;
        }

        // Configurar SSL
        SSLConfig.configureSSL();

        // Hacer scraping para obtener la URL del PDF
        Document document = Jsoup.connect(url).get();
        Element linkElement = document.selectFirst(cssQuery);

        if (linkElement == null || !linkElement.attr("href").endsWith(".pdf")) {
            throw new RuntimeException("No se encontró un enlace al PDF válido.");
        }

        String pdfUrl = linkElement.attr("href");

        // Descargar el PDF
        File pdfFile = new File(pdfPath);
        try (InputStream in = new URL(pdfUrl).openStream();
             FileOutputStream out = new FileOutputStream(pdfFile)) {
            byte[] buffer = new byte[8192]; // Buffer más grande para mejor rendimiento
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        // Convertir la primera página del PDF a imagen
        try (PDDocument pdfDocument = PDDocument.load(pdfFile)) {
            PDFRenderer renderer = new PDFRenderer(pdfDocument);
            BufferedImage image = renderer.renderImageWithDPI(0, 300);
            ImageIO.write(image, "png", imageFile);
            System.out.println("Nueva imagen del calendario guardada en: " + imagePath);
        }

        // Eliminar el PDF temporal
        pdfFile.delete();

        return imagePath;
    }

    private boolean isCacheExpired(File file) {
        long tiempoActual = System.currentTimeMillis();
        long tiempoArchivo = file.lastModified();
        return (tiempoActual - tiempoArchivo) > UN_MES_EN_MILLIS;
    }
}