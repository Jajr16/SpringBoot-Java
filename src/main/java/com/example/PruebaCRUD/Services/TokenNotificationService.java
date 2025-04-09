package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.TokenNotificacion;
import com.example.PruebaCRUD.BD.Usuario;
import com.example.PruebaCRUD.DTO.TokenResponseDTO;
import com.example.PruebaCRUD.Repositories.TokenNotificacionRepository;
import com.example.PruebaCRUD.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class TokenNotificationService {
    private UsuarioRepository usuarioRepository;
    private TokenNotificacionRepository tokenNotificacionRepository;

    @Autowired
    public TokenNotificationService(UsuarioRepository usuarioRepository, TokenNotificacionRepository tokenNotificacionRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tokenNotificacionRepository = tokenNotificacionRepository;
    }

    public TokenResponseDTO registrarToken(String usuarioT, String token) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioT).orElseThrow();

            if (tokenNotificacionRepository.findByToken(token).isPresent()) {
                return new TokenResponseDTO("El token ya está registrado", 400);
            }

            TokenNotificacion tokenNotificacion = new TokenNotificacion();
            tokenNotificacion.setUsuario(usuario);
            tokenNotificacion.setToken(token);
            tokenNotificacionRepository.save(tokenNotificacion);

            return new TokenResponseDTO("Token registrado correctamente", 200);
        } catch (Exception e) {
            return new TokenResponseDTO("Error al registrar el token: " + e.getMessage(), 500);  // Internal Server Error
        }
    }
}
