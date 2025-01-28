package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.CargoDocente;
import com.example.PruebaCRUD.BD.PKCompuesta.CargoDocentePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoDocenteRepository extends JpaRepository<CargoDocente, CargoDocentePK> {
}
