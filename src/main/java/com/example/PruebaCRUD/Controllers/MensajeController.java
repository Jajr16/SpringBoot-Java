package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.BD.Usuario;
import com.example.PruebaCRUD.DTO.ListadoUsuariosDTO;
import com.example.PruebaCRUD.Repositories.UsuarioRepository;
import com.example.PruebaCRUD.Services.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensajes")
public class MensajeController {
    private MensajeService mensajeService;

    private UsuarioRepository usuarioRepository;

    @Autowired
    public MensajeController(MensajeService mensajeService, UsuarioRepository usuarioRepository) {
        this.mensajeService = mensajeService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/enviar")
    public ResponseEntity<Void> enviarMensaje(@RequestParam String remitenteId,
                                              @RequestParam String destinatarioId,
                                              @RequestParam String contenido) {
        Usuario remitente = usuarioRepository.findById(remitenteId).orElseThrow();
        Usuario destinatario = usuarioRepository.findById(destinatarioId).orElseThrow();

        mensajeService.enviarMensaje(remitente, destinatario, contenido);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/PersonasToChat")
    public List<ListadoUsuariosDTO> getChats() {
        return this.mensajeService.getUsers();
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> getConfirmacionMensajes(@RequestBody String usuario) {
        return this.mensajeService.getConfirmacion(usuario);
    }
}