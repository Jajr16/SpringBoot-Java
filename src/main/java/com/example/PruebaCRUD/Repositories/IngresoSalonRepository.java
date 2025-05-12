package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.IngresoSalon;
import com.example.PruebaCRUD.BD.PKCompuesta.BoletaETSPK;
import com.example.PruebaCRUD.BD.ResultadoRN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngresoSalonRepository extends JpaRepository<IngresoSalon, BoletaETSPK> {
    @Query(value = "SELECT verificar_ingreso_salon(:boleta, :idets)", nativeQuery = true)
    boolean verificarIngresoFuncion(@Param("boleta") String boleta, @Param("idets") Integer idets);

    @Query(value = "SELECT eliminar_reporte_alumno(:idets, :boleta)", nativeQuery = true)
    boolean eliminarReporteAlumno(@Param("idets") Integer idets, @Param("boleta") String boleta);
}

