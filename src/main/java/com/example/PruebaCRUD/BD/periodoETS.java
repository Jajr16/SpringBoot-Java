package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "periodoets")
public class periodoETS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPeriodo", nullable = false)
    private Integer idPeriodo;

    @Column(name = "Periodo", nullable = false, length = 20)
    private String Periodo;

    @Column(name = "Tipo", nullable = false)
    private char Tipo;

    @Column(name = "Fecha_Inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date Fecha_Inicio;

    @Column(name = "Fecha_Fin", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date Fecha_Fin;

    public periodoETS() {}

    public periodoETS(String periodo, char Tipo, Date FechaI, Date FechaF) {
        this.Periodo = periodo;
        this.Tipo = Tipo;
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
        return Periodo;
    }

    public void setPeriodo(String periodo) {
        Periodo = periodo;
    }

    public char getTipo() {
        return Tipo;
    }

    public void setTipo(char tipo) {
        Tipo = tipo;
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
