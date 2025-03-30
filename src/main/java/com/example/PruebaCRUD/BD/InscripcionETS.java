package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
import jakarta.persistence.*;

import java.util.List;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "inscripcionets") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class InscripcionETS {

    @EmbeddedId // Indica que una clase la va a tomar como llave primaria (usualmente por tener llave compuesta)
    private InscripcionETSPK id; // Lave primaria compuesta de InscripcionETS

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("Boleta") // Relaciona esta variable con la de la clase de la llave primaria
    @JoinColumn(name = "Boleta", nullable = false) // Notación para especificar el nombre de la columna que tendrá la relación
    private Alumno boletaIns;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idETS")
    @JoinColumn(name = "idETS", nullable = false)
    private ETS idETSIns;


    //    ================= RELACIONES INVERSAS CON OTRAS TABLAS ========================

    @OneToOne(mappedBy = "inscripcionETS", cascade = CascadeType.ALL, orphanRemoval = true)
    private IngresoSalon asistencias;

    // ==================== CONSTRUCTORES =====================
    // Constructor vacío
    public InscripcionETS() {}

    // Constructor con ID
    public InscripcionETS(InscripcionETSPK id) {
        this.id = id;
    }

    // Getters y Setters
    public InscripcionETSPK getId() {
        return id;
    }

    // ==================== SETTERS AND GETTERS ====================
    public void setId(InscripcionETSPK id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return boletaIns;
    }

    public void setAlumno(Alumno alumno) {
        this.boletaIns = alumno;
    }

    public ETS getEts() {
        return idETSIns;
    }

    public void setEts(ETS ets) {
        this.idETSIns = ets;
    }

    public IngresoSalon getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(IngresoSalon asistencias) {
        this.asistencias = asistencias;
    }
}
