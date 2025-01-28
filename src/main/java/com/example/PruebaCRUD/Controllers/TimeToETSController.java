package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.TimeToETSDTO;
import com.example.PruebaCRUD.Services.PeriodoETSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/TimeToETS")
public class TimeToETSController {
    private final PeriodoETSService periodoETSService;

    @Autowired
    public TimeToETSController(PeriodoETSService periodoETSService) {
        this.periodoETSService = periodoETSService;
    }

    @GetMapping
    public TimeToETSDTO getTimeToETS() {
        return this.periodoETSService.getTimeToETS();
    }
}
