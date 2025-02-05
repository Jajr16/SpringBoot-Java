package com.example.PruebaCRUD.DTO;

public class NewETSDTOSaes {
    private Integer idPeriodo;
    private String Turno;
    private String Fecha;
    private Integer Cupo;
    private String idUA;
    private Integer Duracion;

    public NewETSDTOSaes(String UnidadAprendizaje, Integer idPeriodo, String Turno, String Fecha, Integer Cupo, Integer Duracion) {
        this.idUA = UnidadAprendizaje;
        this.idPeriodo = idPeriodo;
        this.Turno = Turno;
        this.Fecha = Fecha;
        this.Cupo = Cupo;
        this.Duracion = Duracion;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
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

    public Integer getCupo() {
        return Cupo;
    }

    public void setCupo(Integer cupo) {
        Cupo = cupo;
    }

    public String getIdUA() {
        return idUA;
    }

    public void setIdUA(String idUA) {
        this.idUA = idUA;
    }

    public Integer getDuracion() {
        return Duracion;
    }

    public void setDuracion(Integer duracion) {
        Duracion = duracion;
    }

    @Override
    public String toString() {
        return "NewETSDTOSaes{" +
                "idPeriodo='" + idPeriodo + '\'' +
                ", Turno='" + Turno + '\'' +
                ", Fecha='" + Fecha + '\'' +
                ", Cupo=" + Cupo +
                ", idUA='" + idUA + '\'' +
                ", Duracion=" + Duracion +
                '}';
    }
}
