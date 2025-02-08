package com.example.PruebaCRUD.BD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "tipopersonal") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class TipoPersonal {

    @Id // Indica que es la llave primaria de la tabla
    @Column(name = "tipoPA", nullable = false) // Notación que indica que la variable será una columna
    private Integer tipoPA;

    @Column(name = "Cargo", nullable = false, length = 100)
    private String Cargo;

    // ==================== CONSTRUCTORES =====================
    public TipoPersonal() {}

    public TipoPersonal(Integer tipoPA, String Cargo) {
        this.tipoPA = tipoPA;
        this.Cargo = Cargo;
    }

    // ==================== SETTERS AND GETTERS ====================
    public Integer getTipoPA() {
        return tipoPA;
    }

    public void setTipoPA(Integer tipoPA) {
        this.tipoPA = tipoPA;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String cargo) {
        Cargo = cargo;
    }
}
