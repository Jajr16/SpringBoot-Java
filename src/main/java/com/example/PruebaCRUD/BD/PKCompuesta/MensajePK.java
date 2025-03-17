package com.example.PruebaCRUD.BD.PKCompuesta;

import com.example.PruebaCRUD.BD.Chat;
import com.example.PruebaCRUD.BD.Usuario;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *  Clase que auyda a construir la llave primaria de la tabla Chat
 */
@Embeddable // Especifica que esta clase debe de estar incrustada en otra dentro de otra entidad
public class MensajePK implements Serializable { // Serializable indica que la clase se va a pasar a una base de
    // datos externa, convirtiendose a una secuencia de bytes

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat", nullable = false)
    private Chat chat;

    @Column(name = "fecha")
    private LocalDateTime fechahora;

//    =================== CONSTRUCTOR ==================
    public MensajePK() {}

    public MensajePK(Chat chat, LocalDateTime fecha) {
        this.chat = chat;
        this.fechahora = fecha;
    }

    // ================== GETTERS AND SETTERS =======================
    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public LocalDateTime getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MensajePK mensajePK = (MensajePK) o;
        return Objects.equals(chat, mensajePK.chat) && Objects.equals(fechahora, mensajePK.fechahora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chat, fechahora);
    }
}
