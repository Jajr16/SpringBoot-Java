package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.AsistenciaInscripcionPK;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "asistenciainscripcion")
public class AsistenciaInscripcion {
    @EmbeddedId
    private AsistenciaInscripcionPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("inscripcionETS") // Relacionamos el campo compuesto
    @JoinColumns({
            @JoinColumn(name = "inscripcionets_boleta", referencedColumnName = "Boleta", nullable = false),
            @JoinColumn(name = "inscripcionets_idets", referencedColumnName = "idETS", nullable = false)
    })
    private InscripcionETS inscripcionETS;

    @Column(name = "Asistio", nullable = false)
    private boolean Asistio;

    @Column(name = "resultado_rn", nullable = false)
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

    public InscripcionETS getInscripcionETS() {
        return inscripcionETS;
    }

    public void setInscripcionETS(InscripcionETS inscripcionETS) {
        this.inscripcionETS = inscripcionETS;
    }

    public boolean isAsistio() {
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
