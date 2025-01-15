package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

@Entity
@Table(name = "cargo")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCargo", nullable = false)
    private Integer idCargo;

    @Column(name = "Cargo", nullable = false, length = 100)
    private String Cargo;

    public Cargo() {}

    public Cargo(Integer idCargo, String Cargo) {
        this.idCargo = idCargo;
        this.Cargo = Cargo;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String cargo) {
        Cargo = cargo;
    }
}
