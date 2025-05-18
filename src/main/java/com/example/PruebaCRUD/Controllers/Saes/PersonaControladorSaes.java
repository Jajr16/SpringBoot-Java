package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.DTO.Saes.*;
import com.example.PruebaCRUD.Services.AlumnoServicio;
import com.example.PruebaCRUD.Services.DocenteServicio;
import com.example.PruebaCRUD.Services.PersonalSeguridadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/saes") // Mapear la url a este método
public class PersonaControladorSaes {
    private final PersonalSeguridadServicio personalSeguridadServicio;
    private final DocenteServicio docenteServicio;
    private final AlumnoServicio alumnoServicio;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public PersonaControladorSaes(PersonalSeguridadServicio personalSeguridadServicio,
                                  DocenteServicio docenteServicio, AlumnoServicio alumnoServicio) {
        this.personalSeguridadServicio = personalSeguridadServicio;
        this.docenteServicio = docenteServicio;
        this.alumnoServicio = alumnoServicio;
    }

    @PostMapping("/nAlumno")
    public ResponseEntity<Object> nuevoAlumno(@ModelAttribute NuevoAlumnoDTOSaes nuevoAlumnoDTOSaes,
                                              @RequestParam("video")MultipartFile video,
                                              @RequestParam("credencial")MultipartFile credencial) throws IOException,
            ExecutionException, InterruptedException {
        return this.alumnoServicio.nuevoAlumno(nuevoAlumnoDTOSaes, video, credencial);
    }

    @GetMapping("/check-volume")
    public String volumenExiste() {
        File dir = new File("/data/EntrenamientoIMG");
        return "Existe: " + dir.exists() + " - Puedo escribir: " + dir.canWrite();
    }

    @GetMapping("/docentes")
    public ResponseEntity<List<DocentesDTOSaes>> obtenerDocentes(){
        List<DocentesDTOSaes> response = this.docenteServicio.obtenerDocentesSaes();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/nd")
    public ResponseEntity<Object> registrarDocentes(@RequestBody NuevoDocenteDTOSaes nuevoDocenteDTOSaes) {
        return this.docenteServicio.nuevoDocente(nuevoDocenteDTOSaes);
    }

    @GetMapping("/ps")
    public ResponseEntity<List<PersonalSeguridadDTOSaes>> obtenerPS(){
        List<PersonalSeguridadDTOSaes> response = this.personalSeguridadServicio.obtenerPS();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/nps")
    public ResponseEntity<Object> registrarPersonalSeguridad(@RequestBody NuevoPersonalSeguridadDTOSaes
                                                                     nuevoPersonalSeguridadDTOSaes) {
        return this.personalSeguridadServicio.nuevoPersonalSeguridad(nuevoPersonalSeguridadDTOSaes);
    }

    @GetMapping("/DocenteToETS")
    public ResponseEntity<List<DocentesDTOParaETS>> obtenerDocenteParaETS() {
        List<DocentesDTOParaETS> response = this.docenteServicio.obtenerDocentesParaETS();
        System.out.println("AQUI EL DOCENTE ES " + response);

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }
}
