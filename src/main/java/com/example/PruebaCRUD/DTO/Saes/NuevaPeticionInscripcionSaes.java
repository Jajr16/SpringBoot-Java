package com.example.PruebaCRUD.DTO.Saes;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos para inscribir a alguien
 */
public class NuevaPeticionInscripcionSaes {
    private String boleta;
    private String ets;
    private String periodo;
    private String turno;
    private String user;

    // ==================== CONSTRUCTORES =====================
    public NuevaPeticionInscripcionSaes() {}

    public NuevaPeticionInscripcionSaes(String boleta, String ets, String periodo, String turno, String user) {
        this.boleta = boleta;
        this.ets = ets;
        this.periodo = periodo;
        this.turno = turno;
        this.user = user;
    }

    // ==================== SETTERS AND GETTERS ====================

    public String getBoleta() {
        return boleta;
    }

    public void setBoleta(String boleta) {
        this.boleta = boleta;
    }

    public String getEts() {
        return ets;
    }

    public void setEts(String ets) {
        this.ets = ets;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "NewInscripcionRequestSaes{" +
                "boleta='" + boleta + '\'' +
                ", ets='" + ets + '\'' +
                ", periodo='" + periodo + '\'' +
                ", turno='" + turno + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
