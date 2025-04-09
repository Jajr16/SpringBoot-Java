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

    private static final String DJANGO_SERVER_URL = "http://192.168.100.7:8000/api/obtener-imagen/"; // URL del servidor de Django

    // Método para obtener la foto del alumno desde Django
    public FotoAlumnoDTO obtenerFotoPorBoleta(String boleta) {
        // 1. Obtener la ruta desde la base de datos
        System.out.println("Hola");
        String rutaImagen = fotoAlumnoRepository.findRutaImagenByBoleta(boleta);

        if (rutaImagen == null || rutaImagen.isEmpty()) {
            throw new RuntimeException("Ruta de imagen no encontrada para la boleta: " + boleta);
        }



        // 2. Enviar la ruta al servidor Django para obtener la imagen
        byte[] imageBytes = obtenerImagenDesdeDjango(rutaImagen);

        // 3. Crear y devolver el DTO
        FotoAlumnoDTO fotoAlumnoDTO = new FotoAlumnoDTO();
        fotoAlumnoDTO.setBoleta(boleta);
        fotoAlumnoDTO.setFotoUrl(rutaImagen); // Opcional si quieres devolver la ruta

        return fotoAlumnoDTO;
    }

    public byte[] obtenerImagenDesdeDjango(String rutaImagen) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Crear el cuerpo de la petición con la ruta de la imagen
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Crear el cuerpo de la solicitud como un mapa y convertirlo a JSON
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("ruta_imagen", rutaImagen);

            // Asegúrate de que el cuerpo esté siendo correctamente serializado a JSON
            String jsonRequestBody = new ObjectMapper().writeValueAsString(requestBody);
            System.out.println("Cuerpo de la solicitud en JSON: " + jsonRequestBody);

            // Enviar la solicitud con el cuerpo y las cabeceras
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequestBody, headers);

            // Enviar la petición POST a Django
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    DJANGO_SERVER_URL,
                    HttpMethod.POST,
                    requestEntity,
                    byte[].class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Error al obtener la imagen desde Django");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al comunicarse con el servidor Django: " + e.getMessage());
        }
    }


}



