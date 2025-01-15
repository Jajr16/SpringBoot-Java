package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.SalonETSPK;
import jakarta.persistence.*;

@Entity
@Table(name = "salonets")
public class SalonETS {

    @EmbeddedId
    private SalonETSPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("numSalon")
    @JoinColumn(name = "numSalon", nullable = false)
    private Salon numSalon;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idETS")
    @JoinColumn(name = "idETS", nullable = false)
    private ETS idETS;

    public SalonETS() {}

    public SalonETS(SalonETSPK id) {
        this.id = id;
    }

    public SalonETSPK getId() {
        return id;
    }

    public void setId(SalonETSPK id) {
        this.id = id;
    }

    public Salon getNumSalon() {
        return numSalon;
    }

    public void setNumSalon(Salon numSalon) {
        this.numSalon = numSalon;
    }

    public ETS getIdETS() {
        return idETS;
    }

    public void setIdETS(ETS idETS) {
        this.idETS = idETS;
    }
}
