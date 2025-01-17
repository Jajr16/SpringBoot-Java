package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.AplicaPK;
import jakarta.persistence.*;

@Entity
@Table(name = "aplica")
public class Aplica {

    @EmbeddedId
    private AplicaPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idETS")
    @JoinColumn(name = "idETS", nullable = false)
    private ETS idETS;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("DocenteRFC")
    @JoinColumn(name = "Docente_RFC", nullable = false)
    private PersonalAcademico DocenteRFC;

    @Column(name = "Titular", nullable = false)
    private boolean Titular;

    public Aplica() {}

    public Aplica(AplicaPK id) {
        this.id = id;
    }

    public AplicaPK getId() {
        return id;
    }

    public void setId(AplicaPK id) {
        this.id = id;
    }

    public ETS getIdETS() {
        return idETS;
    }

    public void setIdETS(ETS idETS) {
        this.idETS = idETS;
    }

    public PersonalAcademico getDocenteRFC() {
        return DocenteRFC;
    }

    public void setDocenteRFC(PersonalAcademico docenteRFC) {
        DocenteRFC = docenteRFC;
    }

    public boolean isTitular() {
        return Titular;
    }

    public void setTitular(boolean titular) {
        Titular = titular;
    }
}
