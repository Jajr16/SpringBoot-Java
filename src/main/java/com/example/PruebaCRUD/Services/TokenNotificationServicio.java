package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.TokenNotificacion;
import com.example.PruebaCRUD.BD.Usuario;
import com.example.PruebaCRUD.DTO.TokenRespuestaDTO;
import com.example.PruebaCRUD.Repositories.TokenNotificacionRepositorio;
import com.example.PruebaCRUD.Repositories.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenNotificationServicio {
    private final UsuarioRepositorio usuarioRepository;
    private final TokenNotificacionRepositorio tokenNotificacionRepositorio;

    @Autowired
    public TokenNotificationServicio(UsuarioRepositorio usuarioRepository, TokenNotificacionRepositorio tokenNotificacionRepositorio) {
        this.usuarioRepository = usuarioRepository;
        this.tokenNotificacionRepositorio = tokenNotificacionRepositorio;
    }

    public TokenRespuestaDTO registrarToken(String usuarioT, String token) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioT)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Buscar si existe un token para este usuario
            var tokenExistente = tokenNotificacionRepositorio.findByUsuarioUsuario(usuarioT);

            if (tokenExistente.isPresent()) {
                // Actualizar el token existente
                TokenNotificacion tokenNotificacion = tokenExistente.get();
                tokenNotificacion.setToken(token);
                tokenNotificacionRepositorio.save(tokenNotificacion);
                return new TokenRespuestaDTO("Token actualizado correctamente", 200);
            }

            // Si no existe, crear un nuevo registro
            TokenNotificacion tokenNotificacion = new TokenNotificacion();
            tokenNotificacion.setUsuario(usuario);
            tokenNotificacion.setToken(token);
            tokenNotificacionRepositorio.save(tokenNotificacion);

            return new TokenRespuestaDTO("Token registrado correctamente", 200);
        } catch (Exception e) {
            return new TokenRespuestaDTO("Error al registrar el token: " + e.getMessage(), 500);
        }
    }
}