package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.LoginResponseDTO;
import com.example.PruebaCRUD.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class LoginService {
    private final UsuarioRepository usuarioRepository;

    @Autowired // Notación que permite inyectar dependencias
    public LoginService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Lógica del logeo de los usuarios en la app.
     *
     * @param username Nombre de usuario para el login
     * @param password Contraseña del usuario
     * @return LoginResponseDTO con el nombre de usuario, un mensaje y el rol del usuario
     */
    public LoginResponseDTO login(String username, String password) {
        System.out.println("EL USUARIO ES " + username);
        Object[] result = (Object[]) usuarioRepository.callLoginFunction(username, password);

        if (result == null) {
            return new LoginResponseDTO(0, "Error inesperado.");
        }

        System.out.println("EL RESULTADO DEL LOGIN FUE " + Arrays.toString(result));

        String message = (String) result[0];
        int error_code = (int) result[1];
        String role = (String) result[2];

        System.out.println("EL ROL DEL LOGIN FUE " + role);

        return new LoginResponseDTO(username, error_code, message, role);
    }


}
