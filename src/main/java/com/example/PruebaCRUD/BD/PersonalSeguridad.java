package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.PersonalSeguridadPK;
import jakarta.persistence.*;

@Entity
@Table(name = "personalseguridad")
public class PersonalSeguridad {

    @EmbeddedId
    private PersonalSeguridadPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turno", nullable = false)
    private Turno Turno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo", nullable = false)
    private CargoPS Cargo;

    public PersonalSeguridad() {}

    public PersonalSeguridad(PersonalSeguridadPK id, Turno turno, CargoPS Cargo) {
        this.id = id;
        this.Turno = turno;
        this.Cargo = Cargo;
    }

    public PersonalSeguridadPK getId() {
        return id;
    }

    public void setId(PersonalSeguridadPK id) {
        this.id = id;
    }

    public com.example.PruebaCRUD.BD.Turno getTurno() {
        return Turno;
    }

    public void setTurno(com.example.PruebaCRUD.BD.Turno turno) {
        Turno = turno;
    }

    public CargoPS getCargo() {
        return Cargo;
    }

    public void setCargo(CargoPS cargo) {
        Cargo = cargo;
    }
}
