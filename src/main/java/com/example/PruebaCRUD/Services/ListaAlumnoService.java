package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.Sexo;
import com.example.PruebaCRUD.DTO.ListAlumnosDTO;
import com.example.PruebaCRUD.Repositories.ListaAlumnosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListaAlumnoService {

    private final ListaAlumnosRepository listaAlumnosRepository;

    @Autowired
    public ListaAlumnoService(ListaAlumnosRepository listaAlumnosRepository) {this.listaAlumnosRepository = listaAlumnosRepository;}

    public List<ListAlumnosDTO> ListarAlumnos(Integer idetss) {
        List<Object[]> results = listaAlumnosRepository.callObtenerAsistenciaDetalles(idetss);

        List<ListAlumnosDTO> responseList = new ArrayList<>();

        System.out.println("resultado" + results);

        // Iterar sobre cada resultado y mapearlo a un DTO
        for (Object[] result : results) {
            Integer idets = (Integer) result[0];

            String boleta = (String) result[1];
            String curp = (String) result[2];
            String nombreA = (String) result[3];
            String apellidoP = (String) result[4];
            String apellidoM = (String) result[5];

            String sexo = (String) result[6];

            String correo = (String) result[7];
            String carrera = (String) result[8];

            Boolean aceptado = (Boolean) result[9];

            // Crear un DTO y agregarlo a la lista de respuestas
            responseList.add(new ListAlumnosDTO(idets, boleta, curp, nombreA, apellidoP, apellidoM,sexo,correo,carrera,aceptado));
        }



        return responseList;

    }

}
