package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.InscripcionETS;
import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscripcionETSRepository extends JpaRepository<InscripcionETS, InscripcionETSPK> {
}
