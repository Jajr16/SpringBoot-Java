//package com.example.PruebaCRUD.Controllers;
//
//import com.example.PruebaCRUD.DTO.ReporteSqlDTO;
//import com.example.PruebaCRUD.Services.ReporteService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//public class ReporteController {
//
//    @Autowired
//    private ReporteService reporteService;
//
//    @GetMapping("/reporte")
//    public ResponseEntity<Map<String, Object>> obtenerReporte(
//            @RequestParam("idets") Integer idets,
//            @RequestParam("boleta") String boleta) throws IOException {
//
//        List<ReporteSqlDTO> reportes = reporteService.obtenerDatosReporte(idets, boleta);
//        ReporteSqlDTO reporte = reportes.get(0); // Asumiendo que solo hay un reporte
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("reporte", reporte);
//
//        return ResponseEntity.ok(response); // Enviar solo los datos del reporte como JSON
//    }
//
//    @GetMapping("/imagenReporte")
//    public ResponseEntity<ByteArrayResource> obtenerImagenReporte(
//            @RequestParam("idets") Integer idets,
//            @RequestParam("boleta") String boleta) throws IOException {
//
//        String rutaImagen = reporteService.obtenerImagenAlumno(idets, boleta);
//
//        if (rutaImagen != null && !rutaImagen.isEmpty() && !rutaImagen.equals("No Imagen")) {
//            byte[] imageBytes = Files.readAllBytes(Paths.get(rutaImagen));
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG); // O MediaType.IMAGE_PNG según el tipo de imagen
//            return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(imageBytes));
//        } else {
//            return ResponseEntity.notFound().build(); // No se encontró la imagen
//        }
//    }
//}