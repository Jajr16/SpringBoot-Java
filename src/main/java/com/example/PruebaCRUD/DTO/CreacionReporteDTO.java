package com.example.PruebaCRUD.DTO;

public class CreacionReporteDTO {

    private String mensaje;

    public CreacionReporteDTO(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
