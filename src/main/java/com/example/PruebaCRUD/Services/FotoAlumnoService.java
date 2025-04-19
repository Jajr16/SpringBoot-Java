package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.FotoAlumnoDTO;
import com.example.PruebaCRUD.Repositories.FotoAlumnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

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



