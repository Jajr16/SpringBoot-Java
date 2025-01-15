package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.CargoDocentePK;
import jakarta.persistence.*;

@Entity
@Table(name = "CargoDocente")
public class CargoDocente {

    @EmbeddedId
    private CargoDocentePK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("RFC")
    @JoinColumn(name = "RFC", nullable = false)
    private PersonalAcademico RFC;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCargo")
    @JoinColumn(name = "idCargo", nullable = false)
    private Cargo idCargo;

    public CargoDocente() {}

    public CargoDocente(CargoDocentePK id) {
        this.id = id;
    }

    public CargoDocentePK getId() {
        return id;
    }

    public void setId(CargoDocentePK id) {
        this.id = id;
    }

    public PersonalAcademico getRFC() {
        return RFC;
    }

    public void setRFC(PersonalAcademico RFC) {
        this.RFC = RFC;
    }

    public Cargo getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Cargo idCargo) {
        this.idCargo = idCargo;
    }
}
