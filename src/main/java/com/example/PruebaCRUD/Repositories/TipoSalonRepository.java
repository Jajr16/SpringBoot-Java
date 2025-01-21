package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.TipoSalon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoSalonRepository extends JpaRepository<TipoSalon, Integer> {
    Optional<TipoSalon> findByTipo(String Tipo);
}
