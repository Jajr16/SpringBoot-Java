package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "unidadaprendizaje") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class UnidadAprendizaje {

    @Id // Indica que es la llave primaria de la tabla
    @Column(name = "idUA", nullable = false, length = 20) // Notación que indica que la variable será una columna
    private String idUA;

    @Column(name = "Nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "Descripcion", nullable = false, length = 200)
    private String Descripcion;

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name = "idPA", nullable = false) // Notación para especificar el nombre de la columna que tendrá la relación
    private ProgramaAcademico idPA;

    // ==================== CONSTRUCTORES =====================
    public UnidadAprendizaje() {}

    public UnidadAprendizaje(String idUA, String nombre, String Descripcion, ProgramaAcademico idPA) {
        this.idUA = idUA;
        this.nombre = nombre;
        this.Descripcion = Descripcion;
        this.idPA = idPA;
    }

    // ==================== SETTERS AND GETTERS ====================
    public String getIdUA() {
        return idUA;
    }

    public void setIdUA(String idUA) {
        this.idUA = idUA;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescirpcion() {
        return Descripcion;
    }

    public void setDescirpcion(String descirpcion) {
        this.Descripcion = descirpcion;
    }

    public ProgramaAcademico getIdPA() {
        return idPA;
    }

    public void setIdPA(ProgramaAcademico idPA) {
        this.idPA = idPA;
    }
}
