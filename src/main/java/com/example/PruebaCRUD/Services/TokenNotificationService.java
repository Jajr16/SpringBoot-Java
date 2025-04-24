package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.TokenNotificacion;
import com.example.PruebaCRUD.BD.Usuario;
import com.example.PruebaCRUD.DTO.TokenResponseDTO;
import com.example.PruebaCRUD.Repositories.TokenNotificacionRepository;
import com.example.PruebaCRUD.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenNotificationService {
    private final UsuarioRepository usuarioRepository;
    private final TokenNotificacionRepository tokenNotificacionRepository;

    @Autowired
    public TokenNotificationService(UsuarioRepository usuarioRepository, TokenNotificacionRepository tokenNotificacionRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tokenNotificacionRepository = tokenNotificacionRepository;
    }

    public TokenResponseDTO registrarToken(String usuarioT, String token) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioT)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Buscar si existe un token para este usuario
            var tokenExistente = tokenNotificacionRepository.findByUsuarioUsuario(usuarioT);

            if (tokenExistente.isPresent()) {
                // Actualizar el token existente
                TokenNotificacion tokenNotificacion = tokenExistente.get();
                tokenNotificacion.setToken(token);
                tokenNotificacionRepository.save(tokenNotificacion);
                return new TokenResponseDTO("Token actualizado correctamente", 200);
            }

            // Si no existe, crear un nuevo registro
            TokenNotificacion tokenNotificacion = new TokenNotificacion();
            tokenNotificacion.setUsuario(usuario);
            tokenNotificacion.setToken(token);
            tokenNotificacionRepository.save(tokenNotificacion);

            return new TokenResponseDTO("Token registrado correctamente", 200);
        } catch (Exception e) {
            return new TokenResponseDTO("Error al registrar el token: " + e.getMessage(), 500);
        }
    }
}