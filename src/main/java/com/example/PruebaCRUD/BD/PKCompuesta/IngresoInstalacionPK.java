package com.example.PruebaCRUD.BD.PKCompuesta;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

/**
 *  Clase que auyda a construir la llave primaria de la tabla IngresoInstalacionPK
 */
@Embeddable // Especifica que esta clase debe de estar incrustada en otra dentro de otra entidad
public class IngresoInstalacionPK implements Serializable { // Serializable indica que la clase se va a pasar a una base de
    // datos externa, convirtiendose a una secuencia de bytes

    @Column(name = "boleta", nullable = false)
    private String boleta;

    @Column(name = "idets", nullable = false)
    private Integer idets;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "hora", nullable = false)
    private Time hora;

//    ================== CONSTRUCTOR ===================
    public IngresoInstalacionPK() {}
    
    public IngresoInstalacionPK(String boleta, Integer idets, Date fecha, Time hora) {
        this.boleta = boleta;
        this.idets = idets;
        this.fecha = fecha;
        this.hora = hora;
    }


    //    ================== GETTER AND SETTER ===================
    public String getBoleta() {
        return boleta;
    }

    public void setBoleta(String boleta) {
        this.boleta = boleta;
    }

    public Integer getIdets() {
        return idets;
    }

    public void setIdets(Integer idets) {
        this.idets = idets;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngresoInstalacionPK that = (IngresoInstalacionPK) o;
        return Objects.equals(boleta, that.boleta) && Objects.equals(idets, that.idets)
                && Objects.equals(fecha, that.fecha) && Objects.equals(hora, that.hora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boleta, idets, fecha, hora);
    }
}
