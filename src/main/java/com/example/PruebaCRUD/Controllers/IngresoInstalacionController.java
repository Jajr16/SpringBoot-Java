package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.IngresoInstalacionDTO;
import com.example.PruebaCRUD.Services.IngresoInstalacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("alumno/")
public class IngresoInstalacionController {

    @Autowired
    private IngresoInstalacionService ingresoInstalacionService;

    @PostMapping("registrar-asistencia")
    public ResponseEntity<?> registrarAsistencia(
            @RequestParam String boleta,
            @RequestParam String fecha,
            @RequestParam String hora,
            @RequestParam Integer idETS) {

        try {
            List<IngresoInstalacionDTO> registro = ingresoInstalacionService
                    .registrarEntrada(boleta, fecha, hora, idETS);
            return ResponseEntity.ok(registro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Datos inválidos",
                    "message", e.getMessage()
            ));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Registro duplicado o no inscrito",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "Error interno del servidor",
                    "message", e.getMessage()
            ));
        }
    }
}