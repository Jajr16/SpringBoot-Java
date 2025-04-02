package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

import java.util.List;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "salon") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class Salon {

    @Id // Indica que es la llave primaria de la tabla
    @Column(name = "numSalon", nullable = false) // Notación que indica que la variable será una columna
    private Integer numSalon;

    @Column(name = "Edificio", nullable = false)
    private Integer Edificio;

    @Column(name = "Piso", nullable = false)
    private Integer Piso;

    /**
     * Relación en la BD de la tabla actual con la clase de la instancia. LAZY indica que las consultas a la tabla
     * principal no van a obtener los datos de la tabla relacionada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_salon", nullable = false) // Notación para especificar el nombre de la columna que tendrá la relación
    private TipoSalon tipoSalon;


    //    ================= RELACIONES INVERSAS CON OTRAS TABLAS ========================
    @OneToMany(mappedBy = "numSalonSETS", cascade = CascadeType.PERSIST)
    private List<SalonETS> SETSDetails;

    // ==================== CONSTRUCTORES =====================
    public Salon() {}

    public Salon(Integer numSalon, Integer Edificio, Integer Piso, TipoSalon tipoSalon) {
        this.numSalon = numSalon;
        this.Edificio = Edificio;
        this.Piso = Piso;
        this.tipoSalon = tipoSalon;
    }

    // ==================== SETTERS AND GETTERS ====================
    public Integer getNumSalon() {
        return numSalon;
    }

    public void setNumSalon(Integer numSalon) {
        this.numSalon = numSalon;
    }

    public Integer getEdificio() {
        return Edificio;
    }

    public void setEdificio(Integer edificio) {
        Edificio = edificio;
    }

    public Integer getPiso() {
        return Piso;
    }

    public void setPiso(Integer piso) {
        Piso = piso;
    }

    public TipoSalon getTipoSalon() {
        return tipoSalon;
    }

    public void setTipoSalon(TipoSalon tipoSalon) {
        this.tipoSalon = tipoSalon;
    }

    public List<SalonETS> getSETSDetails() {
        return SETSDetails;
    }

    public void setSETSDetails(List<SalonETS> SETSDetails) {
        this.SETSDetails = SETSDetails;
    }
}
