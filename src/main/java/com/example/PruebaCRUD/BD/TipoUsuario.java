package com.example.PruebaCRUD.BD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "tipousuario") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class TipoUsuario {

    @Id // Indica que es la llave primaria de la tabla
    @Column(name = "idTU", nullable = false) // Notación que indica que la variable será una columna
    private Integer idTU;

    @Column(name = "Tipo", nullable = false, length = 18)
    private String tipo;

     // ==================== CONSTRUCTORES =====================
    public TipoUsuario(){}

    public TipoUsuario(Integer idTU, String Tipo) {
        this.idTU = idTU;
        this.tipo = Tipo;
    }

    public TipoUsuario(String Tipo) {
        this.tipo = Tipo;
    }

    // ==================== SETTERS AND GETTERS ====================
    public Integer getIdTU() {
        return idTU;
    }

    public void setIdTU(Integer idTU) {
        this.idTU = idTU;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        tipo = tipo;
    }
}
