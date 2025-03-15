package com.example.PruebaCRUD.DTO;

import java.util.Date;

public class DetalleAlumnosDTO {

    private String imagenCredencial; // Foto de la credencial
    private String nombreAlumno; // Nombre del alumno
    private String apellidoPAlumno; // Apellido paterno del alumno
    private String apellidoMAlumno; // Apellido materno del alumno
    private String nombreETS; // Nombre del ETS
    private String nombreTurno; // Nombre del turno
    private Integer salon; // Salón
    private Date fecha; // Fecha
    private String nombreDocente; // Nombre del docente
    private String apellidoPDocente; // Apellido paterno del docente
    private String apellidoMDocente; // Apellido materno del docente

    public DetalleAlumnosDTO(String imagenCredencial, String nombreAlumno, String apellidoPAlumno, String apellidoMAlumno, String nombreETS, String nombreTurno, Integer salon, Date fecha, String nombreDocente, String apellidoPDocente, String apellidoMDocente) {
        this.imagenCredencial = imagenCredencial;
        this.nombreAlumno = nombreAlumno;
        this.apellidoPAlumno = apellidoPAlumno;
        this.apellidoMAlumno = apellidoMAlumno;
        this.nombreETS = nombreETS;
        this.nombreTurno = nombreTurno;
        this.salon = salon;
        this.fecha = fecha;
        this.nombreDocente = nombreDocente;
        this.apellidoPDocente = apellidoPDocente;
        this.apellidoMDocente = apellidoMDocente;
    }

    public String getImagenCredencial() {
        return imagenCredencial;
    }

    public void setImagenCredencial(String imagenCredencial) {
        this.imagenCredencial = imagenCredencial;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getApellidoPAlumno() {
        return apellidoPAlumno;
    }

    public void setApellidoPAlumno(String apellidoPAlumno) {
        this.apellidoPAlumno = apellidoPAlumno;
    }

    public String getApellidoMAlumno() {
        return apellidoMAlumno;
    }

    public void setApellidoMAlumno(String apellidoMAlumno) {
        this.apellidoMAlumno = apellidoMAlumno;
    }

    public String getNombreETS() {
        return nombreETS;
    }

    public void setNombreETS(String nombreETS) {
        this.nombreETS = nombreETS;
    }

    public String getNombreTurno() {
        return nombreTurno;
    }

    public void setNombreTurno(String nombreTurno) {
        this.nombreTurno = nombreTurno;
    }

    public Integer getSalon() {
        return salon;
    }

    public void setSalon(Integer salon) {
        this.salon = salon;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getApellidoPDocente() {
        return apellidoPDocente;
    }

    public void setApellidoPDocente(String apellidoPDocente) {
        this.apellidoPDocente = apellidoPDocente;
    }

    public String getApellidoMDocente() {
        return apellidoMDocente;
    }

    public void setApellidoMDocente(String apellidoMDocente) {
        this.apellidoMDocente = apellidoMDocente;
    }

    @Override
    public String toString() {
        return "DetalleAlumnosDTO{" +
                "imagenCredencial='" + imagenCredencial + '\'' +
                ", nombreAlumno='" + nombreAlumno + '\'' +
                ", apellidoPAlumno='" + apellidoPAlumno + '\'' +
                ", apellidoMAlumno='" + apellidoMAlumno + '\'' +
                ", nombreETS='" + nombreETS + '\'' +
                ", nombreTurno='" + nombreTurno + '\'' +
                ", salon='" + salon + '\'' +
                ", fecha='" + fecha + '\'' +
                ", nombreDocente='" + nombreDocente + '\'' +
                ", apellidoPDocente='" + apellidoPDocente + '\'' +
                ", apellidoMDocente='" + apellidoMDocente + '\'' +
                '}';
    }
}