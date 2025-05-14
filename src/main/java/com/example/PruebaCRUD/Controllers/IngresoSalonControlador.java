package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.Services.IngresoSalonServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngresoSalonControlador {

    private final IngresoSalonServicio ingresoSalonServicio;

    @Autowired
    public IngresoSalonControlador(IngresoSalonServicio ingresoSalonService) {
        this.ingresoSalonServicio = ingresoSalonService;
    }

    @GetMapping("/verificarReporte")
    public ResponseEntity<String> verificarIngreso(
            @RequestParam("idets") Integer idets,
            @RequestParam("boleta") String boleta) {
        String resultado = ingresoSalonServicio.verificarIngreso(idets, boleta);
        String jsonResponse = String.format("{\"resultado\": \"%s\"}", resultado);
        System.out.println("Respuesta del servidor: " + jsonResponse); // Agrega esta línea
        return ResponseEntity.ok(jsonResponse);
    }

    @DeleteMapping("/eliminarReporte")
    public ResponseEntity<String> eliminarReporte(
            @RequestParam("idets") Integer idets,
            @RequestParam("boleta") String boleta) {
        boolean eliminado = ingresoSalonServicio.eliminarReporte(idets, boleta);
        if (eliminado) {
            return ResponseEntity.ok("{\"mensaje\": \"Reporte eliminado exitosamente.\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"mensaje\": \"No se encontró el reporte para eliminar.\"}");
        }
    }
}
