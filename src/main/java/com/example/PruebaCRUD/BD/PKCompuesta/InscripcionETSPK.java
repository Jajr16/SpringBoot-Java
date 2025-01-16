package com.example.PruebaCRUD.BD.PKCompuesta;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InscripcionETSPK implements Serializable {
    @Column(name = "Boleta")
    private String Boleta;

    @Column(name = "idETS")
    private Integer idETS;

    public String getBoleta() {
        return Boleta;
    }

    public void setBoleta(String boleta) {
        Boleta = boleta;
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
        InscripcionETSPK that = (InscripcionETSPK) o;
        return Objects.equals(Boleta, that.Boleta) && Objects.equals(idETS, that.idETS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Boleta, idETS);
    }
}
