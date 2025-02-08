package com.example.PruebaCRUD.DTO.Saes;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de la tabla PersonalSeguridad
 */
public class PersonalSeguridadDTOSaes {
    private String nombre;
    private String turno;
    private String cargo;

    // ==================== CONSTRUCTORES =====================
    public PersonalSeguridadDTOSaes(String nombre, String turno, String cargo) {
        this.nombre = nombre;
        this.turno = turno;
        this.cargo = cargo;
    }

    // ==================== SETTERS AND GETTERS ====================
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
