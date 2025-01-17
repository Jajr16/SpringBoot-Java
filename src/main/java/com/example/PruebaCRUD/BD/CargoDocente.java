package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.CargoDocentePK;
import jakarta.persistence.*;

@Entity
@Table(name = "cargodocente")
public class CargoDocente {

    @EmbeddedId
    private CargoDocentePK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("RFC")
    @JoinColumn(name = "RFC", nullable = false)
    private PersonalAcademico RFCCD;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCargo")
    @JoinColumn(name = "id_cargo", nullable = false)
    private Cargo idCargoCD;

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
        return RFCCD;
    }

    public void setRFC(PersonalAcademico RFC) {
        this.RFCCD = RFC;
    }

    public Cargo getIdCargo() {
        return idCargoCD;
    }

    public void setIdCargo(Cargo idCargo) {
        this.idCargoCD = idCargo;
    }
}
