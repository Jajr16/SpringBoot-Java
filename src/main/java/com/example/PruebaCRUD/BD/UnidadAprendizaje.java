package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

@Entity
@Table(name = "unidadaprendizaje")
public class UnidadAprendizaje {

    @Id
    @Column(name = "idUA", nullable = false, length = 20)
    private String idUA;

    @Column(name = "Nombre", nullable = false, length = 150)
    private String Nombre;

    @Column(name = "Descripcion", nullable = false, length = 200)
    private String Descirpcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPA", nullable = false)
    private ProgramaAcademico idPA;

    public UnidadAprendizaje() {}

    public UnidadAprendizaje(String idUA, String nombre, String Descripcion, ProgramaAcademico idPA) {
        this.idUA = idUA;
        this.Nombre = nombre;
        this.Descirpcion = Descripcion;
        this.idPA = idPA;
    }

    public String getIdUA() {
        return idUA;
    }

    public void setIdUA(String idUA) {
        this.idUA = idUA;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescirpcion() {
        return Descirpcion;
    }

    public void setDescirpcion(String descirpcion) {
        Descirpcion = descirpcion;
    }

    public ProgramaAcademico getIdPA() {
        return idPA;
    }

    public void setIdPA(ProgramaAcademico idPA) {
        this.idPA = idPA;
    }
}
