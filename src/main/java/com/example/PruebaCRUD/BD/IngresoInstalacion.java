package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.IngresoInstalacionPK;
import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "ingresoInstalacion") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class IngresoInstalacion {
    @EmbeddedId
    private IngresoInstalacionPK id;

    // Relación con la tabla IngresoSalon, donde boleta y idets son claves foráneas
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "boleta", referencedColumnName = "boleta", insertable = false, updatable = false),
            @JoinColumn(name = "idets", referencedColumnName = "idets", insertable = false, updatable = false)
    })
    private IngresoSalon ingresoSalon;

    // ==================== CONSTRUCTORES =====================
    public IngresoInstalacion() {}

    public IngresoInstalacion(IngresoInstalacionPK id) {
        this.id = id;
    }

    // ==================== GETTERS AND SETTERS ====================
    public IngresoInstalacionPK getId() {
        return id;
    }

    public void setId(IngresoInstalacionPK id) {
        this.id = id;
    }

    // ==================== TO STRING ====================
    @Override
    public String toString() {
        return "IngresoInstalacion{" +
                "id=" + id +
                '}';
    }
}
