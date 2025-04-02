package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.DataPersonaDTO;
import com.example.PruebaCRUD.DTO.ListAlumnosDTO;
import com.example.PruebaCRUD.Repositories.PersonaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonaDataService {

    private final PersonaDataRepository personaDataRepository;

    @Autowired
    public PersonaDataService(PersonaDataRepository personaDataRepository) {this.personaDataRepository = personaDataRepository;}

    public List<DataPersonaDTO> NombreUsuario(String usuario){

        List<Object[]> results = personaDataRepository.callobtenerpersona(usuario);

        List<DataPersonaDTO> responseList = new ArrayList<>();

        for (Object[] result : results) {
            String nombre = (String) result[0];
            String apellidoP = (String) result[1];
            String apellidoM = (String) result[2];

            responseList.add(new DataPersonaDTO(nombre, apellidoP, apellidoM));

        }

        return responseList;

    }
}
