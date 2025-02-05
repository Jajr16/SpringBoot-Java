package com.example.PruebaCRUD.BD;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "personalacademico")
public class PersonalAcademico {

    @Id
    @Column(name = "RFC", nullable = false, length = 13)
    private String RFC;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CURP", nullable = false)
    private Persona CURP;

    @JsonProperty("CorreoI")
    @Column(name = "CorreoI", nullable = false, length = 100)
    private String correoi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoPA", nullable = false)
    private TipoPersonal TipoPA;

    //    ================= RELACIONES INVERSAS CON OTRAS TABLAS ========================
//    ##### TABLA APLICA ####
    @OneToMany(mappedBy = "DocenteRFC", cascade = CascadeType.PERSIST)
    private List<Aplica> AplicaPersA;

//    ##### TABLA CARGO DOCENTE ####
    @OneToMany(mappedBy = "RFCCD", cascade = CascadeType.PERSIST)
    private List<CargoDocente> RFCCargoDocente;

    public PersonalAcademico() {}

    public PersonalAcademico(String RFC, Persona CURP, String CorreoI, TipoPersonal TipoPA) {
        this.RFC = RFC;
        this.CURP = CURP;
        this.correoi = CorreoI;
        this.TipoPA = TipoPA;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public Persona getCURP() {
        return CURP;
    }

    public void setCURP(Persona CURP) {
        this.CURP = CURP;
    }

    public String getCorreoI() {
        return correoi;
    }

    public void setCorreoI(String correoI) {
        this.correoi = correoI;
    }

    public TipoPersonal getTipoPA() {
        return TipoPA;
    }

    public void setTipoPA(TipoPersonal tipoPA) {
        this.TipoPA = tipoPA;
    }

    public List<Aplica> getAplicaPersA() {
        return AplicaPersA;
    }

    public void setAplicaPersA(List<Aplica> aplicaPersA) {
        this.AplicaPersA = aplicaPersA;
    }

    public List<CargoDocente> getRFCCargoDocente() {
        return RFCCargoDocente;
    }

    public void setRFCCargoDocente(List<CargoDocente> RFCCargoDocente) {
        this.RFCCargoDocente = RFCCargoDocente;
    }
}
