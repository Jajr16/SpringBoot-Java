package com.example.PruebaCRUD.DTO.Saes;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de la tabla PersonalSeguridad (En esta ocasión es para el Request de crear un nuevo Personal de Seguridad)
 */
public class NewPersonalSeguridadDTOSaes {
    private String rfc;
    private String curp;
    private String nombre;
    private String apellido_P;
    private String apellido_M;
    private String sexo;
    private String cargoPS;
    private String turno;
    private String user;

    // ==================== CONSTRUCTORES =====================
    public NewPersonalSeguridadDTOSaes() {}

    public NewPersonalSeguridadDTOSaes(String rfc, String curp, String nombre, String apellido_P, String apellido_M, String sexo,
                                       String cargoPS, String turno, String user) {
        this.rfc = rfc;
        this.curp = curp;
        this.nombre = nombre;
        this.apellido_P = apellido_P;
        this.apellido_M = apellido_M;
        this.sexo = sexo;
        this.cargoPS = cargoPS;
        this.turno = turno;
        this.user = user;
    }

    // ==================== SETTERS AND GETTERS ====================

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_P() {
        return apellido_P;
    }

    public void setApellido_P(String apellido_P) {
        this.apellido_P = apellido_P;
    }

    public String getApellido_M() {
        return apellido_M;
    }

    public void setApellido_M(String apellido_M) {
        this.apellido_M = apellido_M;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCargoPS() {
        return cargoPS;
    }

    public void setCargoPS(String cargoPS) {
        this.cargoPS = cargoPS;
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

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    @Override
    public String toString() {
        return "NewPersonalSeguridadDTOSaes{" +
                "rfc='" + rfc + '\'' +
                ", curp='" + curp + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido_P='" + apellido_P + '\'' +
                ", apellido_M='" + apellido_M + '\'' +
                ", sexo='" + sexo + '\'' +
                ", cargoPS='" + cargoPS + '\'' +
                ", turno='" + turno + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
