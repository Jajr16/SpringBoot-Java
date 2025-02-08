package com.example.PruebaCRUD.BD.PKCompuesta;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;

import java.io.Serializable;
import java.util.Objects;

/**
 *  Clase que auyda a construir la llave primaria de la tabla EscuelaPrograma
 */
@Embeddable // Especifica que esta clase debe de estar incrustada en otra dentro de otra entidad
public class EscuelaProgramaPK implements Serializable { // Serializable indica que la clase se va a pasar a una base de
    // datos externa, convirtiendose a una secuencia de bytes

    @Column(name = "idEscuela") // Notación que indica que la variable será una columna
    private Integer idEscuela;

    @Column(name = "idPA")
    private String idPA;

    // SETTERS AND GETTERS
    public Integer getIdEscuela() {
        return idEscuela;
    }

    public void setIdEscuela(Integer idEscuela) {
        this.idEscuela = idEscuela;
    }

    public String getIdPA() {
        return idPA;
    }

    public void setIdPA(String idPA) {
        this.idPA = idPA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EscuelaProgramaPK that = (EscuelaProgramaPK) o;
        return Objects.equals(idEscuela, that.idEscuela) && Objects.equals(idPA, that.idPA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEscuela, idPA);
    }
}
