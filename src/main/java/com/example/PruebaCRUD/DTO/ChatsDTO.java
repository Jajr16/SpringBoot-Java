package com.example.PruebaCRUD.DTO;

import org.springframework.stereotype.Repository;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de los ETS
 */
public class ChatsDTO {
    private String usuario;
    private String nombre;

    public ChatsDTO() {}

    public ChatsDTO(String usuario, String nombre) {
        this.usuario = usuario;
        this.nombre = nombre;
    }


    // ==================== SETTERS AND GETTERS ====================
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "ChatsDTO{" +
                "usuario='" + usuario + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
