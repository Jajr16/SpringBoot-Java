package com.example.PruebaCRUD.BD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipopersonal")
public class TipoPersonal {

    @Id
    @Column(name = "tipoPA", nullable = false)
    private Integer tipoPA;

    @Column(name = "Cargo", nullable = false, length = 100)
    private String Cargo;

    public TipoPersonal() {}

    public TipoPersonal(Integer tipoPA, String Cargo) {
        this.tipoPA = tipoPA;
        this.Cargo = Cargo;
    }

    public Integer getTipoPA() {
        return tipoPA;
    }

    public void setTipoPA(Integer tipoPA) {
        this.tipoPA = tipoPA;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String cargo) {
        Cargo = cargo;
    }
}
