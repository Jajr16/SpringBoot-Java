package com.example.PruebaCRUD.DTO;

public class AlumnoDTO {
    private String boleta;
    private String nombreCompleto;

    // Constructor
    public AlumnoDTO(String boleta, String nombreCompleto) {
        this.boleta = boleta;
        this.nombreCompleto = nombreCompleto;
    }

    // Getters y Setters
    public String getBoleta() {
        return boleta;
    }

    public void setBoleta(String boleta) {
        this.boleta = boleta;
    }

    public String getnombreCompleto() {
        return nombreCompleto;
    }

    public String setnombreCompleto() {
        return nombreCompleto;
    }
}
