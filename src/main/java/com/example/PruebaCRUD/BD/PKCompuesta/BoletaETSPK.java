package com.example.PruebaCRUD.BD.PKCompuesta;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 *  Clase que auyda a construir la llave primaria de la tabla BoletaETSPK
 */
@Embeddable // Especifica que esta clase debe de estar incrustada en otra dentro de otra entidad
public class BoletaETSPK implements Serializable { // Serializable indica que la clase se va a pasar a una base de
    // datos externa, convirtiendose a una secuencia de bytes

    @Column(name = "boleta")
    private String boleta;

    @Column(name = "idets")
    private Integer idets;

//    ================== CONSTRUCTOR ===================
    public BoletaETSPK() {}

    public BoletaETSPK(String boleta, Integer idets) {
        this.boleta = boleta;
        this.idets = idets;
    }

    //    ================== GETTER AND SETTER ===================
    public String getBoleta() {
        return boleta;
    }

    public void setBoleta(String boleta) {
        this.boleta = boleta;
    }

    public Integer getIdets() {
        return idets;
    }

    public void setIdets(Integer idets) {
        this.idets = idets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoletaETSPK that = (BoletaETSPK) o;
        return Objects.equals(boleta, that.boleta) && Objects.equals(idets, that.idets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boleta, idets);
    }
}
