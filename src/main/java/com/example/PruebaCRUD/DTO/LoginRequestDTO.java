package com.example.PruebaCRUD.DTO;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a obtener
 * datos necesarios para el Login
 */
public class LoginRequestDTO {
    private String Usuario;
    private String Password;

    // ==================== CONSTRUCTORES =====================
    public LoginRequestDTO() {}

    public LoginRequestDTO(String username, String password) {
        this.Usuario = username;
        this.Password = password;
    }

    // ==================== SETTERS AND GETTERS ====================
    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "LoginRequestDTO{" +
                "usuario='" + Usuario + '\'' +
                ", password='" + Password + '\'' +
                '}';
    }
}
