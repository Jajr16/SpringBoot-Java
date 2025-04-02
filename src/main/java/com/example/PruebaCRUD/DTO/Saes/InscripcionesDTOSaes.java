package com.example.PruebaCRUD.DTO.Saes;

import java.util.Date;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de la tabla InscripcionETS
 */
public class InscripcionesDTOSaes {
    private String materia;
    private String turno;
    private Date fecha;
    private Integer cupo;
    private String periodo;
    private String nombre;

    // ==================== CONSTRUCTORES =====================
    public InscripcionesDTOSaes(String materia, String turno, Date fecha, Integer cupo, String periodo, String nombre) {
        this.materia = materia;
        this.turno = turno;
        this.fecha = fecha;
        this.cupo = cupo;
        this.periodo = periodo;
        this.nombre = nombre;
    }

    // ==================== SETTERS AND GETTERS ====================
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCupo() {
        return cupo;
    }

    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        return "InscripcionesDTOSaes{" +
                "materia='" + materia + '\'' +
                ", turno='" + turno + '\'' +
                ", fecha='" + fecha + '\'' +
                ", cupo='" + cupo + '\'' +
                ", periodo='" + periodo + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
