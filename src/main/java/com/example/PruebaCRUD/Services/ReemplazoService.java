package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.Reemplazo;
import com.example.PruebaCRUD.Repositories.ReemplazoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReemplazoService {

    private final ReemplazoRepository reemplazoRepository;

    @Autowired
    public ReemplazoService(ReemplazoRepository reemplazoRepository) {
        this.reemplazoRepository = reemplazoRepository;
    }

    public List<Reemplazo> obtenerReemplazosPorDocenteRFC(String docenteRFC) {
        return reemplazoRepository.findByDocenteRfc(docenteRFC);
    }
}
