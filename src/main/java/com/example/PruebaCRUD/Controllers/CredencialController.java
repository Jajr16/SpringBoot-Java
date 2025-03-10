package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.CredencialDTO;
import com.example.PruebaCRUD.Services.CredencialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alumno")
public class CredencialController {

    private final CredencialService credencialService;

    @Autowired
    public CredencialController(CredencialService credencialService) {
        this.credencialService = credencialService;
    }

    @GetMapping("credencial/{boleta}")
    public List<CredencialDTO>findCredencialPorBoleta(@PathVariable String boleta) {
        return credencialService.findCredencialPorBoleta(boleta);
    }
}
