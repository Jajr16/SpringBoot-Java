package com.example.PruebaCRUD.DTO;

import java.sql.Time;
import java.util.Date;

public class IngresoInstalacionDTO {
    private String boleta;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private Integer idETS;

    public IngresoInstalacionDTO(String boleta, String nombre, String apellidoP, String apellidoM, Integer idETS) {
        this.boleta = boleta;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.idETS = idETS;
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

    public Integer getIdETS() {
        return idETS;
    }

    public void setIdETS(Integer idETS) {
        this.idETS = idETS;
    }

//    public Date getFecha() {
//        return fecha;
//    }
//
//    public void setFecha(Date fecha) {
//        this.fecha = fecha;
//    }
//
//    public Time getHora() {
//        return hora;
//    }
//
//    public void setHora(Time hora) {
//        this.hora = hora;
//    }

    @Override
    public String toString() {
        return "IngresoInstalacionDTO{" +
                "boleta='" + boleta + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidoP='" + apellidoP + '\'' +
                ", apellidoM='" + apellidoM + '\'' +
                ", idETS='" + idETS + '\'' +
                '}';
    }
}