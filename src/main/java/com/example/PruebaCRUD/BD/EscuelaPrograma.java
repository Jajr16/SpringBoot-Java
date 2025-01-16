package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.EscuelaProgramaPK;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "escuelaprograma")
public class EscuelaPrograma implements Serializable {

    @EmbeddedId
    private EscuelaProgramaPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEscuela")
    @JoinColumn(name = "id_Escuela", nullable = false)
    private UnidadAcademica idUA;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPA")
    @JoinColumn(name = "idPA", nullable = false)
    private ProgramaAcademico idPAcad;



    public EscuelaPrograma(){}

    public EscuelaPrograma(EscuelaProgramaPK id) {
        this.id = id;
    }

    public EscuelaPrograma(EscuelaProgramaPK id, UnidadAcademica idEscuela, ProgramaAcademico idPA) {
        this.id = id;
        this.idUA = idEscuela;
        this.idPAcad = idPA;
    }

    public EscuelaPrograma(UnidadAcademica idEscuela, ProgramaAcademico idPA) {
        this.idUA = idEscuela;
        this.idPAcad = idPA;
    }

    public EscuelaProgramaPK getId() {
        return id;
    }

    public void setId(EscuelaProgramaPK id) {
        this.id = id;
    }

    public UnidadAcademica getIdEscuela() {
        return idUA;
    }

    public void setIdEscuela(UnidadAcademica idEscuela) {
        this.idUA = idEscuela;
    }

    public ProgramaAcademico getIdPA() {
        return idPAcad;
    }

    public void setIdPA(ProgramaAcademico idPA) {
        this.idPAcad = idPA;
    }


}
