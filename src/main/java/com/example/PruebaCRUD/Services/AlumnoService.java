package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.AlumnoRepository;
import com.example.PruebaCRUD.Repositories.InscripcionETSRepository;
import com.example.PruebaCRUD.Repositories.periodoETSRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final InscripcionETSRepository inscripcionETSRepository;
    private final periodoETSRepository periodoETSRepository;

    public AlumnoService(AlumnoRepository alumnoRepository, InscripcionETSRepository inscripcionETSRepository, periodoETSRepository periodoETSRepository) {
        this.alumnoRepository = alumnoRepository;
        this.inscripcionETSRepository = inscripcionETSRepository;
        this.periodoETSRepository = periodoETSRepository;
    }

    public List<String> obtenerAlumnosInscritos(Date fecha, String periodo) {
        List<Object[]> resultados = inscripcionETSRepository.findAlumnosInscritosETS(fecha, periodo);
        List<String> lista = new ArrayList<>();

        for (Object[] row : resultados) {
            String alumno = "Boleta: " + row[0] + " - Nombre: " + row[1] + " " + row[2] + " " + row[3];
            lista.add(alumno);
        }
        return lista;
    }
}
