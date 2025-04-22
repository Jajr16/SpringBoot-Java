package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.*;
import com.example.PruebaCRUD.Services.AlumnoService;
import com.example.PruebaCRUD.Services.InscripcionETSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {

    private final AlumnoService alumnoService;
    private final InscripcionETSService inscripcionETSService;

    @Autowired
    public AlumnoController(AlumnoService alumnoService, InscripcionETSService inscripcionETSService) {
        this.alumnoService = alumnoService;
        this.inscripcionETSService = inscripcionETSService;
    }

    @GetMapping("/inscritosETS")
    public List<AlumnoDTO>findAlumnosInscritosETS() {
        return alumnoService.findAlumnosInscritosETS();

    }

    @GetMapping("/detalle/{boleta}")
    public List<DetalleAlumnosDTO>findDetalleAlumnoporboleta(@PathVariable String boleta){
        return inscripcionETSService.findDetalleAlumnoporboleta(boleta);
    }

    @GetMapping("/credencial/{boleta}")
    public List<CredencialDTO>findCredencialPorBoleta(@PathVariable String boleta) {
        return alumnoService.findCredencialPorBoleta(boleta);
    }

    @PostMapping("/comparar/{boleta}")
    public ComparacionDTO compararDatos(@PathVariable String boleta, @RequestBody DatosWebDTO datosWeb) {
        ComparacionDTO resultado = alumnoService.compararDatos(boleta, datosWeb);
        return resultado;
    }

    @GetMapping("/{boleta}")
    public EstudianteEspecificoDTO obtenerEstudiante(@PathVariable String boleta) {
        return alumnoService.obtenerEstudiantePorBoleta(boleta);
    }

    @GetMapping("/foto/{boleta}")
    public ResponseEntity<Map<String, String>> getFotoAlumno(@PathVariable String boleta) {
        try {
            String fotoUrl = alumnoService.obtenerUrlPorBoleta(boleta);

            Map<String, String> response = new HashMap<>();
            response.put("fotoUrl", fotoUrl);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/reporte")
    public ResponseEntity<Map<String, Object>> obtenerReporte(
            @RequestParam("idets") Integer idets,
            @RequestParam("boleta") String boleta) throws IOException {

        List<ReporteSqlDTO> reportes = alumnoService.obtenerDatosReporte(idets, boleta);
        ReporteSqlDTO reporte = reportes.get(0);

        Map<String, Object> response = new HashMap<>();
        response.put("reporte", reporte);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/imagenReporte")
    public ResponseEntity<ByteArrayResource> obtenerImagenReporte(
            @RequestParam("idets") Integer idets,
            @RequestParam("boleta") String boleta) throws IOException {

        String rutaImagen = alumnoService.obtenerImagenAlumno(idets, boleta);

        if (rutaImagen != null && !rutaImagen.isEmpty() && !rutaImagen.equals("No Imagen")) {
            byte[] imageBytes = Files.readAllBytes(Paths.get(rutaImagen));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(imageBytes));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/inscritosETS/{ETSid}") // Notación para manejar solicitudes GET
    public ResponseEntity<List<ListAlumnosDTO>> inscripList(@PathVariable("ETSid") String idets) {
        System.out.println("dato" + idets);
        List<ListAlumnosDTO> response = inscripcionETSService.ListarAlumnos(Integer.valueOf(idets));
        System.out.println("regreso" + response);

        return ResponseEntity.ok(response);
    }

}

