package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.PKCompuesta.PersonalSeguridadPK;
import com.example.PruebaCRUD.BD.PersonalSeguridad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalSeguridadRepository extends JpaRepository<PersonalSeguridad, PersonalSeguridadPK> {
}
