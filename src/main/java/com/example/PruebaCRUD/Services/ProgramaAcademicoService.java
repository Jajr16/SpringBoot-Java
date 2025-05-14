package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.EscuelaProgramaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// El Service es el CRUD de la clase
/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class ProgramaAcademicoService {
    private final EscuelaProgramaRepositorio escuelaProgramaRepositorio;

    @Autowired // Notación que permite inyectar dependencias
    public ProgramaAcademicoService(EscuelaProgramaRepositorio escuelaProgramaRepositorio) {
        this.escuelaProgramaRepositorio = escuelaProgramaRepositorio;
    }

    public List<?> getProgramasAcademicos(Integer escuela) {
        System.out.println("AQUI TENGO A " + escuela);
        return this.escuelaProgramaRepositorio.getEscuelaPrograma(escuela);
    }

    public List<?> getAllProgramasAcademicos() {
        return this.escuelaProgramaRepositorio.getEscuelasProgramas();
    }
}
