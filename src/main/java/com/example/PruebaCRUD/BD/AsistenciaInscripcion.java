package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.AsistenciaInscripcionPK;
import jakarta.persistence.*;

import java.util.Date;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "asistenciainscripcion") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class AsistenciaInscripcion {
    @EmbeddedId // Indica que una clase la va a tomar como llave primaria (usualmente por tener llave compuesta)
    private AsistenciaInscripcionPK id; // Lave primaria compuesta de AsistenciaInscripcion

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("inscripcionETS") // Relaciona esta variable con la de la clase de la llave primaria
    @JoinColumns({
            @JoinColumn(name = "inscripcionets_boleta", referencedColumnName = "Boleta", nullable = false),
            @JoinColumn(name = "inscripcionets_idets", referencedColumnName = "idETS", nullable = false)
    }) // Relaciones a otras columnas de otras tablas
    private InscripcionETS inscripcionETS;

    @Column(name = "Asistio", nullable = false)
    private boolean Asistio;

    @Column(name = "resultado_rn", nullable = false)
    private boolean ResultadoRN;

    @Column(name = "Aceptado", nullable = false)
    private  boolean Aceptado;

    // ==================== CONSTRUCTORES =====================
    public AsistenciaInscripcion() {}

    public AsistenciaInscripcion(AsistenciaInscripcionPK id, boolean Asistio, boolean ResultadoRN, boolean Aceptado) {
        this.id = id;
        this.Asistio = Asistio;
        this.ResultadoRN = ResultadoRN;
        this.Aceptado = Aceptado;
    }

    // ==================== SETTERS AND GETTERS ====================
    public AsistenciaInscripcionPK getId() {
        return id;
    }

    public void setId(AsistenciaInscripcionPK id) {
        this.id = id;
    }

    public InscripcionETS getInscripcionETS() {
        return inscripcionETS;
    }

    public void setInscripcionETS(InscripcionETS inscripcionETS) {
        this.inscripcionETS = inscripcionETS;
    }

    public boolean isAsistio() {
        return Asistio;
    }

    public void setAsistio(boolean asistio) {
        Asistio = asistio;
    }

    public boolean isResultadoRN() {
        return ResultadoRN;
    }

    public void setResultadoRN(boolean resultadoRN) {
        ResultadoRN = resultadoRN;
    }

    public boolean isAceptado() {
        return Aceptado;
    }

    public void setAceptado(boolean aceptado) {
        Aceptado = aceptado;
    }
}
