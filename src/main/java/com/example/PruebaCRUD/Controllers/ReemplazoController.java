package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.BD.Reemplazo;
import com.example.PruebaCRUD.Services.ReemplazoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reemplazos")
@CrossOrigin(origins = "*")
public class ReemplazoController {

    private final ReemplazoService reemplazoService;

    @Autowired
    public ReemplazoController(ReemplazoService reemplazoService) {
        this.reemplazoService = reemplazoService;
    }

    @GetMapping("/docente/{docenteRFC}")
    public List<Reemplazo> obtenerReemplazosPorDocente(@PathVariable String docenteRFC) {
        return reemplazoService.obtenerReemplazosPorDocenteRFC(docenteRFC);
    }
}
