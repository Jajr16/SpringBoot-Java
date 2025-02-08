package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.CargoDocentePK;
import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "cargodocente") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class CargoDocente {

    @EmbeddedId // Indica que una clase la va a tomar como llave primaria (usualmente por tener llave compuesta)
    private CargoDocentePK id; // Lave primaria compuesta de CargoDocente

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("RFC") // Relaciona esta variable con la de la clase de la llave primaria
    @JoinColumn(name = "RFC", nullable = false)  // Notación para especificar el nombre de la columna que tiene la relación
    private PersonalAcademico RFCCD;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCargo")
    @JoinColumn(name = "id_cargo", nullable = false)
    private Cargo idCargoCD;

    // ==================== CONSTRUCTORES =====================
    public CargoDocente() {}

    public CargoDocente(CargoDocentePK id) {
        this.id = id;
    }

    // ==================== SETTERS AND GETTERS ====================
    public CargoDocentePK getId() {
        return id;
    }

    public void setId(CargoDocentePK id) {
        this.id = id;
    }

    public PersonalAcademico getRFC() {
        return RFCCD;
    }

    public void setRFC(PersonalAcademico RFC) {
        this.RFCCD = RFC;
    }

    public Cargo getIdCargo() {
        return idCargoCD;
    }

    public void setIdCargo(Cargo idCargo) {
        this.idCargoCD = idCargo;
    }
}
