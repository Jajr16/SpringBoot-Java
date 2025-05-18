package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.DTO.Saes.ETSDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.NuevoETSDTOSaes;
import com.example.PruebaCRUD.Services.ETSServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/saes") // Mapear la url a este método
public class ETSControladorSaes {

    private final ETSServicio etsService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, ETSDetailsService y ETSService
    public ETSControladorSaes(ETSServicio etsService) {
        this.etsService = etsService;
    }

    @GetMapping("/ets") // Notación para manejar solicitudes GET
    public ResponseEntity<List<ETSDTOSaes>> obtenerETS(){
        List<ETSDTOSaes> response = this.etsService.detallesAdminETS();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/NETS") // Notación para manejar solicitudes POST
    public ResponseEntity<Object> registrarETS(@RequestBody NuevoETSDTOSaes ets) throws Exception {
        System.out.println("EL ETS QUE DA ES " + ets);
        return this.etsService.nuevoETS(ets);
    }
}
