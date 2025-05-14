package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.PKCompuesta.BoletaETSPK;
import com.example.PruebaCRUD.BD.ResultadoRN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoRNRepositorio extends JpaRepository<ResultadoRN, BoletaETSPK> {
}