package com.example.PruebaCRUD.DTO;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de los ETS
 */
public class DataPersonaDTO {

    private String nombre;
    private String apellidoP;
    private String apellidoM;

    public DataPersonaDTO(String Nombre,
                          String ApellidoP, String ApellidoM){

        this.nombre = Nombre;
        this.apellidoP = ApellidoP;
        this.apellidoM = ApellidoM;

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

    @Override
    public String toString() {
        return "{" +
                ", nombre='" + nombre + '\'' +
                ", apellidoP='" + apellidoP + '\'' +
                ", apellidoM='" + apellidoM + '\'' +
                '}';
    }

}
