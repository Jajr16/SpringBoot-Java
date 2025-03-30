package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.PythonResponseDTO;
import com.example.PruebaCRUD.Services.ImagenRedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/red/")
public class ImagenRedController {
    @Autowired
    private ImagenRedService imagenRedService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadImage(
            @RequestPart("image") MultipartFile file,
            @RequestPart("boleta") String boleta,
            @RequestPart("idETS") String idets) { // idets como String
        try {
            int idetsInt = Integer.parseInt(idets); // Convierte a int
            imagenRedService.guardarImagenYActualizarBD(file, boleta, idetsInt);
        } catch (NumberFormatException e) {
            // Manejo del error
            System.out.println("Error al convertir idets a entero: " + e.getMessage());
        }
    }

    @PostMapping("/python-response")
    public void recibirRespuestaPython(
            @RequestParam("boleta") String boleta,
            @RequestParam("idets") int idets,
            @RequestBody PythonResponseDTO pythonResponseDTO) {
        imagenRedService.actualizarResultadoRN(boleta, idets, pythonResponseDTO);
    }
}
