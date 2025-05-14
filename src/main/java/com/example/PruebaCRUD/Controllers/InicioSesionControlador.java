package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.InicioSesionRespuestaDTO;
import com.example.PruebaCRUD.DTO.InicioSesionPeticionDTO;
import com.example.PruebaCRUD.Services.InicioSesionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping(path = "/login") // Mapear la url a este método
public class InicioSesionControlador {
    private final InicioSesionServicio inicioSesionServicio;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public InicioSesionControlador(InicioSesionServicio inicioSesionServicio) {
        this.inicioSesionServicio = inicioSesionServicio;
    }

    @PostMapping // Notación para manejar solicitudes POST
    public ResponseEntity<InicioSesionRespuestaDTO> inicioSesion(@RequestBody InicioSesionPeticionDTO peticion) {
        InicioSesionRespuestaDTO respuesta = inicioSesionServicio.inicioSesion(peticion.getUsuario(), peticion.getPassword());
        System.out.println(respuesta);
        if (respuesta.getError_Code() != 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
        }

        return ResponseEntity.ok(respuesta);
    }

}

