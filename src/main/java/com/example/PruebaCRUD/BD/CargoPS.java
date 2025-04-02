package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "cargops") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class CargoPS {

    @Id // Indica que es la llave primaria de la tabla
    // Indica que esta columna se generará automáticamente (autoincrementandose)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idCargo", nullable = false) // Indica que la variable es una columna
    private Integer idCargo;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    // ==================== CONSTRUCTORES =====================
    public CargoPS() {}

    public CargoPS(Integer idCargo, String Nombre) {
        this.idCargo = idCargo;
        this.nombre = Nombre;
    }

    public CargoPS(String Nombre) {
        this.nombre = Nombre;
    }

    // ==================== SETTERS AND GETTERS ====================
    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
