package com.example.PruebaCRUD.DTO;

public class ListETSResponseDTO {
    private Integer idETS;
    private String idPeriodo;
    private String Turno;
    private String Fecha;
    private String UnidadAprendizaje;
    private String error_message;

    public ListETSResponseDTO() {}

    public ListETSResponseDTO(Integer idETS, String Periodo, String Turno, String Fecha, String UnidadAprendizaje) {
        this.idETS = idETS;
        this.idPeriodo = Periodo;
        this.Turno = Turno;
        this.Fecha = Fecha;
        this.UnidadAprendizaje = UnidadAprendizaje;
    }

    public ListETSResponseDTO(String error_message) {
        this.error_message = error_message;
    }

    public Integer getIdETS() {
        return idETS;
    }

    public void setIdETS(Integer idETS) {
        this.idETS = idETS;
    }

    public String getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(String idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getTurno() {
        return Turno;
    }

    public void setTurno(String turno) {
        Turno = turno;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getUnidadAprendizaje() {
        return UnidadAprendizaje;
    }

    public void setUnidadAprendizaje(String unidadAprendizaje) {
        UnidadAprendizaje = unidadAprendizaje;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    @Override
    public String toString() {
        return "ListETSResponseDTO{" +
                "idETS=" + idETS +
                ", idPeriodo='" + idPeriodo + '\'' +
                ", Turno='" + Turno + '\'' +
                ", Fecha= '" + Fecha + '\'' +
                ", UnidadAprendizaje= '" + UnidadAprendizaje + '\'' +
                '}';
    }
}
