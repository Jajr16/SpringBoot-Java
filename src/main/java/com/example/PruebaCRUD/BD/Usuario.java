package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "usuario") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class Usuario {

    @Id // Indica que es la llave primaria de la tabla
    @Column(name = "Usuario", nullable = false, length = 18) // Notación que indica que la variable será una columna
    private String usuario;

    @Column(name = "Password", nullable = false, length = 100)
    private String Password;

    //  FOREIGN KEYS
    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipou", nullable = false) // Notación para especificar el nombre de la columna que tendrá la relación
    private TipoUsuario TipoU;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURP", nullable = false)
    private Persona CURP;

     // ==================== CONSTRUCTORES =====================
    public Usuario() {}

    public Usuario(String usuario, String password, TipoUsuario tipoU, Persona CURP) {
        this.usuario = usuario;
        this.Password = password;
        this.TipoU = tipoU;
        this.CURP = CURP;
    }

    // ==================== SETTERS AND GETTERS ====================
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
