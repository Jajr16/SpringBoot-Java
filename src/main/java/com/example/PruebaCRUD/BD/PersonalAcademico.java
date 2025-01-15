package com.example.PruebaCRUD.BD;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

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
    private String CorreoI;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoPA", nullable = false)
    private TipoPersonal TipoPA;

    public PersonalAcademico() {}

    public PersonalAcademico(String RFC, Persona CURP, String CorreoI, TipoPersonal TipoPA) {
        this.RFC = RFC;
        this.CURP = CURP;
        this.CorreoI = CorreoI;
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
        return CorreoI;
    }

    public void setCorreoI(String correoI) {
        CorreoI = correoI;
    }

    public TipoPersonal getTipoPA() {
        return TipoPA;
    }

    public void setTipoPA(TipoPersonal tipoPA) {
        TipoPA = tipoPA;
    }
}
