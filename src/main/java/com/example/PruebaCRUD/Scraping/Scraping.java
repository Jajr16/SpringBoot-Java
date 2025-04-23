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
    private static final String IMAGE_PATH = "./src/main/java/com/example/PruebaCRUD/Scraping/images/calendario.png";
    private static final String PDF_PATH = "./src/main/java/com/example/PruebaCRUD/Scraping/files/calendario.pdf";
    private static final long UN_MES_EN_MILLIS = 30L * 24 * 60 * 60 * 1000; // 30 días en milisegundos

    public String processPdfToImage(String url, String cssQuery) {
        try {
            File imageDir = new File("./src/main/java/com/example/PruebaCRUD/Scraping/images");
            File pdfDir = new File("./src/main/java/com/example/PruebaCRUD/Scraping/files");
            if (!imageDir.exists()) imageDir.mkdirs();
            if (!pdfDir.exists()) pdfDir.mkdirs();

            File imageFile = new File(IMAGE_PATH);

            // Verificar si la imagen existe y tiene menos de un mes
            if (imageFile.exists() && !isCacheExpired(imageFile)) {
                System.out.println("Usando imagen en caché (menos de un mes de antigüedad)");
                return IMAGE_PATH;
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
            File pdfFile = new File(PDF_PATH);
            try (InputStream in = new URL(pdfUrl).openStream();
                 FileOutputStream out = new FileOutputStream(pdfFile)) {
                byte[] buffer = new byte[1024];
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
                System.out.println("Nueva imagen del calendario guardada en: " + IMAGE_PATH);
            }

            // Eliminar el PDF temporal
            pdfFile.delete();

            return IMAGE_PATH;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al procesar el PDF a imagen: " + e.getMessage());
        }
    }

    private boolean isCacheExpired(File file) {
        long tiempoActual = System.currentTimeMillis();
        long tiempoArchivo = file.lastModified();
        return (tiempoActual - tiempoArchivo) > UN_MES_EN_MILLIS;
    }
}
