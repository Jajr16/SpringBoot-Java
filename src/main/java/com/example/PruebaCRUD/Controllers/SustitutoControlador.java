package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.SolicitudReemplazoDTO;
import com.example.PruebaCRUD.DTO.VerificacionSolicitudResponseDTO;
import com.example.PruebaCRUD.Services.SustitutoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reemplazos")
@CrossOrigin(origins = "*")
public class SustitutoControlador {

    private final SustitutoServicio sustitutoServicio;

    @Autowired
    public SustitutoControlador(SustitutoServicio sustitutoServicio) {
        this.sustitutoServicio = sustitutoServicio;
    }

    @PostMapping
    public ResponseEntity<SolicitudReemplazoDTO> crearSolicitudReemplazo(@RequestBody SolicitudReemplazoDTO solicitudDTO) {
        System.out.println("LOS PARÁMETROS QUE LE LLEGAN SON: " + solicitudDTO.toString());
        SolicitudReemplazoDTO nuevoReemplazo = sustitutoServicio.crearSolicitudReemplazo(solicitudDTO);
        return ResponseEntity.ok(nuevoReemplazo);
    }

    @GetMapping
    public ResponseEntity<List<SolicitudReemplazoDTO>> obtenerTodasSolicitudes() {
        List<SolicitudReemplazoDTO> solicitudes = sustitutoServicio.obtenerTodasSolicitudes();
        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/verificar-pendiente")
    public ResponseEntity<VerificacionSolicitudResponseDTO> verificarSolicitudPendiente(
            @RequestParam("etsId") Integer etsId,
            @RequestParam("docenteRFC") String docenteRFC) {
        VerificacionSolicitudResponseDTO respuesta = sustitutoServicio.verificarSolicitudPendiente(etsId, docenteRFC);
        return ResponseEntity.ok(respuesta);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Error", true);
        respuesta.put("message", ex.getMessage());

        HttpStatus estado = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex.getMessage().contains("Ya existe una solicitud pendiente")) {
            estado = HttpStatus.CONFLICT;
        }

        return ResponseEntity.status(estado).body(respuesta);
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<SolicitudReemplazoDTO>> obtenerSolicitudesPendientes() {
        List<SolicitudReemplazoDTO> solicitudes = sustitutoServicio.obtenerTodasSolicitudes();
        return ResponseEntity.ok(solicitudes);
    }

    // Endpoint para aprobar reemplazo
    @PostMapping("/aprobar")
    public ResponseEntity<Map<String, Object>> aprobarReemplazo(
            @RequestParam("idETS") Integer idETS,
            @RequestParam("docenteRFC") String docenteRFC,
            @RequestParam("docenteReemplazo") String docenteReemplazo) {

        Map<String, Object> respuesta = new HashMap<>();
        try {
            sustitutoServicio.aprobarReemplazo(idETS, docenteRFC, docenteReemplazo);
            respuesta.put("success", true);
            respuesta.put("message", "Reemplazo aprobado correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (Exception ex) {
            respuesta.put("error", true);
            respuesta.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @PostMapping("/rechazar")
    public ResponseEntity<Map<String, Object>> rechazarReemplazo(
            @RequestParam("idETS") Integer idETS,
            @RequestParam("docenteRFC") String docenteRFC,
            @RequestParam(value = "motivoRechazo", required = false) String motivoRechazo) {

        Map<String, Object> response = new HashMap<>();
        try {
            sustitutoServicio.rechazarReemplazo(idETS, docenteRFC, motivoRechazo);
            response.put("success", true);
            response.put("message", "Solicitud rechazada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.put("error", true);
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}