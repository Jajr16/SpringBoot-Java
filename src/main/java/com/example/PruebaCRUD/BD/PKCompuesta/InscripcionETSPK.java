package com.example.PruebaCRUD.BD.PKCompuesta;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 *  Clase que auyda a construir la llave primaria de la tabla InscripcionETS
 */
@Embeddable // Especifica que esta clase debe de estar incrustada en otra dentro de otra entidad
public class InscripcionETSPK implements Serializable { // Serializable indica que la clase se va a pasar a una base de
    // datos externa, convirtiendose a una secuencia de bytes
    @Column(name = "Boleta") // Notación que indica que la variable será una columna
    private String Boleta;

    @Column(name = "idETS")
    private Integer idETS;

    // SETTERS AND GETTERS
    public String getBoleta() {
        return Boleta;
    }

    public void setBoleta(String boleta) {
        Boleta = boleta;
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
        InscripcionETSPK that = (InscripcionETSPK) o;
        return Objects.equals(Boleta, that.Boleta) && Objects.equals(idETS, that.idETS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Boleta, idETS);
    }
}
