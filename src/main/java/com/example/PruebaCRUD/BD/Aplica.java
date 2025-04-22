package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.AplicaPK;
import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "aplica") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class Aplica {

    @EmbeddedId // Indica que una clase la va a tomar como llave primaria (usualmente por tener llave compuesta)
    private AplicaPK id; // Lave primaria compuesta de Aplica

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idETS") // Relaciona esta variable con la de la clase de la llave primaria
    @JoinColumn(name = "idETS", nullable = false) // Notación para especificar el nombre de la columna que tendrá la relación
    private ETS idETS;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("DocenteRFC")
    @JoinColumn(name = "Docente_RFC", nullable = false)
    private PersonalAcademico docenteRFC;

    @Column(name = "coordinador", nullable = false)
    private boolean coordinador;

    // ==================== CONSTRUCTORES =====================
    public Aplica() {}

    public Aplica(AplicaPK id) {
        this.id = id;
    }

    public Aplica(AplicaPK id, boolean coordinador) {
        this.id = id;
        this.coordinador = coordinador;
    }


    // ==================== SETTERS AND GETTERS ====================
    public AplicaPK getId() {
        return id;
    }

    public void setId(AplicaPK id) {
        this.id = id;
    }

    public ETS getIdETS() {
        return idETS;
    }

    public void setIdETS(ETS idETS) {
        this.idETS = idETS;
    }

    public PersonalAcademico getDocenteRFC() {
        return docenteRFC;
    }

    public void setDocenteRFC(PersonalAcademico docenteRFC) {
        this.docenteRFC = docenteRFC;
    }

    public boolean isTitular() {
        return coordinador;
    }

    public void setTitular(boolean coordinador) {
        this.coordinador = coordinador;
    }
}
