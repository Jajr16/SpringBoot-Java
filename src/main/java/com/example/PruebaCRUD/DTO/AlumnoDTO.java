package com.example.PruebaCRUD.DTO;

public class AlumnoDTO {

    private String boleta;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String turno;
    private Integer error;



    // Constructor actualizado


    public AlumnoDTO(String boleta, String nombre, String apellidoP, String apellidoM, String turno) {
        this.boleta = boleta;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.turno = turno;
    }

    public AlumnoDTO(String boleta, String Nombre, String Apellido_P, String Apellido_M, Integer error) {
        this.boleta = boleta;
        this.nombre = Nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.error = error;
    }

    public AlumnoDTO( Integer error) {
        this.error = error;
    }


    // Getters y Setters


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

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "AlumnoDTO{" +
                "boleta='" + boleta + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidoP='" + apellidoP + '\'' +
                ", apellidoM='" + apellidoM + '\'' +
                ", turno='" + turno + '\'' +
                ", error=" + error +
                '}';
    }
}
