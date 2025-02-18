package com.example.PruebaCRUD.BD.PKCompuesta;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 *  Clase que auyda a construir la llave primaria de la tabla SalonETS
 */
@Embeddable // Especifica que esta clase debe de estar incrustada en otra dentro de otra entidad
public class SalonETSPK implements Serializable { // Serializable indica que la clase se va a pasar a una base de
    // datos externa, convirtiendose a una secuencia de bytes
    @Column(name = "numSalon") // Notación que indica que la variable será una columna
    private Integer numSalon;

    @Column(name = "idETS")
    private Integer idETS;

    // SETTERS AND GETTERS
    public Integer getNumSalon() {
        return numSalon;
    }

    public void setNumSalon(Integer numSalon) {
        this.numSalon = numSalon;
    }

    public Integer getIdETS() {
        return idETS;
    }

    public void setIdETS(Integer idETS) {
        this.idETS = idETS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalonETSPK that = (SalonETSPK) o;
        return Objects.equals(numSalon, that.numSalon) && Objects.equals(idETS, that.idETS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numSalon, idETS);
    }
}
