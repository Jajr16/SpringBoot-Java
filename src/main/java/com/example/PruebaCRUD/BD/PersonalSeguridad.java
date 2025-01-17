package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

@Entity
@Table(name = "personalseguridad")
public class PersonalSeguridad {

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CURP", nullable = false)
    private Persona CURP;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turno", nullable = false)
    private Turno Turno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo", nullable = false)
    private CargoPS Cargo;

    public PersonalSeguridad() {}

    public PersonalSeguridad(Persona CURP, Turno turno, CargoPS Cargo) {
        this.CURP = CURP;
        this.Turno = turno;
        this.Cargo = Cargo;
    }

    public Persona getCURP() {
        return CURP;
    }

    public void setCURP(Persona CURP) {
        this.CURP = CURP;
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
