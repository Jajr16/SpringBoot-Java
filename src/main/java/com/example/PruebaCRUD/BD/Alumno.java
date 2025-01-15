package com.example.PruebaCRUD.BD;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "alumno")
public class Alumno {
//    COLUMNS
    @Id
    @Column(name = "Boleta", nullable = false, length = 15)
    private String Boleta;

    @JsonProperty("CorreoI")
    @Column(name = "CorreoI", nullable = false, length = 100)
    private String CorreoI;

    @Column(name = "imagenCredencial", nullable = false, length = 200)
    private String imagenCredencial;

//    FOREIGNKEYS
    @JsonProperty("idPA")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPA", nullable = false)
    private ProgramaAcademico idPA;

    @JsonProperty("CURP")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CURP", nullable=false)
    private Persona CURP;

    public Alumno() {}

    public Alumno(String Boleta, Persona CURP, String CorreoI, ProgramaAcademico idPA, String imagenCredencial) {
        this.Boleta = Boleta;
        this.CURP = CURP;
        this.CorreoI = CorreoI;
        this.idPA = idPA;
        this.imagenCredencial = imagenCredencial;
    }

    public Alumno(String Boleta, Persona CURP, String CorreoI, ProgramaAcademico idPA) {
        this.Boleta = Boleta;
        this.CURP = CURP;
        this.CorreoI = CorreoI;
        this.idPA = idPA;
    }

    public String getBoleta() {
        return Boleta;
    }

    public void setBoleta(String boleta) {
        Boleta = boleta;
    }

    public String getCorreoI() {
        return CorreoI;
    }

    public void setCorreoI(String correoI) {
        CorreoI = correoI;
    }

    public String getImagenCredencial() {
        return imagenCredencial;
    }

    public void setImagenCredencial(String imagenCredencial) {
        this.imagenCredencial = imagenCredencial;
    }

    public ProgramaAcademico getIdPA() {
        return idPA;
    }

    public void setIdPA(ProgramaAcademico idPA) {
        this.idPA = idPA;
    }

    public Persona getCURP() {
        return CURP;
    }

    public void setCURP(Persona CURP) {
        this.CURP = CURP;
    }
}
