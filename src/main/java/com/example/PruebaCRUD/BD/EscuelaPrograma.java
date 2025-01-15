package com.example.PruebaCRUD.BD;

import com.example.PruebaCRUD.BD.PKCompuesta.EscuelaProgramaPK;
import jakarta.persistence.*;

@Entity
@Table(name = "escuelaprograma")
public class EscuelaPrograma {

    @EmbeddedId
    private EscuelaProgramaPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idEscuela")
    @JoinColumn(name = "idEscuela", nullable = false)
    private UnidadAcademica idEscuela;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPA")
    @JoinColumn(name = "idPA", nullable = false)
    private ProgramaAcademico idPA;

    public EscuelaPrograma(){}

    public EscuelaPrograma(EscuelaProgramaPK id) {
        this.id = id;
    }

    public UnidadAcademica getIdEscuela() {
        return idEscuela;
    }

    public void setIdEscuela(UnidadAcademica idEscuela) {
        this.idEscuela = idEscuela;
    }

    public ProgramaAcademico getIdPA() {
        return idPA;
    }

    public void setIdPA(ProgramaAcademico idPA) {
        this.idPA = idPA;
    }


}
