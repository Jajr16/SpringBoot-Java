package com.example.PruebaCRUD.BD.PKCompuesta;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AplicaPK implements Serializable {

    @Column(name = "idETS")
    private Integer idETS;

    @Column(name = "DocenteRFC")
    private String DocenteRFC;

    public Integer getIdETS() {
        return idETS;
    }

    public void setIdETS(Integer idETS) {
        this.idETS = idETS;
    }

    public String getDocenteRFC() {
        return DocenteRFC;
    }

    public void setDocenteRFC(String docenteRFC) {
        DocenteRFC = docenteRFC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AplicaPK that = (AplicaPK) o;
        return Objects.equals(idETS, that.idETS) && Objects.equals(DocenteRFC, that.DocenteRFC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idETS, DocenteRFC);
    }
}
