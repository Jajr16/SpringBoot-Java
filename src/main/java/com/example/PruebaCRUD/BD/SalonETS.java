package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.SalonETSPK;
import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "salonets") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class SalonETS {

    @EmbeddedId// Indica que una clase la va a tomar como llave primaria (usualmente por tener llave compuesta)
    private SalonETSPK id;// Lave primaria compuesta de Aplica

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("numSalon")// Relaciona esta variable con la de la clase de la llave primaria
    @JoinColumn(name = "num_salon", nullable = false)// Notación para especificar el nombre de la columna que tendrá la relación
    private Salon numSalonSETS;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idETS")
    @JoinColumn(name = "idETS", nullable = false)
    private ETS idETSSETS;

    // ==================== CONSTRUCTORES =====================
    public SalonETS() {}

    public SalonETS(SalonETSPK id) {
        this.id = id;
    }

    // ==================== SETTERS AND GETTERS ====================
    public SalonETSPK getId() {
        return id;
    }

    public void setId(SalonETSPK id) {
        this.id = id;
    }

    public Salon getNumSalon() {
        return numSalonSETS;
    }

    public void setNumSalon(Salon numSalon) {
        this.numSalonSETS = numSalon;
    }

    public ETS getIdETS() {
        return idETSSETS;
    }

    public void setIdETS(ETS idETS) {
        this.idETSSETS = idETS;
    }
}
