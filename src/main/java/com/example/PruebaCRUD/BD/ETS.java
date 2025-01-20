package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ets")
public class ETS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idETS", nullable = false)
    private Integer id_ETS;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPeriodo", nullable = false)
    private periodoETS idPeriodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turno", nullable = false)
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


    //    ================= RELACIONES INVERSAS CON OTRAS TABLAS ========================
//    ##### TABLA APLICA ######
    @OneToMany(mappedBy = "idETS", cascade = CascadeType.PERSIST)
    private List<Aplica> ETS;

//    ##### TABLA INSCRIPCION ETS ####
    @OneToMany(mappedBy = "idETSIns", cascade = CascadeType.PERSIST)
    private List<InscripcionETS> insETS;
//    ##### TABLA SALONETS #####
    @OneToMany(mappedBy = "idETSSETS", cascade = CascadeType.PERSIST)
    private List<SalonETS> ETSSETTSDetails;

    public ETS() {}

//    public ETS(Integer idets, periodoETS idPeriodo, Turno turno, Date fecha, Integer Cupo, UnidadAprendizaje idUA, Integer duracion) {
//        this.id_ETS = idets;
//        this.idPeriodo = idPeriodo;
//        this.Turno = turno;
//        this.Fecha = fecha;
//        this.Cupo = Cupo;
//        this.idUA = idUA;
//        this.Duracion = duracion;
//    }

    public ETS(periodoETS idPeriodo, Turno turno, Date fecha, Integer Cupo, UnidadAprendizaje idUA, Integer duracion) {
        this.idPeriodo = idPeriodo;
        this.Turno = turno;
        this.Fecha = fecha;
        this.Cupo = Cupo;
        this.idUA = idUA;
        this.Duracion = duracion;
    }

    public Integer getIdETS() {
        return id_ETS;
    }

    public void setIdETS(Integer idETS) {
        this.id_ETS = idETS;
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

    public List<Aplica> getETS() {
        return ETS;
    }

    public void setETS(List<Aplica> ETS) {
        this.ETS = ETS;
    }

    public List<InscripcionETS> getInsETS() {
        return insETS;
    }

    public void setInsETS(List<InscripcionETS> insETS) {
        this.insETS = insETS;
    }

    public List<SalonETS> getETSSETTSDetails() {
        return ETSSETTSDetails;
    }

    public void setETSSETTSDetails(List<SalonETS> ETSSETTSDetails) {
        this.ETSSETTSDetails = ETSSETTSDetails;
    }
}
