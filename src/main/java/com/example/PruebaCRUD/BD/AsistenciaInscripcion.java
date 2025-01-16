package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.AsistenciaInscripcionPK;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "asistenciainscripcion")
public class AsistenciaInscripcion {
    @EmbeddedId
    private AsistenciaInscripcionPK id;

    @MapsId("FechaAsistencia")
    @Column(name = "FechaAsistencia", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date FechaAsistencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("InscripcionETSBoleta")
    @JoinColumn(name = "Boleta", nullable = false)
    private InscripcionETS BoletaIns;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("InscripcionETSIdETS")
    @JoinColumn(name = "idETS", nullable = false)
    private InscripcionETS idETSAsisIns;

    @Column(name = "Asistio", nullable = false)
    private boolean Asistio;

    @Column(name = "ResultadoRN", nullable = false)
    private boolean ResultadoRN;

    @Column(name = "Aceptado", nullable = false)
    private  boolean Aceptado;

    public AsistenciaInscripcion() {}

    public AsistenciaInscripcion(AsistenciaInscripcionPK id, boolean Asistio, boolean ResultadoRN, boolean Aceptado) {
        this.id = id;
        this.Asistio = Asistio;
        this.ResultadoRN = ResultadoRN;
        this.Aceptado = Aceptado;
    }

    public AsistenciaInscripcionPK getId() {
        return id;
    }

    public void setId(AsistenciaInscripcionPK id) {
        this.id = id;
    }

    public Date getFechaAsistencia() {
        return FechaAsistencia;
    }

    public void setFechaAsistencia(Date fechaAsistencia) {
        FechaAsistencia = fechaAsistencia;
    }

    public InscripcionETS getBoleta() {
        return BoletaIns;
    }

    public void setBoleta(InscripcionETS boleta) {
        BoletaIns = boleta;
    }

    public InscripcionETS getIdETS() {
        return idETSAsisIns;
    }

    public void setIdETS(InscripcionETS idETS) {
        this.idETSAsisIns = idETS;
    }

    public boolean isAssistio() {
        return Asistio;
    }

    public void setAsistio(boolean asistio) {
        Asistio = asistio;
    }

    public boolean isResultadoRN() {
        return ResultadoRN;
    }

    public void setResultadoRN(boolean resultadoRN) {
        ResultadoRN = resultadoRN;
    }

    public boolean isAceptado() {
        return Aceptado;
    }

    public void setAceptado(boolean aceptado) {
        Aceptado = aceptado;
    }
}
