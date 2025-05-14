package com.example.PruebaCRUD.Controllers;


import com.example.PruebaCRUD.DTO.CreacionReporteDTO;
import com.example.PruebaCRUD.Services.ImagenRedServicio;
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
public class ImagenRedControlador {

    private static final Logger registro = LoggerFactory.getLogger(ImagenRedControlador.class);

    @Autowired
    private ImagenRedServicio imagenRedServicio;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreacionReporteDTO> subirImagen(
            @RequestPart("razon") Optional<String> razon,
            @RequestPart("tipo") String tipo,
            @RequestPart("boleta") String boleta,
            @RequestPart("idETS") String idets,
            @RequestPart("precision") Optional<String> precision,
            @RequestPart("hora") String hora,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        try {
            int idetsInt = Integer.parseInt(idets);

            String razonValue = razon.orElse(null);

            String precisionValue = precision.orElse(null);

            // Llama al servicio con los datos
            String mensaje = imagenRedServicio.guardarImagenYActualizarBD(imagen, boleta, idetsInt, razonValue, tipo, hora, precisionValue);

            registro.info("Solicitud de carga de imagen procesada para boleta: {}", boleta);

            CreacionReporteDTO respuesta = new CreacionReporteDTO(mensaje);
            return ResponseEntity.ok(respuesta);

        } catch (NumberFormatException e) {
            registro.error("Error al convertir idets a entero: {}", e.getMessage());
            CreacionReporteDTO errorRespuesta = new CreacionReporteDTO("Error: idETS debe ser un número entero.");
            return ResponseEntity.badRequest().body(errorRespuesta);
        } catch (Exception e) {
            registro.error("Error al procesar la solicitud: {}", e.getMessage());
            CreacionReporteDTO errorRespuesta = new CreacionReporteDTO("Error interno del servidor.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorRespuesta);
        }
    }
}