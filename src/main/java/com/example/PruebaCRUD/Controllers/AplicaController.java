package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.Services.AplicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/aplica")
public class AplicaController {

    @Autowired
    private AplicaService aplicaService;

    @GetMapping("/docente/rfc/{idets}")
    public ResponseEntity<Map<String, String>> obtenerRfcDocente(@PathVariable int idets) {
        String rfc = aplicaService.obtenerRfcDocente(idets);
        if (rfc != null) {
            Map<String, String> response = new HashMap<>();
            response.put("rfc", rfc);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}