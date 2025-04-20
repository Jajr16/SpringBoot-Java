package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.BoletaETSPK;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "ingresoSalon") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class IngresoSalon {
    @EmbeddedId // Indica que una clase la va a tomar como llave primaria (usualmente por tener llave compuesta)
    private BoletaETSPK id; // Lave primaria compuesta de IngresoSalon

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("inscripcionETS") // Relaciona esta variable con la de la clase de la llave primaria
    @JoinColumns({
            @JoinColumn(name = "boleta", referencedColumnName = "Boleta", nullable = false),
            @JoinColumn(name = "idets", referencedColumnName = "idETS", nullable = false)
    }) // Relaciones a otras columnas de otras tablas
    private InscripcionETS inscripcionETS;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "hora", nullable = false)
    private Time hora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docente", nullable = false) 
    private PersonalAcademico docente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado", nullable = false)
    private TipoEstado estado;

    // ==================== CONSTRUCTORES =====================
    public IngresoSalon() {}

    public IngresoSalon(BoletaETSPK id, Date fecha, Time hora, PersonalAcademico docente, TipoEstado tipo) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.docente = docente;
        this.estado = tipo;
    }

    // ==================== SETTERS AND GETTERS ====================
    public BoletaETSPK getId() {
        return id;
    }

    public void setId(BoletaETSPK id) {
        this.id = id;
    }

    public InscripcionETS getInscripcionETS() {
        return inscripcionETS;
    }

    public void setInscripcionETS(InscripcionETS inscripcionETS) {
        this.inscripcionETS = inscripcionETS;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public PersonalAcademico getDocente() {
        return docente;
    }

    public void setDocente(PersonalAcademico docente) {
        this.docente = docente;
    }

    public TipoEstado getTipo() {
        return estado;
    }

    public void setTipo(TipoEstado tipo) {
        this.estado = tipo;
    }

    // ==================== TO STRING ====================
    @Override
    public String toString() {
        return "IngresoSalon{" +
                "id=" + id +
                ", inscripcionETS=" + inscripcionETS +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", docente=" + docente +
                ", tipo=" + estado +
                '}';
    }
}
