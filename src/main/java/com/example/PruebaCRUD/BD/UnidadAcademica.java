package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "unidadacademica") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class UnidadAcademica {

    public UnidadAcademica() {}

    @Id // Indica que es la llave primaria de la tabla
    // Indica que esta columna se generará automáticamente (autoincrementandose)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_Escuela") // Notación que indica que la variable será una columna
    private Integer id_Escuela;

    @Column(name = "Nombre", nullable = false, unique = true, length = 100)
    private String nombre;

//    @OneToMany(mappedBy = "idUA", cascade = CascadeType.PERSIST)
//    private List<EscuelaPrograma> detailsUA;

    // ==================== CONSTRUCTORES =====================
    public UnidadAcademica (int idEscuela, String nombre) {
        this.id_Escuela = idEscuela;
        this.nombre = nombre;
    }

    public UnidadAcademica (String nombre) {
        this.nombre = nombre;
    }

    // ==================== SETTERS AND GETTERS ====================
    public Integer getIdEscuela() {
        return id_Escuela;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdEscuela(Integer idEscuela) {
        this.id_Escuela = idEscuela;
    }

//    public List<EscuelaPrograma> getDetailsUA() {
//        return detailsUA;
//    }
//
//    public void setDetailsUA(List<EscuelaPrograma> detailsUA) {
//        this.detailsUA = detailsUA;
//    }
}
