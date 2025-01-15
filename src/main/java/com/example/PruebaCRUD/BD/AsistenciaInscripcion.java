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
    @JoinColumn(name = "InscripcionETSBoleta", nullable = false)
    private InscripcionETS Boleta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("InscripcionETSIdETS")
    @JoinColumn(name = "InscripcionETSIdETS", nullable = false)
    private InscripcionETS idETS;

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
}
