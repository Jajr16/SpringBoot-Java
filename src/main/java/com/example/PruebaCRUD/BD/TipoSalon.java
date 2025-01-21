package com.example.PruebaCRUD.BD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tiposalon")
public class TipoSalon {

    @Id
    @Column(name = "idTS", nullable = false)
    private Integer idTS;

    @Column(name = "Tipo", nullable = false)
    private String tipo;

    public TipoSalon() {}

    public TipoSalon(Integer idTS, String Tipo) {
        this.idTS = idTS;
        this.tipo = Tipo;
    }

    public TipoSalon(String Tipo) {
        this.tipo = Tipo;
    }

    public Integer getIdTS() {
        return idTS;
    }

    public void setIdTS(Integer idTS) {
        this.idTS = idTS;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        tipo = tipo;
    }
}
