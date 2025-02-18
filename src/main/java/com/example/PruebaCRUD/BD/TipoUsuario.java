package com.example.PruebaCRUD.BD;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipousuario")
public class TipoUsuario {
    @Id
    @Column(name = "idTU", nullable = false)
    private Integer idTU;

    @Column(name = "Tipo", nullable = false, length = 18)
    private String tipo;

    public TipoUsuario(){}

    public TipoUsuario(Integer idTU, String Tipo) {
        this.idTU = idTU;
        this.tipo = Tipo;
    }

    public TipoUsuario(String Tipo) {
        this.tipo = Tipo;
    }

    public Integer getIdTU() {
        return idTU;
    }

    public void setIdTU(Integer idTU) {
        this.idTU = idTU;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
