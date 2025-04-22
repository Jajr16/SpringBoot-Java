package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.BoletaETSPK;
import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "resultadoRN") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class ResultadoRN {
    @EmbeddedId // Indica que una clase la va a tomar como llave primaria (usualmente por tener llave compuesta)
    private BoletaETSPK id; // Lave primaria compuesta de ResultadoRN

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // Relaciona esta variable con la de la clase de la llave primaria
    @JoinColumns({
            @JoinColumn(name = "boleta", referencedColumnName = "boleta", nullable = false),
            @JoinColumn(name = "idets", referencedColumnName = "idets", nullable = false)
    }) // Relaciones a otras columnas de otras tablas
    private IngresoSalon ingresoSalon;

    @Column(name = "imagenAlumno", nullable = false)
    private String imagenAlumno;

    @Column(name = "precision", scale = 2, nullable = false)
    private Float precision;

    // ==================== CONSTRUCTORES =====================
    // Constructor sin argumentos (requerido por Hibernate)
    public ResultadoRN() {}

    public ResultadoRN(BoletaETSPK id, String imagenAlumno, Float precision, IngresoSalon ingresoSalon) {
        this.id = id;
        this.imagenAlumno = imagenAlumno;
        this.precision = precision;
        this.ingresoSalon = ingresoSalon;
    }

    // ==================== SETTERS AND GETTERS ====================
    public BoletaETSPK getId() {
        return id;
    }

    public void setId(BoletaETSPK id) {
        this.id = id;
    }

    public IngresoSalon getIngresoSalon() {
        return ingresoSalon;
    }

    public void setIngresoSalon(IngresoSalon ingresoSalon) {
        this.ingresoSalon = ingresoSalon;
    }

    public String getImagenAlumno() {
        return imagenAlumno;
    }

    public void setImagenAlumno(String imagenAlumno) {
        this.imagenAlumno = imagenAlumno;
    }

    public Float getPrecision() {
        return precision;
    }

    public void setPrecision(Float precision) {
        this.precision = precision;
    }

    // ==================== TO STRING ====================
    @Override
    public String toString() {
        return "ResultadoRN{" +
                "id=" + id +
                ", ingresoSalon=" + ingresoSalon +
                ", imagenAlumno='" + imagenAlumno + '\'' +
                ", precision=" + precision +
                '}';
    }
}
