package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.BoletaETSPK;
import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "motivoRechazo") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class MotivoRechazo {
    @EmbeddedId // Indica que una clase la va a tomar como llave primaria (usualmente por tener llave compuesta)
    private BoletaETSPK id; // Lave primaria compuesta de MotivoRechazo

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId // Relaciona esta variable con la de la clase de la llave primaria
    @JoinColumns({
            @JoinColumn(name = "boleta", referencedColumnName = "boleta", nullable = false),
            @JoinColumn(name = "idets", referencedColumnName = "idets", nullable = false)
    }) // Relaciones a otras columnas de otras tablas
    private IngresoSalon ingresoSalon;

    // Constructor sin argumentos (requerido por JPA)
    public MotivoRechazo() {
    }

    @Column(name = "motivo", nullable = false)
    private String motivo;

    // ==================== CONSTRUCTORES =====================
    public MotivoRechazo(BoletaETSPK id, String motivo) {
        this.id = id;
        this.motivo = motivo;
    }

    // ==================== SETTERS AND GETTERS ====================
    public BoletaETSPK getId() {
        return id;
    }

    public void setId(BoletaETSPK id) {
        this.id = id;
    }

    public IngresoSalon getIngresoSalon() {
        return ingresoSalon;
    }

    public void setIngresoSalon(IngresoSalon ingresoSalon) {
        this.ingresoSalon = ingresoSalon;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    // ==================== TO STRING ====================
    @Override
    public String toString() {
        return "MotivoRechazo{" +
                "id=" + id +
                ", ingresoSalon=" + ingresoSalon +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}
