package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Alumno;
import com.example.PruebaCRUD.BD.ProgramaAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstudianteEspecificoRepository extends JpaRepository<Alumno, ProgramaAcademico> {

    @Query(value = "SELECT * FROM buscardatosestudiante(:boleta)", nativeQuery = true)
    List<Object[]> callAlumnoEspecificoData(@Param("boleta") String boleta);



}
