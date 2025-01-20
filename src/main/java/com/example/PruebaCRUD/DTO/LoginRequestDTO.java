package com.example.PruebaCRUD.DTO;

public class LoginRequestDTO {
    private String Usuario;
    private String Password;

    public LoginRequestDTO() {}

    public LoginRequestDTO(String username, String password) {
        this.Usuario = username;
        this.Password = password;
    }

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
