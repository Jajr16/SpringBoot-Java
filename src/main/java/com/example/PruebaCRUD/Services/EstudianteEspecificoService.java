package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.EstudianteEspecificoDTO;
import com.example.PruebaCRUD.Repositories.EstudianteEspecificoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteEspecificoService {

    private final EstudianteEspecificoRepository estudianteEspecificoRepository;

    @Autowired
    public EstudianteEspecificoService(EstudianteEspecificoRepository estudianteEspecificoRepository){this.estudianteEspecificoRepository = estudianteEspecificoRepository;}


    // Método para obtener los datos de un estudiante específico por boleta
    public EstudianteEspecificoDTO obtenerEstudiantePorBoleta(String boleta) {
        List<Object[]> results = estudianteEspecificoRepository.callAlumnoEspecificoData(boleta);

        // Verificar si hay resultados
        if (!results.isEmpty()) {
            Object[] result = results.get(0);

            // Obtener los valores de las columnas que devuelve la consulta nativa
            String nombre = (String) result[4];
            String apellidoP = (String) result[2];
            String apellidoM = (String) result[3];
            String boletaResultado = (String) result[0];
            String curp = (String) result[1];
            String unidadAcademica = (String) result[5];


            // Crear y retornar el DTO
            return new EstudianteEspecificoDTO(nombre, apellidoP, apellidoM, boletaResultado, curp, unidadAcademica);
        }


        return null;
    }

}
