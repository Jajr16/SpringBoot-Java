package com.example.PruebaCRUD.BD.PKCompuesta;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CargoDocentePK implements Serializable {

    @Column(name = "RFC")
    private String RFC;

    @Column(name = "id_cargo")
    private Integer idCargo;

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CargoDocentePK that = (CargoDocentePK) o;
        return Objects.equals(RFC, that.RFC) && Objects.equals(idCargo, that.idCargo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(RFC, idCargo);
    }
}

