package com.example.PruebaCRUD.ProgramaAcademico;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
// El Service es el CRUD de la clase
public class ProgramaAcademicoService {
    HashMap<String, Object> datos = new HashMap<>();

    private final ProgramaAcademicoRepository programaAcademicoRepository;

    public ProgramaAcademicoService(ProgramaAcademicoRepository programaAcademicoRepository) {
        this.programaAcademicoRepository = programaAcademicoRepository;
    }

    public ResponseEntity<Object> newProgramaAcademico(ProgramaAcademico programaAcademico) {
        datos = new HashMap<>();

        
    }
}
