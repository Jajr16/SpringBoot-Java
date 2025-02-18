package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.Repositories.UsuarioRepository;
import com.example.PruebaCRUD.BD.Usuario;
import com.example.PruebaCRUD.Services.ListETSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionesController {
    private final ListETSService listETSService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public InscripcionesController(ListETSService listETSService, UsuarioRepository usuarioRepository) {
        this.listETSService = listETSService;
        this.usuarioRepository = usuarioRepository;
    }

    // Validar inscripción de un alumno por BOLETA (sin cambios)
    @GetMapping("/confirm/boleta/{boleta}")
    public Map<String, Boolean> confirmIns(@PathVariable("boleta") String boleta) {
        boolean message = listETSService.confirmInscripcion(boleta);
        Map<String, Boolean> response = new HashMap<>();
        response.put("message", message);
        return response;
    }


    @GetMapping("/confirm/username/{username}")
    public Map<String, Object> confirmValid(@PathVariable("username") String username) {
        Map<String, Object> response = new HashMap<>();

        // Buscar usuario por username
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuario(username);

        if (usuarioOpt.isPresent()) {
            String tipoUsuario = usuarioOpt.get().getTipoU().getTipo(); // Obtener tipo de usuario

            // Validación flexible: acepta cualquier rol que contenga 'Docente' o 'Seguridad'
            if (tipoUsuario.toLowerCase().contains("docente") ||
                    tipoUsuario.toLowerCase().contains("seguridad")) {

                response.put("username", username);
                response.put("tipoUsuario", tipoUsuario);
                response.put("mensaje", "Usuario válido");
            } else {
                response.put("error", "El usuario no corresponde a un Docente o Seguridad");
            }
        } else {
            response.put("error", "Usuario no encontrado");
        }

        return response;
    }
}
