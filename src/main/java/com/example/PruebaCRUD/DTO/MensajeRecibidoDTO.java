package com.example.PruebaCRUD.DTO;

import com.example.PruebaCRUD.Repositories.MensajeRepository;

public class MensajeRecibidoDTO {
    private String remitente;
    private String destinatario;
    private String mensaje;

    // ==================== CONSTRUCTORES =====================
    public MensajeRecibidoDTO() {}

    public MensajeRecibidoDTO(String remitente, String destinatario, String mensaje) {
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.mensaje = mensaje;
    }

    // ==================== SETTERS AND GETTERS ====================
    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    // ==================== TO STRING ====================
    @Override
    public String toString() {
        return "MensajeRecibidoDTO{" +
                "remitente='" + remitente + '\'' +
                ", destinatario='" + destinatario + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
