package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.AplicaPK;
import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "reemplazo") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class Reemplazo {
    @EmbeddedId // Indica que una clase la va a tomar como llave primaria (usualmente por tener llave compuesta)
    private AplicaPK id; // Lave primaria compuesta de Reemplazo

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id") // Relaciona esta variable con la de la clase de la llave primaria
    @JoinColumns({
            @JoinColumn(name = "idETS", referencedColumnName = "idETS", nullable = false),
            @JoinColumn(name = "docenterfc", referencedColumnName = "docente_rfc", nullable = false)
    }) // Relaciones a otras columnas de otras tablas
    private Aplica reemplazoPK;

    @Column(name = "motivo", nullable = false)
    private String motivo;

    @Column(name = "estatus", nullable = false)
    private Integer estatus;

    // ==================== CONSTRUCTORES =====================
    public Reemplazo() {}

    public Reemplazo(AplicaPK id, String motivo, Integer estatus) {
        this.id = id;
        this.motivo = motivo;
        this.estatus = estatus;
    }

    // ==================== SETTERS AND GETTERS ====================
    public AplicaPK getId() {
        return id;
    }

    public void setId(AplicaPK id) {
        this.id = id;
    }

    public Aplica getReemplazoPK() {
        return reemplazoPK;
    }

    public void setReemplazoPK(Aplica reemplazoPK) {
        this.reemplazoPK = reemplazoPK;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }


    // ==================== TO STRING ====================
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", reemplazoPK=" + reemplazoPK +
                ", motivo='" + motivo + '\'' +
                ", estatus=" + estatus +
                '}';
    }
}
