package com.example.PruebaCRUD.BD.PKCompuesta;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SalonETSPK implements Serializable {
    private Integer numSalon;
    private Integer idETS;

    public Integer getNumSalon() {
        return numSalon;
    }

    public void setNumSalon(Integer numSalon) {
        this.numSalon = numSalon;
    }

    public Integer getIdETS() {
        return idETS;
    }

    public void setIdETS(Integer idETS) {
        this.idETS = idETS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalonETSPK that = (SalonETSPK) o;
        return Objects.equals(numSalon, that.numSalon) && Objects.equals(idETS, that.idETS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numSalon, idETS);
    }
}
