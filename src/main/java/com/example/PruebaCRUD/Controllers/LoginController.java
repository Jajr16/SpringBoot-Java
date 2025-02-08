package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.LoginResponseDTO;
import com.example.PruebaCRUD.DTO.LoginRequestDTO;
import com.example.PruebaCRUD.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping(path = "/login") // Mapear la url a este método
public class LoginController {
    private final LoginService loginService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping // Notación para manejar solicitudes POST
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = loginService.login(request.getUsuario(), request.getPassword());
        System.out.println(response);
        if (response.getError_Code() != 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        return ResponseEntity.ok(response);
    }

}

