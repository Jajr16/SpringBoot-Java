package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FotoAlumnoRepository extends JpaRepository<Alumno, String> {

    @Query("SELECT a.imagenCredencial FROM Alumno a WHERE a.boleta = :boleta")
    String findRutaImagenByBoleta(@Param("boleta") String boleta);
}

