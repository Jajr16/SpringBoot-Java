package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.DetailETSDTO;
import com.example.PruebaCRUD.DTO.ETSDTO;
import com.example.PruebaCRUD.Repositories.ETSRepository;
import com.example.PruebaCRUD.Services.ETSDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("SalonETS/")
public class ETSDetailsController {
    private final ETSDetailsService etsDetailsService;

    @Autowired
    public ETSDetailsController(ETSDetailsService etsDetailsService) {
        this.etsDetailsService = etsDetailsService;
    }

    @GetMapping("/{ets}")
    public ResponseEntity<DetailETSDTO> detallesETS(@PathVariable("ets") Integer ets) {
        DetailETSDTO response = this.etsDetailsService.detallesETS(ets);
        return ResponseEntity.ok(response);
    }
}
