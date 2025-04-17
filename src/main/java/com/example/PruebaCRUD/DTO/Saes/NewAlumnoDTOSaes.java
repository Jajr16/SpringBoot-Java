package com.example.PruebaCRUD.DTO.Saes;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de la tabla Alumno
 */
public class NewAlumnoDTOSaes {
    private String curp;
    private String boleta;
    private String nombre;
    private String apellido_p;
    private String apellido_m;
    private String sexo;
    private String correo;
    private Integer escuela;
    private String carrera;
//    private String credencial;

    // ==================== CONSTRUCTORES =====================
    public NewAlumnoDTOSaes(String curp, String boleta, String nombre, String apellido_p, String apellido_m, String sexo,
                            String correo, Integer escuela, String carrera) {
        this.curp = curp;
        this.boleta = boleta;
        this.nombre = nombre;
        this.apellido_p = apellido_p;
        this.apellido_m = apellido_m;
        this.sexo = sexo;
        this.correo = correo;
        this.escuela = escuela;
        this.carrera = carrera;
//        this.credencial = credencial;
    }

    // ==================== SETTERS AND GETTERS ====================
    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getBoleta() {
        return boleta;
    }

    public void setBoleta(String boleta) {
        this.boleta = boleta;
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

    public Integer getEscuela() {
        return escuela;
    }

    public void setEscuela(Integer escuela) {
        this.escuela = escuela;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

//    public String getCredencial() {
//        return credencial;
//    }
//
//    public void setCredencial(String credencial) {
//        this.credencial = credencial;
//    }

    @Override
    public String toString() {
        return "NewAlumnoDTOSaes{" +
                "curp='" + curp + '\'' +
                ", boleta='" + boleta + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido_p='" + apellido_p + '\'' +
                ", apellido_m='" + apellido_m + '\'' +
                ", sexo='" + sexo + '\'' +
                ", correo='" + correo + '\'' +
                ", escuela='" + escuela + '\'' +
                ", carrera='" + carrera + '\'' +
//                ", credencial='" + credencial + '\'' +
                '}';
    }
}
