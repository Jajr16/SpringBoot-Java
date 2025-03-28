package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.TipoEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEstadoRepository extends JpaRepository<TipoEstado, Integer> {

}
