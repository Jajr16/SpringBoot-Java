package com.example.PruebaCRUD.DTO.Saes;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de la tabla Alumno
 */
public class NuevoVideoAlumnoDTOSaes {
    private String boleta;
    private String credencial;
    private String curp;

    // ==================== CONSTRUCTORES =====================
    public NuevoVideoAlumnoDTOSaes(String boleta, String credencial, String curp) {
        this.boleta = boleta;
        this.credencial = credencial;
        this.curp = curp;
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

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    @Override
    public String toString() {
        return "NewVideoAlumnoDTOSaes{" +
                "boleta='" + boleta + '\'' +
                ", credencial='" + credencial + '\'' +
                ", curp='" + curp + '\'' +
                '}';
    }
}
