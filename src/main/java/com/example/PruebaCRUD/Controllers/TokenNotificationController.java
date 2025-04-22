package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.TokenResponseDTO;
import com.example.PruebaCRUD.Services.TokenNotificationService;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.engine.jdbc.mutation.group.PreparedStatementGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping(path = "/notificaciones") // Mapear la url a este método
public class TokenNotificationController {
    private final TokenNotificationService tokenNotificationService;

    @Autowired
    public TokenNotificationController(TokenNotificationService tokenNotificationService){
        this.tokenNotificationService = tokenNotificationService;
    }

    @PostMapping("/register")
    public TokenResponseDTO registrarToken(@RequestParam String usuario, @RequestParam String token) {
        return this.tokenNotificationService.registrarToken(usuario, token);
    }
}
