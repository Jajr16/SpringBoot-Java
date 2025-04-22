package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.DTO.Saes.*;
import com.example.PruebaCRUD.Services.AlumnoService;
import com.example.PruebaCRUD.Services.DocenteService;
import com.example.PruebaCRUD.Services.PersonaService;
import com.example.PruebaCRUD.Services.PersonalSeguridadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/saes") // Mapear la url a este método
public class PersonaControllerSaes {
    private final PersonaService personaService;
    private final PersonalSeguridadService personalSeguridadService;
    private final DocenteService docenteService;
    private final AlumnoService alumnoService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public PersonaControllerSaes(PersonaService personaService, PersonalSeguridadService personalSeguridadService, DocenteService docenteService, AlumnoService alumnoService) {
        this.personaService = personaService;
        this.personalSeguridadService = personalSeguridadService;
        this.docenteService = docenteService;
        this.alumnoService = alumnoService;
    }

    @PostMapping("/nAlumno")
    public ResponseEntity<Object> newAlumno(@ModelAttribute NewAlumnoDTOSaes newAlumnoDTOSaes,
                                            @RequestParam("video")MultipartFile video,
                                            @RequestParam("credencial")MultipartFile credencial) throws IOException,
            ExecutionException, InterruptedException {
        return this.alumnoService.newAlumno(newAlumnoDTOSaes, video, credencial);
    }

    @GetMapping("/check-volume")
    public String testVolumen() {
        File dir = new File("/data/EntrenamientoIMG");
        return "Existe: " + dir.exists() + " - Puedo escribir: " + dir.canWrite();
    }

    @GetMapping("/docentes")
    public ResponseEntity<List<DocentesDTOSaes>> getDocentes(){
        List<DocentesDTOSaes> response = this.docenteService.getDocentes();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/nd")
    public ResponseEntity<Object> registrarDocentes(@RequestBody NewDocentesDTOSaes newDocentesDTOSaes) {
        return this.docenteService.newDocente(newDocentesDTOSaes);
    }

    @GetMapping("/ps")
    public ResponseEntity<List<PersonalSeguridadDTOSaes>> getPS(){
        List<PersonalSeguridadDTOSaes> response = this.personalSeguridadService.getPS();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/nps")
    public ResponseEntity<Object> registrarPersonalSeguridad(@RequestBody NewPersonalSeguridadDTOSaes
                                                                         newPersonalSeguridadDTOSaes) {
        return this.personalSeguridadService.newPersonalSeguridad(newPersonalSeguridadDTOSaes);
    }

    @GetMapping("/DocenteToETS")
    public ResponseEntity<List<DocentesDTOToETS>> getDocenteToETS() {
        List<DocentesDTOToETS> response = this.docenteService.getDocentesToETS();
        System.out.println("AQUI EL DOCENTE ES " + response);

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }
}
