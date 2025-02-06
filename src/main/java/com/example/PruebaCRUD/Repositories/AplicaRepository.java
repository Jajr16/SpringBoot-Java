package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Aplica;
import com.example.PruebaCRUD.BD.PKCompuesta.AplicaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AplicaRepository extends JpaRepository<Aplica, AplicaPK> {

}
