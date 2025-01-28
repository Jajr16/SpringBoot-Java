package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

@Entity
@Table(name = "cargops")
public class CargoPS {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idCargo", nullable = false)
    private Integer idCargo;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    public CargoPS() {}

    public CargoPS(Integer idCargo, String Nombre) {
        this.idCargo = idCargo;
        this.nombre = Nombre;
    }

    public CargoPS(String Nombre) {
        this.nombre = Nombre;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
