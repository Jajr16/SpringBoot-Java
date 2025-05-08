package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "ets") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
    public class ETS {

    @Id // Indica que es la llave primaria de la tabla
    // Indica que esta columna se generará automáticamente (autoincrementandose)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idETS", nullable = false) // Notación que indica que la variable será una columna
    private Integer id_ETS;

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPeriodo", nullable = false) // Notación para especificar el nombre de la columna que tendrá la relación
    private periodoETS idPeriodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turno", nullable = false)
    private Turno Turno;

    @Column(name = "Fecha", nullable = false)
    @Temporal(TemporalType.DATE) // Notación para indicarle que es una variable Dat e
    private Date Fecha;

    @Column(name = "Hora", nullable = false)
    private Time hora;

    @Column(name = "Cupo", nullable = false)
    private Integer Cupo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
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

    // ==================== CONSTRUCTORES =====================
    public ETS() {}

    public ETS(Integer idets, periodoETS idPeriodo, Turno turno, Date fecha, Time hora, Integer Cupo, UnidadAprendizaje idUA, Integer duracion) {
        this.id_ETS = idets;
        this.idPeriodo = idPeriodo;
        this.Turno = turno;
        this.Fecha = fecha;
        this.Cupo = Cupo;
        this.idUA = idUA;
        this.Duracion = duracion;
        this.hora = hora;
    }

    public ETS(periodoETS idPeriodo, Turno turno, Date fecha, Time hora, Integer Cupo, UnidadAprendizaje idUA, Integer duracion) {
        this.idPeriodo = idPeriodo;
        this.Turno = turno;
        this.Fecha = fecha;
        this.Cupo = Cupo;
        this.idUA = idUA;
        this.Duracion = duracion;
        this.hora = hora;
    }

    // ==================== SETTERS AND GETTERS ====================
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

    public Turno getTurno() {
        return Turno;
    }

    public void setTurno(Turno turno) {
        Turno = turno;
    }

    public Date getFecha() {
        return Fecha;
    }

    public Integer getId_ETS() {
        return id_ETS;
    }

    public void setId_ETS(Integer id_ETS) {
        this.id_ETS = id_ETS;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
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

    @Override
    public String toString() {
        return "ETS{" +
                "id_ETS=" + id_ETS +
                ", idPeriodo=" + idPeriodo +
                ", Turno=" + Turno +
                ", Fecha=" + Fecha +
                ", hora=" + hora +
                ", Cupo=" + Cupo +
                ", idUA=" + idUA +
                ", Duracion=" + Duracion +
                ", ETS=" + ETS +
                ", insETS=" + insETS +
                ", ETSSETTSDetails=" + ETSSETTSDetails +
                '}';
    }
}
