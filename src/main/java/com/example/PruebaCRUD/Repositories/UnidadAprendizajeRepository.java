package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.UnidadAprendizaje;
import com.example.PruebaCRUD.DTO.Saes.UnidadAprendizajeProjectionSaes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface UnidadAprendizajeRepository extends JpaRepository<UnidadAprendizaje, String> {
    // Notación findByAll proporcionada por JPA para obtener todos los registros de la tabla
    List<UnidadAprendizajeProjectionSaes> findAllBy();
}
