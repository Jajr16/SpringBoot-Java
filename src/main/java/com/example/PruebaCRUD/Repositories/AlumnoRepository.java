package com.example.PruebaCRUD.Repositories;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.PruebaCRUD.BD.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, String> {
    Optional<Alumno> findByBoleta(String Boleta);


}
