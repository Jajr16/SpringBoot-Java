package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "inscripcionets")
public class InscripcionETS {

    @EmbeddedId
    private InscripcionETSPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("Boleta")
    @JoinColumn(name = "Boleta", nullable = false)
    private Alumno BoletaIns;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idETS")
    @JoinColumn(name = "idETS", nullable = false)
    private ETS idETSIns;

//    ================= RELACIONES INVERSAS CON OTRAS TABLAS ========================

    @OneToMany(mappedBy = "Boleta", cascade = CascadeType.PERSIST)
    private List<AsistenciaInscripcion> AsistIns;

    @OneToMany(mappedBy = "idETSAsisIns", cascade = CascadeType.PERSIST)
    private List<AsistenciaInscripcion> ETSAsisIns;

    public InscripcionETS() {}

    public InscripcionETS(InscripcionETSPK id) {
        this.id = id;
    }

    public InscripcionETSPK getId() {
        return id;
    }

    public void setId(InscripcionETSPK id) {
        this.id = id;
    }

    public Alumno getBoleta() {
        return BoletaIns;
    }

    public void setBoleta(Alumno boleta) {
        BoletaIns = boleta;
    }

    public ETS getIdETS() {
        return idETSIns;
    }

    public void setIdETS(ETS idETS) {
        this.idETSIns = idETS;
    }

    public List<AsistenciaInscripcion> getAsistIns() {
        return AsistIns;
    }

    public void setAsistIns(List<AsistenciaInscripcion> asistIns) {
        AsistIns = asistIns;
    }

    public List<AsistenciaInscripcion> getETSAsisIns() {
        return ETSAsisIns;
    }

    public void setETSAsisIns(List<AsistenciaInscripcion> idETSAsisIns) {
        this.ETSAsisIns = idETSAsisIns;
    }
}
