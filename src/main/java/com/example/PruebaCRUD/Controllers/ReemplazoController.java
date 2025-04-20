package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.SolicitudReemplazoDTO;
import com.example.PruebaCRUD.DTO.VerificacionSolicitudResponseDTO;
import com.example.PruebaCRUD.Services.ReemplazoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
}