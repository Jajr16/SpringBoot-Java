package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "salon")
public class Salon {

    @Id
    @Column(name = "numSalon", nullable = false)
    private Integer numSalon;

    @Column(name = "Edificio", nullable = false)
    private Integer Edificio;

    @Column(name = "Piso", nullable = false)
    private Integer Piso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_salon", nullable = false)
    private TipoSalon tipoSalon;


    //    ================= RELACIONES INVERSAS CON OTRAS TABLAS ========================
    @OneToMany(mappedBy = "numSalonSETS", cascade = CascadeType.PERSIST)
    private List<SalonETS> SETSDetails;

    public Salon() {}

    public Salon(Integer numSalon, Integer Edificio, Integer Piso, TipoSalon tipoSalon) {
        this.numSalon = numSalon;
        this.Edificio = Edificio;
        this.Piso = Piso;
        this.tipoSalon = tipoSalon;
    }

    public Integer getNumSalon() {
        return numSalon;
    }

    public void setNumSalon(Integer numSalon) {
        this.numSalon = numSalon;
    }

    public Integer getEdificio() {
        return Edificio;
    }

    public void setEdificio(Integer edificio) {
        Edificio = edificio;
    }

    public Integer getPiso() {
        return Piso;
    }

    public void setPiso(Integer piso) {
        Piso = piso;
    }

    public TipoSalon getTipoSalon() {
        return tipoSalon;
    }

    public void setTipoSalon(TipoSalon tipoSalon) {
        this.tipoSalon = tipoSalon;
    }

    public List<SalonETS> getSETSDetails() {
        return SETSDetails;
    }

    public void setSETSDetails(List<SalonETS> SETSDetails) {
        this.SETSDetails = SETSDetails;
    }
}
