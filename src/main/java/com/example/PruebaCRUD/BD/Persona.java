package com.example.PruebaCRUD.BD;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

// Para decirle que es una entidad y una tabla dentro de la BD, se pone esto
@Entity
@Table(name = "Persona")
public class Persona {
    // Si ocupamos que se ponga como id se pone @Id, si queremos que se genere automaticamente: @GeneratedValue(strategy = Generation.Type.IDENTITY)
    //@Column(unique = true)
    @Id
    @Column(name = "CURP", nullable=false, length = 18)
    private String CURP;

    @JsonProperty("nombre")
    @Column(name = "nombre", nullable=false)
    private String Nombre;

    @JsonProperty("ApellidoP")
    @Column(name = "ApellidoP", nullable=false)
    private String Apellido_P;

    @JsonProperty("ApellidoM")
    @Column(name = "ApellidoM", nullable=false)
    private String Apellido_M;

//  ############ REFERENCIAS A OTRAS TABLAS #############
//    @Column(name = "sexo", nullable=false)
    @JsonProperty("sexo")
    @ManyToOne
    @JoinColumn(name = "idSexo", nullable = false)
    private Sexo sexo;

//    @Column(name = "id_escuela", nullable=false)
    @JsonProperty("idEscuela")
    @ManyToOne
    @JoinColumn(name = "idEscuela", nullable = false)
    private UnidadAcademica unidadAcademica;

    public Persona() {
    }
    /**
     * Dar de alta a una persona, ya sea personal de seguridad, alumno o personal académico
     * @param CURP identificación de la persona
     * @param nombre Nombre de la persona
     * @param apellido_p Apellido paterno de la persona
     * @param apellido_m Apellido materno de la persona
     * @param sexo Sexo de la persona
     * @param unidadAcademica Escuela a la que pertenece la persona
     */
    public Persona(String CURP, String nombre, String apellido_p, String apellido_m, Sexo sexo, UnidadAcademica unidadAcademica) {
        this.CURP = CURP;
        this.Nombre = nombre;
        this.Apellido_P = apellido_p;
        this.Apellido_M = apellido_m;
        this.sexo = sexo;
        this.unidadAcademica = unidadAcademica;
    }

    public Persona(String nombre, String apellido_p, String apellido_m, Sexo sexo, UnidadAcademica unidadAcademica) {
        Nombre = nombre;
        Apellido_P = apellido_p;
        Apellido_M = apellido_m;
        this.sexo = sexo;
        this.unidadAcademica = unidadAcademica;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido_P() {
        return Apellido_P;
    }

    public void setApellido_P(String apellido_P) {
        Apellido_P = apellido_P;
    }

    public String getApellido_M() {
        return Apellido_M;
    }

    public void setApellido_M(String apellido_M) {
        Apellido_M = apellido_M;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public UnidadAcademica getUnidadAcademica() {
        return unidadAcademica;
    }

    public void setUnidadAcademica(UnidadAcademica unidadAcademica) {
        this.unidadAcademica = unidadAcademica;
    }
}
