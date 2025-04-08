package com.example.PruebaCRUD.BD.PKCompuesta;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 *  Clase que auyda a construir la llave primaria de la tabla CargoDocente
 */
@Embeddable // Especifica que esta clase debe de estar incrustada en otra dentro de otra entidad
public class CargoDocentePK implements Serializable { // Serializable indica que la clase se va a pasar a una base de
    // datos externa, convirtiendose a una secuencia de bytes

    @Column(name = "RFC") // Notación que indica que la variable será una columna
    private String RFC;

    @Column(name = "id_cargo")
    private Integer idCargo;

    // SETTERS AND GETTERS
    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CargoDocentePK that = (CargoDocentePK) o;
        return Objects.equals(RFC, that.RFC) && Objects.equals(idCargo, that.idCargo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(RFC, idCargo);
    }
}

