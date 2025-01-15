package com.example.PruebaCRUD.Sexo;

import com.example.PruebaCRUD.BD.Sexo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/sexo")
public class SexoController {
    private final SexoService sexoService;

    @Autowired
    public SexoController(SexoService sexoService) {
        this.sexoService = sexoService;
    }

    @GetMapping
    public List<Sexo> getSexo() {
        return this.sexoService.getSexo();
    }

}
