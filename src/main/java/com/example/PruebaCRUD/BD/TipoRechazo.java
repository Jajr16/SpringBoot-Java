package com.example.PruebaCRUD.BD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "tipoRechazo") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class TipoRechazo {
    @Id // Indica que es la llave primaria de la tabla
    private Integer idtipo;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    //    ============== CONSTRUCTOR ================
    public TipoRechazo(Integer idtipo, String tipo) {
        this.idtipo = idtipo;
        this.tipo = tipo;
    }

    //    ================== GETTERS AND SETTERS ==================
    public Integer getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(Integer idtipo) {
        this.idtipo = idtipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    //    ================== TO STRING ==================
    @Override
    public String toString() {
        return "TipoRechazo{" +
                "idtipo=" + idtipo +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
