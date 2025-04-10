package com.example.PruebaCRUD.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatosWebDTO {
    private String boleta;
    private String curp;
    private String nombre;
    private String carrera;
    private String escuela;

    public DatosWebDTO(
            @JsonProperty("boleta") String boleta,
            @JsonProperty("curp") String curp,
            @JsonProperty("nombre") String nombre,
            @JsonProperty("carrera") String carrera,
            @JsonProperty("escuela") String escuela) {

        this.boleta = boleta;
        this.curp = curp;
        this.nombre = nombre;
        this.carrera = carrera;
        this.escuela = escuela;
    }

    // Getters y setters estándar
    public String getBoleta() {
        return boleta;
    }

    public String getCurp() {  // Cambiado de getcurp() a getCurp()
        return curp;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getEscuela() {
        return escuela;
    }

    // Setters
    public void setBoleta(String boleta) {
        this.boleta = boleta;
    }

    public void setCurp(String curp) {  // Cambiado de setcurp() a setCurp()
        this.curp = curp;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }

    @Override
    public String toString() {
        return String.format(
                "DatosWebDTO{boleta='%s', curp='%s', nombre='%s', carrera='%s', escuela='%s'}",
                boleta, curp, nombre, carrera, escuela
        );
    }
}