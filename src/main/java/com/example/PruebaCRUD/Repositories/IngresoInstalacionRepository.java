package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.IngresoInstalacion;
import com.example.PruebaCRUD.BD.PKCompuesta.IngresoInstalacionPK;
import com.example.PruebaCRUD.DTO.IngresoInstalacionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface IngresoInstalacionRepository extends JpaRepository<IngresoInstalacion, IngresoInstalacionPK> {

    @Query("SELECT DISTINCT new com.example.PruebaCRUD.DTO.IngresoInstalacionDTO(" +
            "a.boleta, " +
            "p.nombre, " +
            "p.apellido_p, " +
            "p.apellido_m, " +
            "CAST(e.id_ETS AS string), " +
            "ii.id.fecha) " +  // Se eliminó la coma que causaba error
            "FROM InscripcionETS ie " +
            "JOIN ie.boletaIns a " +
            "JOIN a.CURP p " +
            "JOIN ie.idETSIns e " +
            "JOIN IngresoInstalacion ii ON ii.id.boleta = a.boleta " +
            "AND CAST(ii.id.idets AS integer) = e.id_ETS " +
            "WHERE a.boleta = :boleta")
    List<IngresoInstalacionDTO> findAlumnosInscritosETS(@Param("boleta") String boleta);
}