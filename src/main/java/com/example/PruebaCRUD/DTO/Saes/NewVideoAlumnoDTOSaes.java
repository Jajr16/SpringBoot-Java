package com.example.PruebaCRUD.DTO.Saes;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de la tabla Alumno
 */
public class NewVideoAlumnoDTOSaes {
    private String boleta;
    private String credencial;

    // ==================== CONSTRUCTORES =====================
    public NewVideoAlumnoDTOSaes(String boleta, String credencial) {
        this.boleta = boleta;
        this.credencial = credencial;
    }

    // ==================== SETTERS AND GETTERS ====================

    public String getBoleta() {
        return boleta;
    }

    public void setBoleta(String boleta) {
        this.boleta = boleta;
    }

    public String getCredencial() {
        return credencial;
    }

    public void setCredencial(String credencial) {
        this.credencial = credencial;
    }

    @Override
    public String toString() {
        return "NewAlumnoDTOSaes{" +
                ", boleta='" + boleta + '\'' +
                ", credencial='" + credencial + '\'' +
                '}';
    }
}
