package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.FotoAlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FotoAlumnoService {

    @Autowired
    private FotoAlumnoRepository fotoAlumnoRepository;

    public String obtenerUrlPorBoleta(String boleta) {
        String rutaImagen = fotoAlumnoRepository.findRutaImagenByBoleta(boleta);

        if (rutaImagen == null || rutaImagen.isEmpty()) {
            throw new RuntimeException("Ruta de imagen no encontrada para la boleta: " + boleta);
        }

        return rutaImagen;
    }
}





