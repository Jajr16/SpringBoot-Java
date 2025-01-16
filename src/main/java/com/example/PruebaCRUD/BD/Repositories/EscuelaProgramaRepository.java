package com.example.PruebaCRUD.BD.Repositories;

import com.example.PruebaCRUD.BD.EscuelaPrograma;
import com.example.PruebaCRUD.BD.PKCompuesta.EscuelaProgramaPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EscuelaProgramaRepository extends JpaRepository<EscuelaPrograma, EscuelaProgramaPK> {
    List<EscuelaPrograma> findByIdUANombre(String nombre);

    List<EscuelaPrograma> findByIdPAcadNombre(String nombre);

    List<EscuelaPrograma> findByIdUANombreAndIdPAcadNombre(String nombreEscuela, String nombrePrograma);
}
