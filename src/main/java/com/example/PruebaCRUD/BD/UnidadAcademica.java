package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

@Entity
@Table(name = "UnidadAcademica")
public class UnidadAcademica {

    public UnidadAcademica() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Escuela")
    private Integer id_escuela;

    @Column(name = "Nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    public UnidadAcademica (int id_escuela, String nombre) {
        this.id_escuela = id_escuela;
        this.nombre = nombre;
    }

    public UnidadAcademica (String nombre) {
        this.nombre = nombre;
    }

    // ########## GETTERS AND SETTERS ##########
    public Integer getId_escuela() {
        return id_escuela;
    }

    public void setId_escuela(Integer id_escuela) {
        this.id_escuela = id_escuela;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
