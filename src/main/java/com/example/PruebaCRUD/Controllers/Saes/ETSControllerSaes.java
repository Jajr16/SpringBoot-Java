package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.DTO.Saes.ETSDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.NewETSDTOSaes;
import com.example.PruebaCRUD.Services.ETSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/saes") // Mapear la url a este método
public class ETSControllerSaes {

    private final ETSService etsService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, ETSDetailsService y ETSService
    public ETSControllerSaes(ETSService etsService) {
        this.etsService = etsService;
    }

    @GetMapping("/ets") // Notación para manejar solicitudes GET
    public ResponseEntity<List<ETSDTOSaes>> getETS(){
        List<ETSDTOSaes> response = this.etsService.detailAdminETS();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/NETS") // Notación para manejar solicitudes POST
    public ResponseEntity<Object> registrarETS(@RequestBody NewETSDTOSaes ets) throws Exception {
        System.out.println("EL ETS QUE DA ES " + ets);
        return this.etsService.newETS(ets);
    }
}
