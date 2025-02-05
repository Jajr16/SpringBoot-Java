package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.BD.ETS;
import com.example.PruebaCRUD.BD.periodoETS;
import com.example.PruebaCRUD.DTO.*;
import com.example.PruebaCRUD.Services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saes")
public class SAESController {
    private final ETSDetailsService etsDetailsService;
    private final PersonaService personaService;
    private final PeriodoETSService periodoETSService;
    private final UnidadAprendizajeService unidadAprendizajeService;
    private final ETSService etsService;

    public SAESController(ETSDetailsService etsDetailsService, PersonaService personaService, PeriodoETSService periodoETSService, UnidadAprendizajeService unidadAprendizajeService, ETSService etsService) {
        this.etsDetailsService = etsDetailsService;
        this.personaService = personaService;
        this.periodoETSService = periodoETSService;
        this.unidadAprendizajeService = unidadAprendizajeService;
        this.etsService = etsService;
    }

    @GetMapping("/ets")
    public ResponseEntity<List<ETSDTOSaes>> getETS(){
        List<ETSDTOSaes> response = this.etsDetailsService.detailAdminETS();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/alumnos")
    public ResponseEntity<List<AlumnoDTOSaes>> getAlumnos(){
        List<AlumnoDTOSaes> response = this.personaService.getAlumnos();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/docentes")
    public ResponseEntity<List<DocentesDTOSaes>> getDocentes(){
        List<DocentesDTOSaes> response = this.personaService.getDocentes();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ps")
    public ResponseEntity<List<PersonalSeguridadDTOSaes>> getPS(){
        List<PersonalSeguridadDTOSaes> response = this.personaService.getPS();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/periodoETS")
    public ResponseEntity<List<periodoETS>> getPeriodos() {
        List<periodoETS> response = this.periodoETSService.getPeriodos();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/periodoETS")
    public ResponseEntity<Object> registrarPeriodo(@RequestBody periodoETS periodoETS) {
        System.out.println("EL PERIODO QUE DA ES " + periodoETS);
        return this.periodoETSService.newPeriodo(periodoETS);
    }

    @PostMapping("/NETS")
    public ResponseEntity<Object> registrarETS(@RequestBody NewETSDTOSaes ets) {
        System.out.println("EL ETS QUE DA ES " + ets);
        return this.etsService.newETS(ets);
    }

//    ######################## PARA NUEVOS REGISTROS DE OTRAS TABLAS

    @GetMapping("/PeriodoToETS")
    public ResponseEntity<List<PeriodosETSProjectionSaes>> getPeriodosToETS() {
        List<PeriodosETSProjectionSaes> response = this.periodoETSService.obtenerPeriodos();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        System.out.println(response);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/UAprenToETS")
    public ResponseEntity<List<UnidadAprendizajeProjectionSaes>> getUAprenToETS() {
        List<UnidadAprendizajeProjectionSaes> response = this.unidadAprendizajeService.getUApren();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        System.out.println(response);
        return ResponseEntity.ok(response);
    }
}
