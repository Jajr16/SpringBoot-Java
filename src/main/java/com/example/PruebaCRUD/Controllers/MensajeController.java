package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.BD.Usuario;
import com.example.PruebaCRUD.DTO.ListadoUsuariosDTO;
import com.example.PruebaCRUD.DTO.MensajeDTO;
import com.example.PruebaCRUD.DTO.MensajeRecibidoDTO;
import com.example.PruebaCRUD.Repositories.UsuarioRepository;
import com.example.PruebaCRUD.Services.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> enviarMensaje(@RequestBody MensajeRecibidoDTO request) {
        try {
            Usuario remi = usuarioRepository.findById(request.getRemitente())
                    .orElseThrow(() -> new RuntimeException("Remitente no encontrado"));
            Usuario desti = usuarioRepository.findById(request.getDestinatario())
                    .orElseThrow(() -> new RuntimeException("Destinatario no encontrado"));

            mensajeService.enviarMensaje(remi, desti, request.getMensaje());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/PersonasToChat")
    public List<ListadoUsuariosDTO> getUsersToChat() {
        return this.mensajeService.getUsers();
    }

    @GetMapping("/{usuario}")
    public ResponseEntity<Object> getChat(@PathVariable String usuario) {
        return this.mensajeService.getChat(usuario);
    }

    @GetMapping("/historial/{remitente}/{destinatario}")
    public ResponseEntity<List<MensajeDTO>> getHistorial(@PathVariable String remitente, @PathVariable String destinatario) {
        return mensajeService.getHistorialMensajes(remitente, destinatario);
    }
}