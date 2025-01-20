package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

@Entity
@Table(name = "turno")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTurno", nullable = false)
    private Integer idTurno;

    @Column(name = "Nombre", nullable = false, length = 10)
    private String nombre;

    public Turno() {}

    public Turno(Integer IdTurno, String Nombre) {
        this.idTurno = IdTurno;
        this.nombre = Nombre;
    }

    public Turno(String Nombre) {
        this.nombre = Nombre;
    }

    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
