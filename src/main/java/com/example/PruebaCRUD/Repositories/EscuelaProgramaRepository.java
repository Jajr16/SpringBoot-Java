package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.EscuelaPrograma;
import com.example.PruebaCRUD.BD.PKCompuesta.EscuelaProgramaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EscuelaProgramaRepository extends JpaRepository<EscuelaPrograma, EscuelaProgramaPK> {
    List<EscuelaPrograma> findByIdUANombre(String nombre);

    List<EscuelaPrograma> findByIdPAcadNombre(String nombre);

    List<EscuelaPrograma> findByIdUANombreAndIdPAcadNombre(String nombreEscuela, String nombrePrograma);
}
