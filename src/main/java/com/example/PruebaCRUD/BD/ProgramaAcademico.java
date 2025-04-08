package com.example.PruebaCRUD.BD;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "programaacademico") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class ProgramaAcademico {

    @Id // Indica que es la llave primaria de la tabla
    @Column(name = "idPA", nullable = false, length = 20) // Notación que indica que la variable será una columna
    private String idPA;

    @JsonProperty("Nombre") // Notación que indica el nombre de esta variable en un json
    @Column(name = "Nombre", nullable = false)
    private String nombre; // Cambiado a minúscula

    @JsonProperty("Descripcion")
    @Column(name = "Descripcion", nullable = false)
    private String Descripcion; // Cambiado a minúscula

    @OneToMany(mappedBy = "idPAcad", cascade = CascadeType.PERSIST) // Notación para indicar una relación entre tablas
    private List<EscuelaPrograma> detailsEP;

    // ==================== CONSTRUCTORES =====================
    public ProgramaAcademico() {}

    public ProgramaAcademico(String id_PA, String nombre, String descripcion) {
        this.idPA = id_PA;
        this.nombre = nombre;
        this.Descripcion = descripcion;
    }

    public ProgramaAcademico(String nombre, String descripcion) {
        this.nombre = nombre;
        this.Descripcion = descripcion;
    }

    // ==================== SETTERS AND GETTERS ====================
    public String getIdPA() {
        return idPA;
    }

    public void setIdPA(String idPA) {
        this.idPA = idPA;
    }

    public List<EscuelaPrograma> getDetailsEP() {
        return detailsEP;
    }

    public void setDetailsEP(List<EscuelaPrograma> detailsEP) {
        this.detailsEP = detailsEP;
    }

    public String getId_PA() {
        return idPA;
    }

    public void setId_PA(String id_PA) {
        this.idPA = id_PA;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }
}