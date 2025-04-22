package com.example.PruebaCRUD.DTO;

public class DocenteDTO {
    private String rfcDocente;
    private String nombreDocente;

    public DocenteDTO(String rfcDocente, String nombreDocente) {
        this.rfcDocente = rfcDocente;
        this.nombreDocente = nombreDocente;
    }

    public String getRfcDocente() {
        return rfcDocente;
    }

    public void setRfcDocente(String rfcDocente) {
        this.rfcDocente = rfcDocente;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    @Override
    public String toString() {
        return "DocenteDTO{" +
                "rfcDocente='" + rfcDocente + '\'' +
                ", nombreDocente='" + nombreDocente + '\'' +
                '}';
    }
}
