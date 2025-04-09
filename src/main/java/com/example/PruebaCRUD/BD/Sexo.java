package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "sexo") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class Sexo {

    @Id // Indica que es la llave primaria de la tabla
    // Indica que esta columna se generará automáticamente (autoincrementandose)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idSexo") // Notación que indica que la variable será una columna
    private Integer idSexo;

    @Column(name = "Nombre", nullable = false, unique = true, length = 9)
    private String nombre;

    // ==================== CONSTRUCTORES =====================
    public Sexo() {}

    public Sexo(int idSexo, String nombre) {
        this.idSexo = idSexo;
        this.nombre = nombre;
    }

    public Sexo(String nombre) {
        this.nombre = nombre;
    }

    // ==================== SETTERS AND GETTERS ====================
    public Integer getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(Integer idSexo) {
        this.idSexo = idSexo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
