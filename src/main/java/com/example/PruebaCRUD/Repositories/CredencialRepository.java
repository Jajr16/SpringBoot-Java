package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Alumno;
import com.example.PruebaCRUD.DTO.CredencialDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CredencialRepository extends JpaRepository<Alumno, String> {
    @Query("SELECT new com.example.PruebaCRUD.DTO.CredencialDTO(" +
            "a.imagenCredencial, " +
            "p.nombre, " +
            "p.apellido_p as apellidoP, " +
            "p.apellido_m as apellidoM, " +
            "a.boleta, " +
            "p.CURP as curp, " +
            "pa.nombre, " +
            "ua.nombre ) " +
            "FROM Alumno a " +
            "JOIN a.CURP p " +
            "JOIN a.idPA pa " +
            "JOIN EscuelaPrograma ep ON ep.idPAcad = pa " +
            "JOIN ep.idUA ua " +
            "WHERE a.boleta = :boleta")
    List<CredencialDTO> findbyBoleta(@Param("boleta") String boleta);

}

