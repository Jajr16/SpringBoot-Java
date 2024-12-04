package com.example.PruebaCRUD.ProgramaAcademico;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "Programa_academico")
public class ProgramaAcademico {

    public ProgramaAcademico() {}

    @Id
    @Column(name = "Id_PA", nullable = false, length = 20)
    private String id_PA;

    @JsonProperty("nombre")
    @Column(name = "nombre", nullable = false)
    private String Nombre;

    @JsonProperty("descripcion")
    @Column(name = "descripcion", nullable = false)
    private String Descripcion;

    public ProgramaAcademico(String id_PA, String nombre, String descripcion) {
        this.id_PA = id_PA;
        this.Nombre = nombre;
        this.Descripcion = descripcion;
    }

    public ProgramaAcademico(String nombre, String descripcion) {
        this.Nombre = nombre;
        this.Descripcion = descripcion;
    }

    public String getId_PA() {
        return id_PA;
    }

    public void setId_PA(String id_PA) {
        this.id_PA = id_PA;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}

