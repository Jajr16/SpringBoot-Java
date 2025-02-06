package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.DTO.Saes.ETSDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.NewETSDTOSaes;
import com.example.PruebaCRUD.Services.ETSDetailsService;
import com.example.PruebaCRUD.Services.ETSService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saes")
public class ETSControllerSaes {

    private final ETSDetailsService etsDetailsService;
    private final ETSService etsService;

    public ETSControllerSaes(ETSDetailsService etsDetailsService, ETSService etsService) {
        this.etsDetailsService = etsDetailsService;
        this.etsService = etsService;
    }

    @GetMapping("/ets")
    public ResponseEntity<List<ETSDTOSaes>> getETS(){
        List<ETSDTOSaes> response = this.etsDetailsService.detailAdminETS();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/NETS")
    public ResponseEntity<Object> registrarETS(@RequestBody NewETSDTOSaes ets) throws Exception {
        System.out.println("EL ETS QUE DA ES " + ets);
        return this.etsService.newETS(ets);
    }
}
