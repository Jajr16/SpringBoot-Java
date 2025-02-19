package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.Services.ListETSService;
import com.example.PruebaCRUD.Services.ListETSService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;



public class InscripcionesController2 {

    private final ListETSService2 listETSService2;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public InscripcionesController2(ListETSService2 listETSService2) {
        this.listETSService2 = listETSService2;
    }

    @GetMapping("/confirm2/{docente_rfc}") // Notación para manejar solicitudes GET
    public Map<String, Boolean>  confirmIns(@PathVariable("docente_rfc") String boleta) {
        boolean message = listETSService2.confirmInscripcion(boleta);

        Map<String, Boolean> response = new HashMap<>();
        response.put("message", message);
        return response;

    }
}
