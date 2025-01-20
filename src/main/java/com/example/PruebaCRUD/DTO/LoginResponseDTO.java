package com.example.PruebaCRUD.DTO;

public class LoginResponseDTO {
    private String Usuario;
    private Integer Error_Code;
    private String Message;
    private String Rol;

    public LoginResponseDTO() {}

    public LoginResponseDTO(String Usuario, Integer Error_Code, String Message, String rol) {
        this.Usuario = Usuario;
        this.Error_Code = Error_Code;
        this.Message = Message;
        this.Rol = rol;
    }

    public LoginResponseDTO(Integer Error_Code, String Message) {
        this.Error_Code = Error_Code;
        this.Message = Message;
    }

    public Integer getError_Code() {
        return Error_Code;
    }

    public void setError_Code(Integer error_Code) {
        Error_Code = error_Code;
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

    @Override
    public String toString() {
        return "LoginDTO{" +
                "Error_Code=" + Error_Code +
                ", Message='" + Message + '\'' +
                ", Rol='" + Rol + '\'' +
                '}';
    }
}
