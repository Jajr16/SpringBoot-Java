package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.DTO.Saes.AlumnoDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.DocentesDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.DocentesDTOToETS;
import com.example.PruebaCRUD.DTO.Saes.PersonalSeguridadDTOSaes;
import com.example.PruebaCRUD.Services.PersonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/saes")
public class PersonaControllerSaes {
    private final PersonaService personaService;

    public PersonaControllerSaes(PersonaService personaService) {
        this.personaService = personaService;
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

    @GetMapping("/DocenteToETS")
    public ResponseEntity<List<DocentesDTOToETS>> getDocenteToETS() {
        List<DocentesDTOToETS> response = this.personaService.getDocentesToETS();
        System.out.println("AQUI EL DOCENTE ES " + response);

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }
}
