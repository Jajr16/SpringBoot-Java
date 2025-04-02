package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.MotivoRechazo;
import com.example.PruebaCRUD.BD.TipoEstado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotivoRechazoRepository extends JpaRepository<MotivoRechazo, Integer> {
}
