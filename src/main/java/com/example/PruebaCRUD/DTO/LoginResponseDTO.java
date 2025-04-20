package com.example.PruebaCRUD.DTO;

import java.util.List;
import java.util.Optional;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de la respuesta del proceso de Login
 */
public class LoginResponseDTO {
    private String Usuario;
    private Integer Error_code;
    private String Message;
    private String Rol;
    private Optional<List<String>> cargos;

    // ==================== CONSTRUCTORES =====================
    public LoginResponseDTO() {}

    public LoginResponseDTO(String Usuario, Integer Error_Code, String Message, String rol) {
        this.Usuario = Usuario;
        this.Error_code = Error_Code;
        this.Message = Message;
        this.Rol = rol;
    }
    public LoginResponseDTO(String Usuario, Integer Error_Code, String Message, String rol, Optional<List<String>> cargos) {
        this.Usuario = Usuario;
        this.Error_code = Error_Code;
        this.Message = Message;
        this.Rol = rol;
        this.cargos = cargos;
    }

    public LoginResponseDTO(Integer Error_Code, String Message) {
        this.Error_code = Error_Code;
        this.Message = Message;
    }

    // ==================== SETTERS AND GETTERS ====================
    public Integer getError_Code() {
        return Error_code;
    }

    public void setError_Code(Integer error_Code) {
        Error_code = error_Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public Integer getError_code() {
        return Error_code;
    }

    public void setError_code(Integer error_code) {
        Error_code = error_code;
    }

    public Optional<List<String>> getCargos() {
        return cargos;
    }

    public void setCargos(Optional<List<String>> cargos) {
        this.cargos = cargos;
    }

    @Override
    public String toString() {
        return "LoginResponseDTO{" +
                "Usuario='" + Usuario + '\'' +
                ", Error_code=" + Error_code +
                ", Message='" + Message + '\'' +
                ", Rol='" + Rol + '\'' +
                ", cargos=" + cargos +
                '}';
    }
}
