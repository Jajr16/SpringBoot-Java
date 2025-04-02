package com.example.PruebaCRUD.BD;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "personalacademico") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class PersonalAcademico {

    @Id // Indica que es la llave primaria de la tabla
    @Column(name = "rfc", nullable = false, length = 13) // Notación que indica que la variable será una columna
    private String rfc;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Notación para indicar una relación entre tablas
    // Notación para especificar el nombre de la columna que tendrá la relación
    @JoinColumn(name = "CURP", nullable = false)
    private Persona CURP;

    @JsonProperty("CorreoI") // Notación que indica el nombre de esta variable en un json
    @Column(name = "CorreoI", nullable = false, length = 100)
    private String correoi;

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoPA", nullable = false) // Notación para especificar el nombre de la columna que tendrá la relación
    private TipoPersonal TipoPA;

    //    ================= RELACIONES INVERSAS CON OTRAS TABLAS ========================
//    ##### TABLA APLICA ####
    @OneToMany(mappedBy = "docenteRFC", cascade = CascadeType.PERSIST)
    private List<Aplica> AplicaPersA;

//    ##### TABLA CARGO DOCENTE ####
    @OneToMany(mappedBy = "RFCCD", cascade = CascadeType.PERSIST)
    private List<CargoDocente> RFCCargoDocente;

    // ==================== CONSTRUCTORES =====================
    public PersonalAcademico() {}

    public PersonalAcademico(String RFC, Persona CURP, String CorreoI, TipoPersonal TipoPA) {
        this.rfc = RFC;
        this.CURP = CURP;
        this.correoi = CorreoI;
        this.TipoPA = TipoPA;
    }

    // ==================== SETTERS AND GETTERS ====================
    public String getRFC() {
        return rfc;
    }

    public void setRFC(String RFC) {
        this.rfc = RFC;
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
