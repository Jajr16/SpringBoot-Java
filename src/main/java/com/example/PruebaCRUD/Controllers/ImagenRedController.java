package com.example.PruebaCRUD.Controllers;


import com.example.PruebaCRUD.DTO.CreacionReporteDTO;
import com.example.PruebaCRUD.Services.ImagenRedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


@RestController
@RequestMapping("/red/")
public class ImagenRedController {

    private static final Logger logger = LoggerFactory.getLogger(ImagenRedController.class);

    @Autowired
    private ImagenRedService imagenRedService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreacionReporteDTO> uploadImage(
            @RequestPart("razon") Optional<String> razon,
            @RequestPart("tipo") String tipo,
            @RequestPart("boleta") String boleta,
            @RequestPart("idETS") String idets,
            @RequestPart("precision") Optional<String> precision,
            @RequestPart("hora") String hora,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        try {
            int idetsInt = Integer.parseInt(idets);

            String razonValue = precision.orElse(null);

            String precisionValue = precision.orElse(null);

            // Llama al servicio con los datos
            String mensaje = imagenRedService.guardarImagenYActualizarBD(imagen, boleta, idetsInt, razonValue, tipo, hora, precisionValue);

            // Log para indicar que la solicitud se ha procesado
            logger.info("Solicitud de carga de imagen procesada para boleta: {}", boleta);

            CreacionReporteDTO respuesta = new CreacionReporteDTO(mensaje); // Envía el mensaje del servicio
            return ResponseEntity.ok(respuesta);

        } catch (NumberFormatException e) {
            logger.error("Error al convertir idets a entero: {}", e.getMessage());
            CreacionReporteDTO errorRespuesta = new CreacionReporteDTO("Error: idETS debe ser un número entero.");
            return ResponseEntity.badRequest().body(errorRespuesta);
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud: {}", e.getMessage());
            CreacionReporteDTO errorRespuesta = new CreacionReporteDTO("Error interno del servidor.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorRespuesta);
        }
    }
}