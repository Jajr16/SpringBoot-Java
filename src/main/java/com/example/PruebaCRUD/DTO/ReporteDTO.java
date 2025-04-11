package com.example.PruebaCRUD.DTO;

import java.sql.Time;
import java.sql.Date;

public class ReporteDTO {
    private String curp;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String escuela;
    private String carrera;
    private String periodo;
    private String tipo;
    private String turno;
    private String materia;
    private String fechaIngreso;
    private String horaIngreso;
    private String nombreDocente;
    private String tipoEstado;
    private byte[] imagenAlumno;
    private Double presicion;
    private String motivo;

    public ReporteDTO(){}

    public ReporteDTO(String curp, String nombre, String apellidoP, String apellidoM, String escuela, String carrera, String periodo, String tipo, String turno, String materia, String fechaIngreso, String horaIngreso, String nombreDocente, String tipoEstado, byte[] imagenAlumno, Double presicion, String motivo) {
        this.curp = curp;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.escuela = escuela;
        this.carrera = carrera;
        this.periodo = periodo;
        this.tipo = tipo;
        this.turno = turno;
        this.materia = materia;
        this.fechaIngreso = fechaIngreso;
        this.horaIngreso = horaIngreso;
        this.nombreDocente = nombreDocente;
        this.tipoEstado = tipoEstado;
        this.imagenAlumno = imagenAlumno;
        this.presicion = presicion;
        this.motivo = motivo;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Double getPresicion() {
        return presicion;
    }

    public void setPresicion(Double presicion) {
        this.presicion = presicion;
    }

    public byte[] getImagenAlumno() {
        return imagenAlumno;
    }

    public void setImagenAlumno(byte[] imagenAlumno) {
        this.imagenAlumno = imagenAlumno;
    }

    public String getTipoEstado() {
        return tipoEstado;
    }

    public void setTipoEstado(String tipoEstado) {
        this.tipoEstado = tipoEstado;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(String horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
