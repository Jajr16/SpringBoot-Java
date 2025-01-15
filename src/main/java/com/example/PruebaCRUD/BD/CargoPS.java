package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

@Entity
@Table(name = "CargoPS")
public class CargoPS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCargo", nullable = false)
    private Integer idCargo;

    @Column(name = "Nombre", nullable = false)
    private String Nombre;

    public CargoPS() {}

    public CargoPS(Integer idCargo, String Nombre) {
        this.idCargo = idCargo;
        this.Nombre = Nombre;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
