package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.DocenteDTO;
import com.example.PruebaCRUD.Repositories.PersonalAcademicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonalAcademicoService {

    private final PersonalAcademicoRepository personalAcademicoRepository;

    @Autowired
    public PersonalAcademicoService(PersonalAcademicoRepository personalAcademicoRepository) {
        this.personalAcademicoRepository = personalAcademicoRepository;
    }

    /**
     * Obtiene todos los docentes con su información básica en formato DTO
     * @return Lista de DocenteDTO con RFC y nombre completo
     */
    public List<DocenteDTO> obtenerTodosLosDocentes() {
        return personalAcademicoRepository.findDocentes();
    }
}