package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.ListaETSRespuestaDTO;
import com.example.PruebaCRUD.Services.ListaETSServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/ETS")
public class ListaInscripcionesControlador {
    private final ListaETSServicio listaETSServicio;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public ListaInscripcionesControlador(ListaETSServicio listaETSServicio) {
        this.listaETSServicio = listaETSServicio;
    }

    @GetMapping("/InscripcionAlumno/{boleta}") // Notación para manejar solicitudes GET
    public ResponseEntity<List<ListaETSRespuestaDTO>> listaInscritos(@PathVariable("boleta") String boleta) {
        List<ListaETSRespuestaDTO> respuesta = listaETSServicio.inscripcionesETS(boleta);

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/InscripcionDocente/{docente_rfc}") // Notación para manejar solicitudes GET
    public ResponseEntity<List<ListaETSRespuestaDTO>> LitaAplicacion(@PathVariable("docente_rfc") String boleta) {
        List<ListaETSRespuestaDTO> respuesta = listaETSServicio.aplicacionETS(boleta);

        return ResponseEntity.ok(respuesta);
    }
}
