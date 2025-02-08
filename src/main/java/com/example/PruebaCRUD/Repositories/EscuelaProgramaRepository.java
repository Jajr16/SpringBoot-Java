package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.EscuelaPrograma;
import com.example.PruebaCRUD.BD.PKCompuesta.EscuelaProgramaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface EscuelaProgramaRepository extends JpaRepository<EscuelaPrograma, EscuelaProgramaPK> {
    // Notación findBy(Columna con primera mayúscula) proporcionada por JPA
    List<EscuelaPrograma> findByIdUANombre(String nombre);

    List<EscuelaPrograma> findByIdPAcadNombre(String nombre);

    List<EscuelaPrograma> findByIdUANombreAndIdPAcadNombre(String nombreEscuela, String nombrePrograma);
}
