package com.example.PruebaCRUD.BD;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "alumno") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class Alumno {
    // COLUMNS
    @Id // Notación que indica que es una llave primaria en la BD
    @Column(name = "Boleta", nullable = false, length = 15) // Notación que indica que la variable será una columna
    private String boleta;

    @JsonProperty("CorreoI") // Notación que indica el nombre de esta variable en un json
    @Column(name = "CorreoI", nullable = false, length = 100)
    private String CorreoI;

    @Column(name = "imagenCredencial", nullable = false)
    private String imagenCredencial;

    //    FOREIGNKEYS
    @JsonProperty("idPA")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPA", nullable = false)
    private ProgramaAcademico idPA;

    @JsonProperty("CURP")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CURP", nullable = false)
    private Persona CURP;

    //    ================= RELACIONES INVERSAS CON OTRAS TABLAS ========================

    @OneToMany(mappedBy = "boletaIns", cascade = CascadeType.PERSIST)
    private List<InscripcionETS> inscETSAl;

    // ==================== CONSTRUCTORES =====================
    public Alumno() {
    }

    public Alumno(String Boleta, Persona CURP, String CorreoI, ProgramaAcademico idPA, String imagenCredencial) {
        this.boleta = Boleta;
        this.CURP = CURP;
        this.CorreoI = CorreoI;
        this.idPA = idPA;
        this.imagenCredencial = imagenCredencial;
    }

    public Alumno(String Boleta, Persona CURP, String CorreoI, ProgramaAcademico idPA) {
        this.boleta = Boleta;
        this.CURP = CURP;
        this.CorreoI = CorreoI;
        this.idPA = idPA;
    }

    // ==================== GETTERS AND SETTERS ====================
    public String getBoleta() {
        return boleta;
    }

    public void setBoleta(String boleta) {
        this.boleta = boleta;
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

    public List<InscripcionETS> getInscETSAl() {
        return inscETSAl;
    }

    public void setInscETSAl(List<InscripcionETS> inscETSAl) {
        this.inscETSAl = inscETSAl;
    }

}
