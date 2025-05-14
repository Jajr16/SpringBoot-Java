package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.MotivoRechazo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotivoRechazoRepositorio extends JpaRepository<MotivoRechazo, Integer> {
}
