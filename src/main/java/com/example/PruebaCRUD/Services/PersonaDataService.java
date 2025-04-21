package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.DataPersonaDTO;
import com.example.PruebaCRUD.Repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonaDataService {

    private final PersonaRepository personaRepository;

    @Autowired
    public PersonaDataService(PersonaRepository personaRepository) {this.personaRepository = personaRepository;}

    public List<DataPersonaDTO> NombreUsuario(String usuario){

        List<Object[]> results = personaRepository.callobtenerpersona(usuario);

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
