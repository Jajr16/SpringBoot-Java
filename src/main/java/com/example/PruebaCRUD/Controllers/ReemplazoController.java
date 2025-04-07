package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.BD.Reemplazo;
import com.example.PruebaCRUD.DTO.SolicitudReemplazoDTO;
import com.example.PruebaCRUD.Services.ReemplazoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Reemplazo> crearSolicitudReemplazo(@RequestBody SolicitudReemplazoDTO solicitudDTO) {
        Reemplazo nuevoReemplazo = reemplazoService.crearSolicitudReemplazo(solicitudDTO);
        return ResponseEntity.ok(nuevoReemplazo);
    }
}