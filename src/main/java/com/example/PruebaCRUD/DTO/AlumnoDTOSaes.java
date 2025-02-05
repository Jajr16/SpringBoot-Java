package com.example.PruebaCRUD.DTO;


public class AlumnoDTOSaes {
    private String nombre;
    private String boleta;
    private String programaAcademico;
    private String correo;

    public AlumnoDTOSaes(String nombre, String boleta, String pa, String correo) {
        this.nombre = nombre;
        this.boleta = boleta;
        this.programaAcademico = pa;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBoleta() {
        return boleta;
    }

    public void setBoleta(String boleta) {
        this.boleta = boleta;
    }

    public String getProgramaAcademico() {
        return programaAcademico;
    }

    public void setProgramaAcademico(String programaAcademico) {
        this.programaAcademico = programaAcademico;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "AlumnoDTO{" +
                "nombre='" + nombre + '\'' +
                ", boleta='" + boleta + '\'' +
                ", programaAcademico='" + programaAcademico + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
