package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.PKCompuesta.SalonETSPK;
import com.example.PruebaCRUD.BD.SalonETS;
import com.example.PruebaCRUD.DTO.SalonesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalonETSRepository extends JpaRepository<SalonETS, SalonETSPK> {
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
