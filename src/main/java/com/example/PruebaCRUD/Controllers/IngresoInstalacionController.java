package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.IngresoInstalacionDTO;
import com.example.PruebaCRUD.Services.IngresoInstalacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("alumno/")
public class IngresoInstalacionController {

    @Autowired
    private IngresoInstalacionService ingresoInstalacionService;

    @PostMapping("/registrar-asistencia")
    public ResponseEntity<?> registrarAsistencia(
            @RequestParam String boleta,
            @RequestParam String fecha,
            @RequestParam String hora,
            @RequestParam Integer idETS) {

        // 1. Validación de parámetros
        if (boleta == null || boleta.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "error", "validation_error",
                    "message", "La boleta no puede estar vacía",
                    "timestamp", LocalDateTime.now()
            ));
        }

        if (idETS == null || idETS <= 0) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "error", "validation_error",
                    "message", "El ID ETS no es válido",
                    "timestamp", LocalDateTime.now()
            ));
        }

        try {
            // 2. Validar formato de fecha y hora
            LocalDateTime fechaHora;
            try {
                fechaHora = LocalDateTime.parse(fecha + "T" + hora);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "error", "validation_error",
                        "message", "Formato de fecha u hora inválido. Use YYYY-MM-DD y HH:mm:ss",
                        "timestamp", LocalDateTime.now()
                ));
            }

            // 3. Verificar inscripción primero
            boolean inscrito = ingresoInstalacionService.verificarInscripcionCompleta(boleta, idETS);
            if (!inscrito) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                        "success", false,
                        "error", "business_rule_violation",
                        "message", "El alumno no está inscrito en esta instalación",
                        "timestamp", LocalDateTime.now()
                ));
            }

            // 4. Registrar asistencia
            List<IngresoInstalacionDTO> registro = ingresoInstalacionService
                    .registrarEntrada(boleta, fecha, hora, idETS);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Asistencia registrada exitosamente",
                    "data", registro,
                    "timestamp", LocalDateTime.now()
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "error", "validation_error",
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "success", false,
                    "error", "business_rule_violation",
                    "message", e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "error", "server_error",
                    "message", "Ocurrió un error inesperado",
                    "details", e.getMessage(),
                    "timestamp", LocalDateTime.now()
            ));
        }
    }

    @GetMapping("/verificar-inscripcion")
    public ResponseEntity<?> verificarInscripcion(
            @RequestParam String boleta,
            @RequestParam Integer idETS) {

        // Validación básica
        if (boleta == null || boleta.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "validation_error",
                    "message", "La boleta no puede estar vacía"
            ));
        }

        if (idETS == null || idETS <= 0) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "validation_error",
                    "message", "El ID ETS no es válido"
            ));
        }

        try {
            boolean inscrito = ingresoInstalacionService.verificarInscripcionCompleta(boleta, idETS);
            return ResponseEntity.ok(Map.of(
                    "inscrito", inscrito,
                    "message", inscrito ? "El alumno está inscrito" : "El alumno no está inscrito"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "Error al verificar inscripción",
                    "message", e.getMessage()
            ));
        }
    }
}