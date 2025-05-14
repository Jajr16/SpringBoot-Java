package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.BD.Usuario;
import com.example.PruebaCRUD.DTO.ListadoUsuariosDTO;
import com.example.PruebaCRUD.DTO.MensajeDTO;
import com.example.PruebaCRUD.DTO.MensajeRecibidoDTO;
import com.example.PruebaCRUD.Repositories.UsuarioRepositorio;
import com.example.PruebaCRUD.Services.MensajeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mensajes")
public class MensajeControlador {
    private final MensajeServicio mensajeServicio;

    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public MensajeControlador(MensajeServicio mensajeServicio, UsuarioRepositorio usuarioRepositorio) {
        this.mensajeServicio = mensajeServicio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @PostMapping("/enviar")
    public ResponseEntity<Map<String, Object>> enviarMensaje(@RequestBody MensajeRecibidoDTO request) {
        try {
            Usuario remi = usuarioRepositorio.findById(request.getRemitente())
                    .orElseThrow(() -> new RuntimeException("Remitente no encontrado"));
            Usuario desti = usuarioRepositorio.findById(request.getDestinatario())
                    .orElseThrow(() -> new RuntimeException("Destinatario no encontrado"));

            mensajeServicio.enviarMensaje(remi, desti, request.getMensaje());

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("success", true);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("success", false);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/PersonasToChat/{usuario}")
    public List<ListadoUsuariosDTO> obtenerUsuariosParaChatear(@PathVariable String usuario) {
        return this.mensajeServicio.obtenerUsuarios(usuario);
    }

    @GetMapping("/{usuario}")
    public ResponseEntity<Object> obtenerChat(@PathVariable String usuario) {
        try {
            if (usuario == null || usuario.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Ocurrió un error, usuario inválido."));
            }

            return this.mensajeServicio.obtenerChat(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Error al obtener los chats",
                            "mensaje", e.getMessage()
                    ));
        }
    }

    @GetMapping("/historial/{remitente}/{destinatario}")
    public ResponseEntity<List<MensajeDTO>> obtenerHistorial(@PathVariable String remitente, @PathVariable String destinatario) {
        return mensajeServicio.obtenerHistorialMensajes(remitente, destinatario);
    }
}