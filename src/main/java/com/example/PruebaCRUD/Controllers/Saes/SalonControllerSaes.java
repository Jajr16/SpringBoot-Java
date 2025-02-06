package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.DTO.Saes.PeriodosETSProjectionSaes;
import com.example.PruebaCRUD.DTO.Saes.SalonProjectionSaes;
import com.example.PruebaCRUD.Services.SalonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/saes")
public class SalonControllerSaes {
    private final SalonService salonService;

    public SalonControllerSaes(SalonService salonService) {
        this.salonService = salonService;
    }

    @GetMapping("/SalonToETS")
    public ResponseEntity<List<?>> getSalonToETS() {
        List<?> response = this.salonService.getSalonesToETS();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        System.out.println("SALON TO ETS ES " + response);
        return ResponseEntity.ok(response);
    }
}
