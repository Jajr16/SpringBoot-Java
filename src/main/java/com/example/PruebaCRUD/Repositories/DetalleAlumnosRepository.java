package com.example.PruebaCRUD.Repositories;

//import com.example.PruebaCRUD.BD.InscripcionETS;
//import com.example.PruebaCRUD.DTO.DetalleAlumnosDTO;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface DetalleAlumnosRepository extends JpaRepository<InscripcionETS, Long> {
////checar aplica
//    @Query("SELECT new com.example.PruebaCRUD.DTO.DetalleAlumnosDTO(" +
//            "a.imagenCredencial, " +
//            "a.boleta, " +
//            "p.nombre, " +
//            "p.apellido_p as apellidoP, " +
//            "p.apellido_m as apellidoM, " +
//            "e.idETS, " +
//            "pp.nombre, " +
//            "pp.apellido_p as apellidoP, " +
//            "pp.apellido_m as apellidoM, " +
//            "t.nombre, " +
//            "e.fecha) " +
//            "FROM InscripcionETS i " +
//            "JOIN i.boletaIns a " +
//            "JOIN a.CURP p " +
//            "JOIN i.idETSIns e " +
//            "JOIN e.Turno t " +
//            "JOIN e.aplica ap " +
//            "JOIN ap.docenteRFC pa " +
//            "JOIN pa.CURP pp " +
//            "WHERE a.boleta = :boleta")
//    List<DetalleAlumnosDTO> findDetalleAlumnoporboleta(@Param("boleta") String boleta);
//}
