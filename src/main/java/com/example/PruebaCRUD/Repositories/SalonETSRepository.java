package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.PKCompuesta.SalonETSPK;
import com.example.PruebaCRUD.BD.SalonETS;
import com.example.PruebaCRUD.DTO.SalonesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface SalonETSRepository extends JpaRepository<SalonETS, SalonETSPK> {
    /**
     * En lugar de hacer la notación de findBy que nos proporciona JPA, se realiza una consulta más detallada y
     * personalizable con las clases del proyecto
     */
    @Query("""
            SELECT new com.example.PruebaCRUD.DTO.SalonesDTO(
                         salon.numSalon,\s
                         salon.Edificio,\s
                         salon.Piso,\s
                         tipoSalon.tipo\s
                     )
                     FROM SalonETS salonETS
                     JOIN salonETS.numSalonSETS salon 
                     JOIN salon.tipoSalon tipoSalon   
                     WHERE salonETS.idETSSETS.id_ETS = :idets
            """)
    List<SalonesDTO> findByIdETSSETS(Integer idets);
}
