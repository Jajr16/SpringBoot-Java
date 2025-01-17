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

    @OneToMany(mappedBy = "inscripcionETS", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AsistenciaInscripcion> asistencias;

    // Constructor vacío
    public InscripcionETS() {}

    // Constructor con ID
    public InscripcionETS(InscripcionETSPK id) {
        this.id = id;
    }

    // Getters y Setters
    public InscripcionETSPK getId() {
        return id;
    }

    public void setId(InscripcionETSPK id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return BoletaIns;
    }

    public void setAlumno(Alumno alumno) {
        this.BoletaIns = alumno;
    }

    public ETS getEts() {
        return idETSIns;
    }

    public void setEts(ETS ets) {
        this.idETSIns = ets;
    }

    public List<AsistenciaInscripcion> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<AsistenciaInscripcion> asistencias) {
        this.asistencias = asistencias;
    }
}
