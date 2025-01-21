package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "periodoets")
public class periodoETS {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idPeriodo", nullable = false)
    private Integer idPeriodo;

    @Column(name = "Periodo", nullable = false, length = 20)
    private String periodo;

    @Column(name = "Tipo", nullable = false)
    private char tipo;

    @Column(name = "Fecha_Inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date Fecha_Inicio;

    @Column(name = "Fecha_Fin", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date Fecha_Fin;

    public periodoETS() {}

    public periodoETS(String periodo, char Tipo, Date FechaI, Date FechaF) {
        this.periodo = periodo;
        this.tipo = Tipo;
        this.Fecha_Inicio = FechaI;
        this.Fecha_Fin = FechaF;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        periodo = periodo;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        tipo = tipo;
    }

    public Date getFecha_Inicio() {
        return Fecha_Inicio;
    }

    public void setFecha_Inicio(Date fecha_Inicio) {
        Fecha_Inicio = fecha_Inicio;
    }

    public Date getFecha_Fin() {
        return Fecha_Fin;
    }

    public void setFecha_Fin(Date fecha_Fin) {
        Fecha_Fin = fecha_Fin;
    }
}
