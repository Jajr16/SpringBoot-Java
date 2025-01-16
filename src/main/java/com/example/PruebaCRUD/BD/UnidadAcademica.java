package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "unidadacademica")
public class UnidadAcademica {

    public UnidadAcademica() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Escuela")
    private Integer id_Escuela;

    @Column(name = "Nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    @OneToMany(mappedBy = "idUA", cascade = CascadeType.PERSIST)
    private List<EscuelaPrograma> detailsUA;


    public UnidadAcademica (int idEscuela, String nombre) {
        this.id_Escuela = idEscuela;
        this.nombre = nombre;
    }

    public UnidadAcademica (String nombre) {
        this.nombre = nombre;
    }

    // ########## GETTERS AND SETTERS ##########
    public Integer getIdEscuela() {
        return id_Escuela;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdEscuela(Integer idEscuela) {
        this.id_Escuela = idEscuela;
    }

    public List<EscuelaPrograma> getDetailsUA() {
        return detailsUA;
    }

    public void setDetailsUA(List<EscuelaPrograma> detailsUA) {
        this.detailsUA = detailsUA;
    }
}
