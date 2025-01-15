package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ets")
public class ETS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idETS", nullable = false)
    private Integer idETS;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPeriodo", nullable = false)
    private periodoETS idPeriodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Turno", nullable = false)
    private Turno Turno;

    @Column(name = "Fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date Fecha;

    @Column(name = "Cupo", nullable = false)
    private Integer Cupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUA", nullable = false)
    private UnidadAprendizaje idUA;

    @Column(name = "Duracion", nullable = false)
    private Integer Duracion;

    public ETS() {}

    public ETS(periodoETS idPeriodo, Turno turno, Date fecha, Integer Cupo, UnidadAprendizaje idUA, Integer duracion) {
        this.idPeriodo = idPeriodo;
        this.Turno = turno;
        this.Fecha = fecha;
        this.Cupo = Cupo;
        this.idUA = idUA;
        this.Duracion = duracion;
    }

    public Integer getIdETS() {
        return idETS;
    }

    public void setIdETS(Integer idETS) {
        this.idETS = idETS;
    }

    public periodoETS getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(periodoETS idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public com.example.PruebaCRUD.BD.Turno getTurno() {
        return Turno;
    }

    public void setTurno(com.example.PruebaCRUD.BD.Turno turno) {
        Turno = turno;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }

    public Integer getCupo() {
        return Cupo;
    }

    public void setCupo(Integer cupo) {
        Cupo = cupo;
    }

    public UnidadAprendizaje getIdUA() {
        return idUA;
    }

    public void setIdUA(UnidadAprendizaje idUA) {
        this.idUA = idUA;
    }

    public Integer getDuracion() {
        return Duracion;
    }

    public void setDuracion(Integer duracion) {
        Duracion = duracion;
    }
}
