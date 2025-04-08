package com.example.PruebaCRUD.BD.PKCompuesta;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase que auyda a construir la llave primaria de la tabla Aplica
 */
@Embeddable // Especifica que esta clase debe de estar incrustada en otra dentro de otra entidad
public class AplicaPK implements Serializable { // Serializable indica que la clase se va a pasar a una base de
    // datos externa, convirtiendose a una secuencia de bytes

    @Column(name = "idETS") // Notación que indica que la variable será una columna
    private Integer idETS;

    @Column(name = "DocenteRFC")
    private String DocenteRFC;

    // SETTERS AND GETTERS
    public Integer getIdETS() {
        return idETS;
    }

    public void setIdETS(Integer idETS) {
        this.idETS = idETS;
    }

    public String getDocenteRFC() {
        return DocenteRFC;
    }

    public void setDocenteRFC(String docenteRFC) {
        DocenteRFC = docenteRFC;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AplicaPK that = (AplicaPK) o;
        return Objects.equals(idETS, that.idETS) && Objects.equals(DocenteRFC, that.DocenteRFC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idETS, DocenteRFC);
    }
}
