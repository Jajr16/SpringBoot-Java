package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.EscuelaProgramaPK;
import jakarta.persistence.*;

import java.io.Serializable;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "escuelaprograma") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class EscuelaPrograma implements Serializable {

    @EmbeddedId // Indica que una clase la va a tomar como llave primaria (usualmente por tener llave compuesta)
    private EscuelaProgramaPK id; // Lave primaria compuesta de Aplica

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEscuela") // Relaciona esta variable con la de la clase de la llave primaria
    @JoinColumn(name = "id_Escuela", nullable = false) // Notación para especificar el nombre de la columna que tendrá la relación
    private UnidadAcademica idUA;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPA")
    @JoinColumn(name = "idPA", nullable = false)
    private ProgramaAcademico idPAcad;

    // ==================== CONSTRUCTORES =====================
    public EscuelaPrograma(){}

    public EscuelaPrograma(EscuelaProgramaPK id) {
        this.id = id;
    }

    public EscuelaPrograma(EscuelaProgramaPK id, UnidadAcademica idEscuela, ProgramaAcademico idPA) {
        this.id = id;
        this.idUA = idEscuela;
        this.idPAcad = idPA;
    }

    public EscuelaPrograma(UnidadAcademica idEscuela, ProgramaAcademico idPA) {
        this.idUA = idEscuela;
        this.idPAcad = idPA;
    }

    // ==================== SETTERS AND GETTERS ====================
    public EscuelaProgramaPK getId() {
        return id;
    }

    public void setId(EscuelaProgramaPK id) {
        this.id = id;
    }

    public UnidadAcademica getIdEscuela() {
        return idUA;
    }

    public void setIdEscuela(UnidadAcademica idEscuela) {
        this.idUA = idEscuela;
    }

    public ProgramaAcademico getIdPA() {
        return idPAcad;
    }

    public void setIdPA(ProgramaAcademico idPA) {
        this.idPAcad = idPA;
    }


}
