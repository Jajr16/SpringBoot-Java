package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

@Entity
@Table(name = "unidadacademica")
public class UnidadAcademica {

    public UnidadAcademica() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEscuela")
    private Integer idEscuela;

    @Column(name = "Nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    public UnidadAcademica (int id_escuela, String nombre) {
        this.idEscuela = id_escuela;
        this.nombre = nombre;
    }

    public UnidadAcademica (String nombre) {
        this.nombre = nombre;
    }

    // ########## GETTERS AND SETTERS ##########
    public Integer getId_escuela() {
        return idEscuela;
    }

    public void setId_escuela(Integer id_escuela) {
        this.idEscuela = id_escuela;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
