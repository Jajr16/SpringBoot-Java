package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

@Entity
@Table(name = "chat", uniqueConstraints = { @UniqueConstraint(columnNames = { "remitente", "destinatario" }) })
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remitente", nullable = false)
    private Usuario remitente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinatario", nullable = false)
    private Usuario destinatario;

    //    =================== CONSTRUCTOR ==================
    public Chat() {}

    public Chat(Usuario remitente, Usuario destinatario) {
        this.remitente = remitente;
        this.destinatario = destinatario;
    }

    //    =================== GETTERS AND SETTERS ==================
    public Usuario getRemitente() {
        return remitente;
    }

    public void setRemitente(Usuario remitente) {
        this.remitente = remitente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    //    =================== TO STRING ==================
    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", remitente=" + remitente +
                ", destinatario=" + destinatario +
                '}';
    }
}
