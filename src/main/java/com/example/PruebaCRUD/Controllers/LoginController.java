package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.LoginResponseDTO;
import com.example.PruebaCRUD.DTO.LoginRequestDTO;
import com.example.PruebaCRUD.Services.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "login")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        System.out.println("AQUÍ DEBE DE SER " + request);
        LoginResponseDTO response = loginService.login(request.getUsuario(), request.getPassword());
        System.out.println(response);
        if (response.getError_Code() != 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        return ResponseEntity.ok(response);
    }

}

