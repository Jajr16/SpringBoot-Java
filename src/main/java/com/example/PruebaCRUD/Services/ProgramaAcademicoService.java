package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.ProgramaAcademico;
import com.example.PruebaCRUD.Repositories.ProgramaAcademicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

// El Service es el CRUD de la clase
/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class ProgramaAcademicoService {
    HashMap<String, Object> datos = new HashMap<>();

    private final ProgramaAcademicoRepository programaAcademicoRepository;

    @Autowired // Notación que permite inyectar dependencias
    public ProgramaAcademicoService(ProgramaAcademicoRepository programaAcademicoRepository) {
        this.programaAcademicoRepository = programaAcademicoRepository;
    }

//    public ResponseEntity<Object> newProgramaAcademico(ProgramaAcademico programaAcademico) {
//        datos = new HashMap<>();
////
////        if (programaAcademico.getId_PA()) {
////
////        }
//        return "Si"
//    }
}
