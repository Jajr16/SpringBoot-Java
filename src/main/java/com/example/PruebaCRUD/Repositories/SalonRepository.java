package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Salon;
import com.example.PruebaCRUD.DTO.Saes.SalonProjectionSaes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalonRepository extends JpaRepository<Salon, Integer> {
    Optional<Salon> findByNumSalon(Integer numsalon);

    List<SalonProjectionSaes> findAllBy();
}
