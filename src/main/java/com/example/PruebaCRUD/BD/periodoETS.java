package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

import java.util.Date;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "periodoets") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class periodoETS {

    @Id // Indica que es la llave primaria de la tabla
    // Indica que esta columna se generará automáticamente (autoincrementandose)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idPeriodo", nullable = false) // Notación que indica que la variable será una columna
    private Integer idPeriodo;

    @Column(name = "Periodo", nullable = false, length = 20)
    private String periodo;

    @Column(name = "Tipo", nullable = false)
    private char tipo;

    @Column(name = "Fecha_Inicio", nullable = false)
    @Temporal(TemporalType.DATE) // Notación para indicarle que es una variable Date
    private Date Fecha_Inicio;

    @Column(name = "Fecha_Fin", nullable = false)
    @Temporal(TemporalType.DATE) // Notación para indicarle que es una variable Date
    private Date Fecha_Fin;

    // ==================== CONSTRUCTORES =====================
    public periodoETS() {}

    public periodoETS(String periodo, char Tipo, Date FechaI, Date FechaF) {
        this.periodo = periodo;
        this.tipo = Tipo;
        this.Fecha_Inicio = FechaI;
        this.Fecha_Fin = FechaF;
    }

    // ==================== SETTERS AND GETTERS ====================
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
        this.periodo = periodo;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
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

    @Override
    public String toString() {
        return "periodoETS{" +
                "idPeriodo=" + idPeriodo +
                ", periodo='" + periodo + '\'' +
                ", tipo=" + tipo +
                ", Fecha_Inicio=" + Fecha_Inicio +
                ", Fecha_Fin=" + Fecha_Fin +
                '}';
    }
}
