package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.LoginResponseDTO;
import com.example.PruebaCRUD.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public LoginService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponseDTO login(String username, String password) {
        Object[] result = (Object[]) usuarioRepository.callLoginFunction(username, password);

        if (result == null) {
            return new LoginResponseDTO(0, "Error inesperado.");
        }

        String message = (String) result[0];
        int error_code = (int) result[1];
        String role = (String) result[2];

        return new LoginResponseDTO(username, error_code, message, role);
    }


}
