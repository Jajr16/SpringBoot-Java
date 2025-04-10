package com.example.PruebaCRUD.DTO;

import java.time.LocalDateTime;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * los mensajes
 */
public class MensajeDTO {
    private String usuario;
    private LocalDateTime fecha;
    private String mensaje;

// ==================== CONSTRUCTORES =====================
    public MensajeDTO() {}

    public MensajeDTO(String remitente, LocalDateTime fecha, String mensaje) {
        this.usuario = remitente;
        this.fecha = fecha;
        this.mensaje = mensaje;
    }

// ==================== SETTERS AND GETTERS ====================
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    // ==================== TO STRING ====================
    @Override
    public String toString() {
        return "MensajeDTO{" +
                "usuario='" + usuario + '\'' +
                ", fecha=" + fecha +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
