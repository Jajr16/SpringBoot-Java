package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "tipoEstado") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class TipoEstado {
    @Id // Indica que es la llave primaria de la tabla
    private Integer idtipo;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    // ============== CONSTRUCTOR SIN ARGUMENTOS (OBLIGATORIO PARA JPA) ================
    public TipoEstado() {
        // Constructor vacío requerido por JPA
    }

//    ============== CONSTRUCTOR ================
    public TipoEstado(Integer idtipo, String tipo) {
        if (!isValid(idtipo)) {
            throw new IllegalArgumentException("Valor inválido para idtipo: " + idtipo);
        }
        this.idtipo = idtipo;
        this.tipo = tipo;
    }

//    ================== VALIDACIONES ==================

    @PrePersist // Notación para que ejecute esto antes de que se guarde una nueva entidad en la BD
    @PreUpdate // Notación que se ejecuta antes de que se actualice un registro existente en la BD
    private void validarIdtipo() {
        if (!isValid(this.idtipo)) {
            throw new IllegalArgumentException("Valor inválido para idtipo: " + this.idtipo);
        }
    }

    private boolean isValid(Integer valor) {
        if (valor == null) return false;
        return valor == -1 || valor == 0 || valor == 1 || valor == 2 || valor == 3 || valor == 4 || valor == 5 || valor == 6;
    }

//    ================== GETTERS AND SETTERS ==================
    public Integer getIdtipo() { return idtipo; }

    public void setIdtipo(Integer idtipo) {
        if (!isValid(idtipo)) {
            throw new IllegalArgumentException("Valor inválido para idtipo: " + idtipo);
        }
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
        return "TipoEstado{" +
                "idtipo=" + idtipo +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
