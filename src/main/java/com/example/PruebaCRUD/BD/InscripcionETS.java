package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
import jakarta.persistence.*;

@Entity
@Table(name = "InscripcionETS")
public class InscripcionETS {

    @EmbeddedId
    private InscripcionETSPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("Boleta")
    @JoinColumn(name = "Boleta", nullable = false)
    private Alumno Boleta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idETS")
    @JoinColumn(name = "idETS", nullable = false)
    private ETS idETS;

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
        return Boleta;
    }

    public void setBoleta(Alumno boleta) {
        Boleta = boleta;
    }

    public ETS getIdETS() {
        return idETS;
    }

    public void setIdETS(ETS idETS) {
        this.idETS = idETS;
    }
}
