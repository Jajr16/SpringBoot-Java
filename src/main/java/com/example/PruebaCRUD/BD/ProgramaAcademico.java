package com.example.PruebaCRUD.BD;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "programaacademico")
public class ProgramaAcademico {

    public ProgramaAcademico() {}

    @Id
    @Column(name = "idPA", nullable = false, length = 20)
    private String idPA;

    @JsonProperty("Nombre")
    @Column(name = "Nombre", nullable = false)
    private String nombre; // Cambiado a minúscula

    @JsonProperty("Descripcion")
    @Column(name = "Descripcion", nullable = false)
    private String Descripcion; // Cambiado a minúscula

    @OneToMany(mappedBy = "idPAcad", cascade = CascadeType.PERSIST)
    private List<EscuelaPrograma> detailsEP;



    public ProgramaAcademico(String id_PA, String nombre, String descripcion) {
        this.idPA = id_PA;
        this.nombre = nombre;
        this.Descripcion = descripcion;
    }

    public ProgramaAcademico(String nombre, String descripcion) {
        this.nombre = nombre;
        this.Descripcion = descripcion;
    }

    public String getIdPA() {
        return idPA;
    }

    public void setIdPA(String idPA) {
        this.idPA = idPA;
    }

    public List<EscuelaPrograma> getDetailsEP() {
        return detailsEP;
    }

    public void setDetailsEP(List<EscuelaPrograma> detailsEP) {
        this.detailsEP = detailsEP;
    }

    public String getId_PA() {
        return idPA;
    }

    public void setId_PA(String id_PA) {
        this.idPA = id_PA;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }
}