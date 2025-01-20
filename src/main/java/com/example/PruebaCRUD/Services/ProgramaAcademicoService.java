package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.ProgramaAcademicoRepository;

import java.util.HashMap;
// El Service es el CRUD de la clase
public class ProgramaAcademicoService {
    HashMap<String, Object> datos = new HashMap<>();

    private final ProgramaAcademicoRepository programaAcademicoRepository;

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
