package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.Repositories.TipoUsuarioRepository;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(name = "Usuario", nullable = false, length = 18)
    private String usuario;

    @Column(name = "Password", nullable = false, length = 100)
    private String Password;

//  FOREIGN KEYS
    @JoinColumn(name = "idTU", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoUsuario TipoU;

    @JoinColumn(name = "CURP", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Persona CURP;

    public Usuario() {}

    public Usuario(String usuario, String password, TipoUsuario tipoU, Persona CURP) {
        this.usuario = usuario;
        this.Password = password;
        this.TipoU = tipoU;
        this.CURP = CURP;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Persona getCURP() {
        return CURP;
    }

    public void setCURP(Persona CURP) {
        this.CURP = CURP;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public TipoUsuario getTipoU() {
        return TipoU;
    }

    public void setTipoU(TipoUsuario tipoU) {
        TipoU = tipoU;
    }
}
