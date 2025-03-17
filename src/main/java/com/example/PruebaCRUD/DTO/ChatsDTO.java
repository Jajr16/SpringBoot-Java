package com.example.PruebaCRUD.DTO;

import org.springframework.stereotype.Repository;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de los ETS
 */
public class ChatsDTO {
    private String destinatario;
    private String nombre;

    // ==================== CONSTRUCTORES =====================
    public ChatsDTO() {}

    public ChatsDTO(String destinatario, String nombre) {
        this.destinatario = destinatario;
        this.nombre = nombre;
    }

    // ==================== SETTERS AND GETTERS ====================
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    @Override
    public String toString() {
        return "{" +
                "destinatario='" + destinatario + '\'' +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
