package com.example.PruebaCRUD.BD.PKCompuesta;

import com.example.PruebaCRUD.BD.Persona;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase que auyda a construir la llave primaria de la tabla PersonalSeguridad
 */
@Embeddable // Especifica que esta clase debe de estar incrustada en otra dentro de otra entidad
public class PersonalSeguridadPK implements Serializable { // Serializable indica que la clase se va a pasar a una base de
    // datos externa, convirtiendose a una secuencia de bytes

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Notación para indicar una relación entre tablas
    // Notación para especificar el nombre de la columna que tendrá la relación
    @JoinColumn(name = "CURP", nullable = false)
    private Persona CURP;

    public PersonalSeguridadPK() {}

    //SETTERS AND GETTERS
    public PersonalSeguridadPK(Persona CURP) {
        this.CURP = CURP;
    }

    public Persona getCURP() {
        return CURP;
    }

    public void setCURP(Persona CURP) {
        this.CURP = CURP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalSeguridadPK that = (PersonalSeguridadPK) o;
        return Objects.equals(CURP, that.CURP);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(CURP);
    }
}
