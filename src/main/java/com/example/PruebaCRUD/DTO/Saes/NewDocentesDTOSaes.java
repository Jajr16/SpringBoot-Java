package com.example.PruebaCRUD.DTO.Saes;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de la tabla ETS
 */
public class NewDocentesDTOSaes {

    private String curp;
    private String rfc;
    private String nombre;
    private String apellido_p;
    private String apellido_m;
    private String sexo;
    private String correo;
    private String cargo;
    private String turno;
    private String user;

    // ==================== CONSTRUCTORES =====================
    public NewDocentesDTOSaes(String curp, String rfc, String nombre, String apellido_p, String apellido_m, String sexo,
                              String correo, String cargo, String turno, String user) {
        this.curp = curp;
        this.rfc = rfc;
        this.nombre = nombre;
        this.apellido_p = apellido_p;
        this.apellido_m = apellido_m;
        this.sexo = sexo;
        this.correo = correo;
        this.cargo = cargo;
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

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_p() {
        return apellido_p;
    }

    public void setApellido_p(String apellido_p) {
        this.apellido_p = apellido_p;
    }

    public String getApellido_m() {
        return apellido_m;
    }

    public void setApellido_m(String apellido_m) {
        this.apellido_m = apellido_m;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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
        return "NewDocentesDTOSaes{" +
                "curp='" + curp + '\'' +
                ", rfc='" + rfc + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido_p='" + apellido_p + '\'' +
                ", apellido_m='" + apellido_m + '\'' +
                ", sexo='" + sexo + '\'' +
                ", correo='" + correo + '\'' +
                ", cargo='" + cargo + '\'' +
                ", turno='" + turno + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
