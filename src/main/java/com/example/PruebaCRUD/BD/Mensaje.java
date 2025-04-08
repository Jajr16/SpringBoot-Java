package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.MensajePK;
import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "mensaje") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class Mensaje {

    @EmbeddedId
    private MensajePK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remitente", nullable = false)
    private Usuario usuario;

    @Column(name="mensaje", nullable = false)
    private String mensaje;

//    ==================== CONSTRUCTOR ===================
    public Mensaje() {}

    public Mensaje(MensajePK id, Usuario remitente, String mensaje) {
        this.id = id;
        this.usuario = remitente;
        this.mensaje = mensaje;
    }

//    ===================== GETTERS AND SETTERS ======================
    public Usuario getRemitente() {
        return usuario;
    }

    public void setRemitente(Usuario remitente) {
        this.usuario = remitente;
    }

    public MensajePK getId() {
        return id;
    }

    public void setId(MensajePK id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
