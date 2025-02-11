package com.example.PruebaCRUD.DTO.Saes;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de la tabla PersonalAcademico (en este caso un docente)
 */
public class EscuelaProgramaDTOSaes {
    private String nombre;
    private String idPA;

    // ==================== CONSTRUCTORES =====================
    public EscuelaProgramaDTOSaes(String nombre, String idPA) {
        this.nombre = nombre;
        this.idPA = idPA;
    }

    // ==================== SETTERS AND GETTERS ====================

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdPA() {
        return idPA;
    }

    public void setIdPA(String idPA) {
        this.idPA = idPA;
    }

    @Override
    public String toString() {
        return "EscuelaProgramaDTOSaes{" +
                "nombre='" + nombre + '\'' +
                ", idPA='" + idPA + '\'' +
                '}';
    }
}