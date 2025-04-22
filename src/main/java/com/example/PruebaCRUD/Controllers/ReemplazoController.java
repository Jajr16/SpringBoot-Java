package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.BD.Reemplazo;
import com.example.PruebaCRUD.DTO.SolicitudReemplazoDTO;
import com.example.PruebaCRUD.DTO.VerificacionSolicitudResponseDTO;
import com.example.PruebaCRUD.Services.ReemplazoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reemplazos")
@CrossOrigin(origins = "*")
public class ReemplazoController {

    private final ReemplazoService reemplazoService;

    @Autowired
    public ReemplazoController(ReemplazoService reemplazoService) {
        this.reemplazoService = reemplazoService;
    }

    @PostMapping
    public ResponseEntity<SolicitudReemplazoDTO> crearSolicitudReemplazo(@RequestBody SolicitudReemplazoDTO solicitudDTO) {
        SolicitudReemplazoDTO nuevoReemplazo = reemplazoService.crearSolicitudReemplazo(solicitudDTO);
        return ResponseEntity.ok(nuevoReemplazo);
    }

    @GetMapping
    public ResponseEntity<List<SolicitudReemplazoDTO>> obtenerTodasSolicitudes() {
        List<SolicitudReemplazoDTO> solicitudes = reemplazoService.obtenerTodasSolicitudes();
        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/verificar-pendiente")
    public ResponseEntity<VerificacionSolicitudResponseDTO> verificarSolicitudPendiente(
            @RequestParam("etsId") Integer etsId,
            @RequestParam("docenteRFC") String docenteRFC) {
        VerificacionSolicitudResponseDTO response = reemplazoService.verificarSolicitudPendiente(etsId, docenteRFC);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("Error", true);
        response.put("message", ex.getMessage());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex.getMessage().contains("Ya existe una solicitud pendiente")) {
            status = HttpStatus.CONFLICT;
        }

        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<SolicitudReemplazoDTO>> obtenerSolicitudesPendientes() {
        List<SolicitudReemplazoDTO> solicitudes = reemplazoService.obtenerTodasSolicitudes();
        return ResponseEntity.ok(solicitudes);
    }

    // Endpoint para aprobar reemplazo
    @PostMapping("/aprobar")
    public ResponseEntity<Map<String, Object>> aprobarReemplazo(
            @RequestParam("idETS") Integer idETS,
            @RequestParam("docenteRFC") String docenteRFC,
            @RequestParam("docenteReemplazo") String docenteReemplazo) {

        Map<String, Object> response = new HashMap<>();
        try {
            reemplazoService.aprobarReemplazo(idETS, docenteRFC, docenteReemplazo);
            response.put("success", true);
            response.put("message", "Reemplazo aprobado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.put("error", true);
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/rechazar")
    public ResponseEntity<Map<String, Object>> rechazarReemplazo(
            @RequestParam("idETS") Integer idETS,
            @RequestParam("docenteRFC") String docenteRFC,
            @RequestParam(value = "motivoRechazo", required = false) String motivoRechazo) {

        Map<String, Object> response = new HashMap<>();
        try {
            reemplazoService.rechazarReemplazo(idETS, docenteRFC, motivoRechazo);
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