package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FotoAlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    public String obtenerUrlPorBoleta(String boleta) {
        String rutaImagen = alumnoRepository.findRutaImagenByBoleta(boleta);

        if (rutaImagen == null || rutaImagen.isEmpty()) {
            throw new RuntimeException("Ruta de imagen no encontrada para la boleta: " + boleta);
        }

        return rutaImagen;
    }
}



