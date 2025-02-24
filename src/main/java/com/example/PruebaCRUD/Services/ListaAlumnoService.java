package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.ListaAlumnosRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ListaAlumnoService {

    private final ListaAlumnosRepository listaAlumnosRepository;

    @Autowired
    public ListaAlumnoService(ListaAlumnosRepository listaAlumnosRepository) {this.listaAlumnosRepository = listaAlumnosRepository;}

}
