package com.example.PruebaCRUD.BD.PKCompuesta;

import com.example.PruebaCRUD.BD.Usuario;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *  Clase que auyda a construir la llave primaria de la tabla Mensaje
 */
@Embeddable // Especifica que esta clase debe de estar incrustada en otra dentro de otra entidad
public class MensajePK implements Serializable { // Serializable indica que la clase se va a pasar a una base de
    // datos externa, convirtiendose a una secuencia de bytes

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remitente", nullable = false)
    private Usuario remitente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinatarioU", nullable = false, insertable = false, updatable = false )
    private Usuario destinatario;

    @Column(name="fecha")
    private LocalDate fecha;

//    =================== CONSTRUCTOR ==================
    public MensajePK(Usuario remitente, Usuario destinatario, LocalDate fecha) {
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.fecha = fecha;
    }

    // ================== GETTERS AND SETTERS =======================

    public Usuario getRemitente() {
        return remitente;
    }

    public void setRemitente(Usuario remitente) {
        this.remitente = remitente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MensajePK mensajePK = (MensajePK) o;
        return Objects.equals(remitente, mensajePK.remitente) && Objects.equals(destinatario, mensajePK.destinatario)
                && Objects.equals(fecha, mensajePK.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remitente, destinatario, fecha);
    }
}
