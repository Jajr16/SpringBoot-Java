package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "turno") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class Turno {

    @Id // Indica que es la llave primaria de la tabla
    // Indica que esta columna se generará automáticamente (autoincrementandose)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idTurno", nullable = false) // Notación que indica que la variable será una columna
    private Integer idTurno;

    @Column(name = "Nombre", nullable = false, length = 10)
    private String nombre;

    // ==================== CONSTRUCTORES =====================
    public Turno() {}

    public Turno(Integer IdTurno, String Nombre) {
        this.idTurno = IdTurno;
        this.nombre = Nombre;
    }

    public Turno(String Nombre) {
        this.nombre = Nombre;
    }

    // ==================== SETTERS AND GETTERS ====================
    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
