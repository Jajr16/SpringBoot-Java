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

    private static final String IMAGE_PATH = "./src/main/java/com/example/PruebaCRUD/Scraping/images/calendario.png"; // Ruta donde guardarás la imagen
    private static final String PDF_PATH = "./src/main/java/com/example/PruebaCRUD/Scraping/files/calendario.pdf";   // Ruta donde guardarás el PDF

    public String processPdfToImage(String url, String cssQuery) {
        try {

            File pdfDir = new File("./src/main/java/com/example/PruebaCRUD/Scraping/files");
            File imageDir = new File("./src/main/java/com/example/PruebaCRUD/Scraping/images");
            if (!pdfDir.exists()) pdfDir.mkdirs();
            if (!imageDir.exists()) imageDir.mkdirs();

            // Verificar si la imagen ya existe
            File imageFile = new File(IMAGE_PATH);

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
                BufferedImage image = renderer.renderImageWithDPI(0, 300); // Primera página, 300 DPI
                ImageIO.write(image, "png", imageFile); // Guardar como PNG
                System.out.println("Imagen guardada en: " + IMAGE_PATH);
            }

            return IMAGE_PATH;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al procesar el PDF a imagen: " + e.getMessage());
        }
    }
}
