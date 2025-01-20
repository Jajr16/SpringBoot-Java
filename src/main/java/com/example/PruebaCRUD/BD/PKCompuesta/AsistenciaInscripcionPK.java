package com.example.PruebaCRUD.BD.PKCompuesta;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class AsistenciaInscripcionPK implements Serializable {

    @Column(name = "fecha_asistencia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date FechaAsistencia;

    @Column(name = "inscripcionets_boleta")
    private String inscripcionETSBoleta;

    @Column(name = "inscripcionets_idets")
    private Integer inscripcionETSIdETS;

    public Date getFechaAsistencia() {
        return FechaAsistencia;
    }

    public void setFechaAsistencia(Date fechaAsistencia) {
        FechaAsistencia = fechaAsistencia;
    }

    public String getInscripcionETSBoleta() {
        return inscripcionETSBoleta;
    }

    public void setInscripcionETSBoleta(String inscripcionETSBoleta) {
        this.inscripcionETSBoleta = inscripcionETSBoleta;
    }

    public Integer getInscripcionETSIdETS() {
        return inscripcionETSIdETS;
    }

    public void setInscripcionETSIdETS(Integer inscripcionETSIdETS) {
        this.inscripcionETSIdETS = inscripcionETSIdETS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsistenciaInscripcionPK that = (AsistenciaInscripcionPK) o;
        return Objects.equals(FechaAsistencia, that.FechaAsistencia)
                && Objects.equals(inscripcionETSBoleta, that.inscripcionETSBoleta)
                && Objects.equals(inscripcionETSIdETS, that.inscripcionETSIdETS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FechaAsistencia, inscripcionETSBoleta, inscripcionETSIdETS);
    }
}
