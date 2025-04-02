package com.example.PruebaCRUD.BD;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
// Para decirle que es una entidad y una tabla dentro de la BD, se pone esto
@Entity
@Table(name = "persona") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class Persona {
    // Si ocupamos que se ponga como id se pone @Id, si queremos que se genere automaticamente:
    // @GeneratedValue(strategy = Generation.Type.IDENTITY)
    @Id // Indica que es la llave primaria de la tabla
    @Column(name = "CURP", nullable = false, length = 18) // Notación que indica que la variable será una columna
    private String CURP;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "Apellido_P", nullable = false)
    private String apellido_p;

    @Column(name = "Apellido_M", nullable = false)
    private String apellido_m;

    //  ############ REFERENCIAS A OTRAS TABLAS #############
    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY) // Relaciona esta variable con la de la clase de la llave primaria
    @JoinColumn(name = "Sexo", nullable = false) // Notación para especificar el nombre de la columna que tendrá la relación
    private Sexo sexo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEscuela", nullable = false)
    private UnidadAcademica unidadAcademica;

    // ==================== CONSTRUCTORES =====================
    public Persona() {
    }

    /**
     * Dar de alta a una persona, ya sea personal de seguridad, alumno o personal académico
     *
     * @param CURP            identificación de la persona
     * @param nombre          Nombre de la persona
     * @param apellido_p      Apellido paterno de la persona
     * @param apellido_m      Apellido materno de la persona
     * @param sexo            Sexo de la persona
     * @param unidadAcademica Escuela a la que pertenece la persona
     */
    public Persona(String CURP, String nombre, String apellido_p, String apellido_m, Sexo sexo, UnidadAcademica unidadAcademica) {
        this.CURP = CURP;
        this.nombre = nombre;
        this.apellido_p = apellido_p;
        this.apellido_m = apellido_m;
        this.sexo = sexo;
        this.unidadAcademica = unidadAcademica;
    }

    public Persona(String nombre, String apellido_p, String apellido_m, Sexo sexo, UnidadAcademica unidadAcademica) {
        this.nombre = nombre;
        this.apellido_p = apellido_p;
        this.apellido_m = apellido_m;
        this.sexo = sexo;
        this.unidadAcademica = unidadAcademica;
    }

    // ==================== SETTERS AND GETTERS ====================
    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_P() {
        return apellido_p;
    }

    public void setApellido_P(String apellido_P) {
        this.apellido_p = apellido_P;
    }

    public String getApellido_M() {
        return apellido_m;
    }

    public void setApellido_M(String apellido_M) {
        this.apellido_m = apellido_M;
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
