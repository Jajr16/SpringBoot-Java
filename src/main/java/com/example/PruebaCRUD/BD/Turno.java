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
    private String Nombre;

    public Turno() {}

    public Turno(Integer IdTurno, String Nombre) {
        this.idTurno = IdTurno;
        this.Nombre = Nombre;
    }

    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
