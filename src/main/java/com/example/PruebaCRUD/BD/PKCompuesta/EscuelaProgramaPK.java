package com.example.PruebaCRUD.BD.PKCompuesta;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EscuelaProgramaPK implements Serializable {
    private Integer idEscuela;
    private String idPA;

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
