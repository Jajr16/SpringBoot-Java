package com.example.PruebaCRUD.BD;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "ProgramaAcademico")
public class ProgramaAcademico {

    public ProgramaAcademico() {}

    @Id
    @Column(name = "idPA", nullable = false, length = 20)
    private String idPA;

    @JsonProperty("Nombre")
    @Column(name = "Nombre", nullable = false)
    private String Nombre; // Cambiado a minúscula

    @JsonProperty("Descripcion")
    @Column(name = "Descripcion", nullable = false)
    private String Descripcion; // Cambiado a minúscula

    public ProgramaAcademico(String id_PA, String nombre, String descripcion) {
        this.idPA = id_PA;
        this.Nombre = nombre;
        this.Descripcion = descripcion;
    }

    public ProgramaAcademico(String nombre, String descripcion) {
        this.Nombre = nombre;
        this.Descripcion = descripcion;
    }

    public String getId_PA() {
        return idPA;
    }

    public void setId_PA(String id_PA) {
        this.idPA = id_PA;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }
}