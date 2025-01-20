package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.ETS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ETSRepository extends JpaRepository<ETS, Integer> {
}
