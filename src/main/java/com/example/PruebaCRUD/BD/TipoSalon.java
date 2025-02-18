package com.example.PruebaCRUD.BD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "tiposalon") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class TipoSalon {

    @Id // Indica que es la llave primaria de la tabla
    @Column(name = "idTS", nullable = false) // Notación que indica que la variable será una columna
    private Integer idTS;

    @Column(name = "Tipo", nullable = false)
    private String tipo;

    // ==================== CONSTRUCTORES =====================
    public TipoSalon() {}

    public TipoSalon(Integer idTS, String Tipo) {
        this.idTS = idTS;
        this.tipo = Tipo;
    }

    public TipoSalon(String Tipo) {
        this.tipo = Tipo;
    }

    // ==================== SETTERS AND GETTERS ====================
    public Integer getIdTS() {
        return idTS;
    }

    public void setIdTS(Integer idTS) {
        this.idTS = idTS;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        tipo = tipo;
    }
}
