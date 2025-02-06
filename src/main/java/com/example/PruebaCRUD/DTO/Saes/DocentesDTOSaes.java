package com.example.PruebaCRUD.DTO.Saes;

public class DocentesDTOSaes {
    private String nombre;
//    private String cargo;
    private String correo;

    public DocentesDTOSaes(String nombre, String correo) {
        this.nombre = nombre;
//        this.cargo = cargo;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

//    public String getCargo() {
//        return cargo;
//    }
//
//    public void setCargo(String cargo) {
//        this.cargo = cargo;
//    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "DocentesDTOSaes{" +
                "nombre='" + nombre + '\'' +
//                ", cargo='" + cargo + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
