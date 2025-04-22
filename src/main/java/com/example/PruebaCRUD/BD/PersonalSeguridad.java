package com.example.PruebaCRUD.BD;

import jakarta.persistence.*;

/**
 *  Clase para crear una tabla en la base de datos
 */
@Entity // Notación para indicar que esta clase es una entidad (sirve para JPAQL)
@Table(name = "personalseguridad") // Notación que relaciona el nombre de la tabla que se le asigna con la de la BD
public class PersonalSeguridad {

    @Id // Indica que es la llave primaria de la tabla
    @Column(name = "rfc", nullable = false, length = 13) // Notación que indica que la variable será una columna
    private String rfc;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Notación para indicar una relación entre tablas
    // Notación para especificar el nombre de la columna que tendrá la relación
    @JoinColumn(name = "CURP", nullable = false)
    private Persona CURP;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turno", nullable = false)
    private Turno Turno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo", nullable = false)
    private CargoPS Cargo;

    public PersonalSeguridad() {
    }

    public PersonalSeguridad(String RFC, Persona CURP, Turno turno, CargoPS Cargo) {
        this.rfc = RFC;
        this.CURP = CURP;
        this.Turno = turno;
        this.Cargo = Cargo;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Persona getCURP() {
        return CURP;
    }

    public void setCURP(Persona CURP) {
        this.CURP = CURP;
    }

    public Turno getTurno() {
        return Turno;
    }

    public void setTurno(Turno turno) {
        Turno = turno;
    }

    public CargoPS getCargo() {
        return Cargo;
    }

    public void setCargo(CargoPS cargo) {
        Cargo = cargo;
    }
}
