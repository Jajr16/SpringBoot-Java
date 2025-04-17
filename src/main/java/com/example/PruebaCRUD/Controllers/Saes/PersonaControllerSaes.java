package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.DTO.Saes.*;
import com.example.PruebaCRUD.Services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/saes") // Mapear la url a este método
public class PersonaControllerSaes {
    private final PersonaService personaService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public PersonaControllerSaes(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping("/alumnos") // Notación para manejar solicitudes GET
    public ResponseEntity<List<AlumnoDTOSaes>> getAlumnos(){
        List<AlumnoDTOSaes> response = this.personaService.getAlumnos();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/nAlumno")
    public ResponseEntity<Object> newAlumno(@ModelAttribute NewAlumnoDTOSaes newAlumnoDTOSaes,
                                            @RequestParam("video")MultipartFile video,
                                            @RequestParam("credencial")MultipartFile credencial) throws IOException {
        return this.personaService.newAlumno(newAlumnoDTOSaes, video, credencial);
    }

    @GetMapping("/check-volume")
    public String testVolumen() {
        File dir = new File("/data/EntrenamientoIMG");
        return "Existe: " + dir.exists() + " - Puedo escribir: " + dir.canWrite();
    }

    @PostMapping("/nvAlumno")
    public ResponseEntity<Object> newVideoAlumno(@ModelAttribute NewVideoAlumnoDTOSaes newVideoAlumnoDTOSaes)
            throws IOException {
        return this.personaService.newVideoAlumno(newVideoAlumnoDTOSaes);
    }

    @GetMapping("/docentes")
    public ResponseEntity<List<DocentesDTOSaes>> getDocentes(){
        List<DocentesDTOSaes> response = this.personaService.getDocentes();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/nd")
    public ResponseEntity<Object> registrarDocentes(@RequestBody NewDocentesDTOSaes newDocentesDTOSaes) {
        return this.personaService.newDocente(newDocentesDTOSaes);
    }

    @GetMapping("/ps")
    public ResponseEntity<List<PersonalSeguridadDTOSaes>> getPS(){
        List<PersonalSeguridadDTOSaes> response = this.personaService.getPS();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/nps")
    public ResponseEntity<Object> registrarPersonalSeguridad(@RequestBody NewPersonalSeguridadDTOSaes
                                                                         newPersonalSeguridadDTOSaes) {
        return this.personaService.newPersonalSeguridad(newPersonalSeguridadDTOSaes);
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
