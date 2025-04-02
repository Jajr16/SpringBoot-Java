package com.example.PruebaCRUD.DTO;

public class SolicitudReemplazoDTO {
    private Integer idETS;
    private String docenteRFC;
    private String motivo;
    private String estatus;

    public SolicitudReemplazoDTO(Integer idETS, String docenteRFC, String motivo, String estatus) {
        this.idETS = idETS;
        this.docenteRFC = docenteRFC;
        this.motivo = motivo;
        this.estatus = estatus;
    }

    public Integer getIdETS() {
        return idETS;
    }

    public void setIdETS(Integer idETS) {
        this.idETS = idETS;
    }

    public String getDocenteRFC() {
        return docenteRFC;
    }

    public void setDocenteRFC(String docenteRFC) {
        this.docenteRFC = docenteRFC;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "SolicitudReemplazoDTO{" +
                "idETS=" + idETS +
                ", docenteRFC='" + docenteRFC + '\'' +
                ", motivo='" + motivo + '\'' +
                ", estatus='" + estatus + '\'' +
                '}';
    }
}
