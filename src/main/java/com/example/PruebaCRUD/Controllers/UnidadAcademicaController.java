package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.BD.UnidadAcademica;
import com.example.PruebaCRUD.Services.UnidadAcademicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/UA")
public class UnidadAcademicaController {
    private final UnidadAcademicaService unidadAcademicaService;

    @Autowired
    public UnidadAcademicaController(UnidadAcademicaService unidadAcademicaService) {
        this.unidadAcademicaService = unidadAcademicaService;
    }

    @GetMapping
    public List<UnidadAcademica> getUA() {
        return this.unidadAcademicaService.getUA();
    }
}
