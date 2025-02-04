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
    @JoinColumn(name = "num_salon", nullable = false)
    private Salon numSalonSETS;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idETS")
    @JoinColumn(name = "idETS", nullable = false)
    private ETS idETSSETS;

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
        return numSalonSETS;
    }

    public void setNumSalon(Salon numSalon) {
        this.numSalonSETS = numSalon;
    }

    public ETS getIdETS() {
        return idETSSETS;
    }

    public void setIdETS(ETS idETS) {
        this.idETSSETS = idETS;
    }
}
