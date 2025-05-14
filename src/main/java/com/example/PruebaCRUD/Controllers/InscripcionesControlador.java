package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.BD.Usuario;
import com.example.PruebaCRUD.Repositories.UsuarioRepositorio;
import com.example.PruebaCRUD.Services.ListaETSServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/inscripciones") // Mapear la url a este método
public class InscripcionesControlador {
    private final ListaETSServicio listaETSServicio;
    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public InscripcionesControlador(ListaETSServicio listaETSServicio, UsuarioRepositorio usuarioRepositorio) {
        this.listaETSServicio = listaETSServicio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @GetMapping("/confirm/{boleta}") // Notación para manejar solicitudes GET
    public Map<String, Boolean> confirmarInscripcion(@PathVariable("boleta") String boleta) {
        boolean mensaje = listaETSServicio.confirmarInscripcion(boleta);

        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("message", mensaje);
        return respuesta;
    }

    @GetMapping("/confirm/username/{username}")
    public Map<String, Object> confirmarUsuarioValido(@PathVariable("username") String usuario) {
        Map<String, Object> response = new HashMap<>();

        // Buscar usuario por usuario
        Optional<Usuario> usuarioOpc = usuarioRepositorio.findByUsuario(usuario);

        if (usuarioOpc.isPresent()) {
            String tipoUsuario = usuarioOpc.get().getTipoU().getTipo(); // Obtener tipo de usuario

            // Validación flexible: acepta cualquier rol que contenga 'Docente' o 'Seguridad'
            if (tipoUsuario.toLowerCase().contains("personal academico") ||
                    tipoUsuario.toLowerCase().contains("seguridad") || tipoUsuario.toLowerCase().contains("alumno")) {

                response.put("username", usuario);
                response.put("tipoUsuario", tipoUsuario);
                response.put("mensaje", "Usuario válido");
            } else {
                response.put("error", "El usuario no corresponde a ningún rol");
            }
        } else {
            response.put("error", "Usuario no encontrado");
        }

        return response;
    }

}
