package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.Services.ListETSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionesController {
    private final ListETSService listETSService;

    @Autowired
    public InscripcionesController(ListETSService listETSService) {
        this.listETSService = listETSService;
    }

    @GetMapping("/confirm/{boleta}")
    public Map<String, Boolean>  confirmIns(@PathVariable("boleta") String boleta) {
        boolean message = listETSService.confirmInscripcion(boleta);

        Map<String, Boolean> response = new HashMap<>();
        response.put("message", message);
        return response;

    }

}
