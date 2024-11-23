package com.example.PruebaCRUD.Persona;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Para decirle que es una entidad y una tabla dentro de la BD, se pone esto
@Entity
@Table(name = "Persona")
public class Persona {
    // Si ocupamos que se ponga como id se pone @Id, si queremos que se genere automaticamente: @GeneratedValue(strategy = Generation.Type.IDENTITY)
    //@Column(unique = true)
    @Id
    @Column(name = "curp", nullable=false)
    private String CURP;
    @JsonProperty("nombre")
    @Column(name = "nombre", nullable=false)
    private String Nombre;
    @JsonProperty("apellido_P")
    @Column(name = "apellido_p", nullable=false)
    private String Apellido_P;
    @JsonProperty("apellido_M")
    @Column(name = "apellido_m", nullable=false)
    private String Apellido_M;
    @JsonProperty("sexo")
    @Column(name = "sexo", nullable=false)
    private int Sexo;
    @JsonProperty("id_Escuela")
    @Column(name = "id_escuela", nullable=false)
    private int Id_Escuela;

    public Persona() {
    }
    /**
     * Dar de alta a una persona, ya sea personal de seguridad, alumno o personal académico
     * @param CURP identificación de la persona
     * @param nombre Nombre de la persona
     * @param apellido_p Apellido paterno de la persona
     * @param apellido_m Apellido materno de la persona
     * @param sexo Sexo de la persona
     * @param id_escuela Escuela a la que pertenece la persona
     */
    public Persona(String CURP, String nombre, String apellido_p, String apellido_m, int sexo, int id_escuela) {
        this.CURP = CURP;
        this.Nombre = nombre;
        this.Apellido_P = apellido_p;
        this.Apellido_M = apellido_m;
        this.Sexo = sexo;
        this.Id_Escuela = id_escuela;
    }

    public Persona(String nombre, String apellido_p, String apellido_m, int sexo, int id_escuela) {
        Nombre = nombre;
        Apellido_P = apellido_p;
        Apellido_M = apellido_m;
        Sexo = sexo;
        Id_Escuela = id_escuela;
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

    public int getSexo() {
        return Sexo;
    }

    public void setSexo(int sexo) {
        Sexo = sexo;
    }

    public int getId_Escuela() {
        return Id_Escuela;
    }

    public void setId_Escuela(int id_Escuela) {
        Id_Escuela = id_Escuela;
    }
}
