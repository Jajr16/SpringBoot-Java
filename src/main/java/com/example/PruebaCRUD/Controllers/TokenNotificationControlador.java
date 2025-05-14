package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.TokenRespuestaDTO;
import com.example.PruebaCRUD.Services.TokenNotificationServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping(path = "/" +
        "notificaciones") // Mapear la url a este método
public class TokenNotificationControlador {
    private final TokenNotificationServicio tokenNotificationServicio;

    @Autowired
    public TokenNotificationControlador(TokenNotificationServicio tokenNotificationServicio){
        this.tokenNotificationServicio = tokenNotificationServicio;
    }

    @PostMapping("/register")
    public TokenRespuestaDTO registrarToken(@RequestParam String usuario, @RequestParam String token) {
        return this.tokenNotificationServicio.registrarToken(usuario, token);
    }
}
