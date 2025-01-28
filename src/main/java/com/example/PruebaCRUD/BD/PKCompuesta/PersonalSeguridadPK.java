package com.example.PruebaCRUD.BD.PKCompuesta;

import com.example.PruebaCRUD.BD.Persona;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PersonalSeguridadPK implements Serializable {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CURP", nullable = false)
    private Persona CURP;

    public PersonalSeguridadPK() {}

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
