package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.TipoPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPersonalRepository extends JpaRepository<TipoPersonal, Integer> {
}
