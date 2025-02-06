package com.example.PruebaCRUD.DTO.Saes;

public class PersonalSeguridadDTOSaes {
    private String nombre;
    private String turno;
    private String cargo;

    public PersonalSeguridadDTOSaes(String nombre, String turno, String cargo) {
        this.nombre = nombre;
        this.turno = turno;
        this.cargo = cargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "PersonalSeguridadDTOSaes{" +
                "nombre='" + nombre + '\'' +
                ", turno='" + turno + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }
}
