package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

import java.util.List;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "cargo") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class Cargo {

    @Id // Indica que es la llave primaria de la tabla
    // Indica que esta columna se generará automáticamente (autoincrementandose)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cargo", nullable = false) // Indica que la variable es una columna
    private Integer id_cargo;

    @Column(name = "Cargo", nullable = false, length = 100)
    private String cargo;

    //    ================= RELACIONES INVERSAS CON OTRAS TABLAS ========================
    @OneToMany(mappedBy = "idCargoCD", cascade = CascadeType.PERSIST)
    private List<CargoDocente> CDDetails;

    // ==================== CONSTRUCTORES =====================
    public Cargo() {}

    public Cargo(String Cargo) {
        this.cargo = Cargo;
    }

    public Cargo(Integer idCargo, String Cargo) {
        this.id_cargo = idCargo;
        this.cargo = Cargo;
    }

    // ==================== SETTERS AND GETTERS ====================
    public Integer getIdCargo() {
        return id_cargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.id_cargo = idCargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public List<CargoDocente> getCDDetails() {
        return CDDetails;
    }

    public void setCDDetails(List<CargoDocente> CDDetails) {
        this.CDDetails = CDDetails;
    }
}
