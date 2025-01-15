package com.example.PruebaCRUD.BD.PKCompuesta;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class AsistenciaInscripcionPK implements Serializable {
    private Date FechaAsistencia;
    private String InscripcionETSBoleta;
    private Integer InscripcionETSIdETS;

    public Date getFechaAsistencia() {
        return FechaAsistencia;
    }

    public void setFechaAsistencia(Date fechaAsistencia) {
        FechaAsistencia = fechaAsistencia;
    }

    public String getInscripcionETSBoleta() {
        return InscripcionETSBoleta;
    }

    public void setInscripcionETSBoleta(String inscripcionETSBoleta) {
        InscripcionETSBoleta = inscripcionETSBoleta;
    }

    public Integer getInscripcionETSIdETS() {
        return InscripcionETSIdETS;
    }

    public void setInscripcionETSIdETS(Integer inscripcionETSIdETS) {
        InscripcionETSIdETS = inscripcionETSIdETS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsistenciaInscripcionPK that = (AsistenciaInscripcionPK) o;
        return Objects.equals(FechaAsistencia, that.FechaAsistencia)
                && Objects.equals(InscripcionETSBoleta, that.InscripcionETSBoleta)
                && Objects.equals(InscripcionETSIdETS, that.InscripcionETSIdETS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FechaAsistencia, InscripcionETSBoleta, InscripcionETSIdETS);
    }
}
