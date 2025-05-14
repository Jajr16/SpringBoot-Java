package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.*;
import com.example.PruebaCRUD.Services.AlumnoServicio;
import com.example.PruebaCRUD.Services.InscripcionETSServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alumno")
public class AlumnoControlador {

    private final AlumnoServicio alumnoServicio;
    private final InscripcionETSServicio inscripcionETSServicio;

    @Autowired
    public AlumnoControlador(AlumnoServicio alumnoServicio, InscripcionETSServicio inscripcionETSServicio) {
        this.alumnoServicio = alumnoServicio;
        this.inscripcionETSServicio = inscripcionETSServicio;
    }

    @GetMapping("/inscritosETS")
    public List<AlumnoDTO> encontrarAlumnosInscritosETS() {
        return alumnoServicio.encontrarAlumnosInscritosETS();

    }

    @GetMapping("/detalle/{boleta}")
    public List<DetalleAlumnosDTO> encontrarDetalleAlumnoporboleta(@PathVariable String boleta) {
        return inscripcionETSServicio.encontrarDetalleAlumnoporboleta(boleta);
    }

    @GetMapping("/credencial/{boleta}")
    public List<CredencialDTO> encontrarCredencialPorBoleta(@PathVariable String boleta) {
        return alumnoServicio.encontrarCredencialPorBoleta(boleta);
    }

    @PostMapping("/comparar/{boleta}")
    public ComparacionDTO compararDatos(@PathVariable String boleta, @RequestBody DatosWebDTO datosWeb) {
        ComparacionDTO resultado = alumnoServicio.compararDatos(boleta, datosWeb);
        return resultado;
    }

    @GetMapping("/{boleta}")
    public EstudianteEspecificoDTO obtenerEstudiante(@PathVariable String boleta) {
        return alumnoServicio.obtenerEstudiantePorBoleta(boleta);
    }

    @GetMapping("/foto/{boleta}")
    public ResponseEntity<Map<String, String>> obtenerFotoAlumno(@PathVariable String boleta) {
        try {
            String fotoUrl = alumnoServicio.obtenerUrlPorBoleta(boleta);

            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("fotoUrl", fotoUrl);

            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/reporte")
    public ResponseEntity<Map<String, Object>> obtenerReporte(
            @RequestParam("idets") Integer idets,
            @RequestParam("boleta") String boleta) throws IOException {

        List<ReporteSqlDTO> reportes = alumnoServicio.obtenerDatosReporte(idets, boleta);
        ReporteSqlDTO reporte = reportes.get(0);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("reporte", reporte);

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/imagenReporte")
    public ResponseEntity<Map<String, String>> obtenerImagenReporteUrl(
            @RequestParam("idets") Integer idets,
            @RequestParam("boleta") String boleta) {

        String rutaImagen = alumnoServicio.obtenerImagenAlumno(idets, boleta);

        if (rutaImagen != null && !rutaImagen.isEmpty() && !rutaImagen.equals("No Imagen")) {
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("imageUrl", rutaImagen);
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/inscritosETS/{ETSid}") // Notación para manejar solicitudes GET
    public ResponseEntity<List<ListaAlumnosDTO>> inscripLista(@PathVariable("ETSid") String idets) {
        System.out.println("dato" + idets);
        List<ListaAlumnosDTO> respuesta = inscripcionETSServicio.ListarAlumnos(Integer.valueOf(idets));
        System.out.println("regreso" + respuesta);

        return ResponseEntity.ok(respuesta);
    }

}

