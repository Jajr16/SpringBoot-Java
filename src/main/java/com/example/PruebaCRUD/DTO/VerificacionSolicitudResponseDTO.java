package com.example.PruebaCRUD.DTO;

public class VerificacionSolicitudResponseDTO {
    private boolean tieneSolicitudPendiente;
    private SolicitudReemplazoDTO solicitudExistente;

    // Constructor vacío necesario para Jackson
    public VerificacionSolicitudResponseDTO() {}

    // Constructor completo
    public VerificacionSolicitudResponseDTO(boolean tieneSolicitudPendiente, SolicitudReemplazoDTO solicitudExistente) {
        this.tieneSolicitudPendiente = tieneSolicitudPendiente;
        this.solicitudExistente = solicitudExistente;
    }

    // Getters y Setters
    public boolean isTieneSolicitudPendiente() {
        return tieneSolicitudPendiente;
    }

    public void setTieneSolicitudPendiente(boolean tieneSolicitudPendiente) {
        this.tieneSolicitudPendiente = tieneSolicitudPendiente;
    }

    public SolicitudReemplazoDTO getSolicitudExistente() {
        return solicitudExistente;
    }

    public void setSolicitudExistente(SolicitudReemplazoDTO solicitudExistente) {
        this.solicitudExistente = solicitudExistente;
    }
}
