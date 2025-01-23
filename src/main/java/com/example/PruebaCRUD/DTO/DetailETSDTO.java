package com.example.PruebaCRUD.DTO;

import java.util.List;

public class DetailETSDTO {

    private int idETS;
    private String UnidadAprendizaje;
    private char tipoETS;
    private String idPeriodo;
    private String Turno;
    private String Fecha;
    private Integer Cupo;
    private Integer Duracion;
    private List<SalonesDTO> Salon;

    public DetailETSDTO(Integer idETS, String UnidadAprendizaje, char tipoETS, String idPeriodo, String Turno, String Fecha, Integer Cupo, Integer Duracion, List<SalonesDTO> Salon) {
        this.idETS = idETS;
        this.UnidadAprendizaje = UnidadAprendizaje;
        this.tipoETS = tipoETS;
        this.idPeriodo = idPeriodo;
        this.Turno = Turno;
        this.Fecha = Fecha;
        this.Cupo = Cupo;
        this.Duracion = Duracion;
        this.Salon = Salon;
    }

    public DetailETSDTO(Integer idETS, String UnidadAprendizaje, char tipoETS, String idPeriodo, String Turno, String Fecha, Integer Cupo, Integer Duracion) {
        this.idETS = idETS;
        this.UnidadAprendizaje = UnidadAprendizaje;
        this.tipoETS = tipoETS;
        this.idPeriodo = idPeriodo;
        this.Turno = Turno;
        this.Fecha = Fecha;
        this.Cupo = Cupo;
        this.Duracion = Duracion;
    }

    public String getUnidadAprendizaje() {
        return UnidadAprendizaje;
    }

    public void setUnidadAprendizaje(String unidadAprendizaje) {
        UnidadAprendizaje = unidadAprendizaje;
    }

    public char getTipoETS() {
        return tipoETS;
    }

    public void setTipoETS(char tipoETS) {
        this.tipoETS = tipoETS;
    }

    public int getIdETS() {
        return idETS;
    }

    public void setIdETS(int idETS) {
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

    public int getCupo() {
        return Cupo;
    }

    public void setCupo(int cupo) {
        Cupo = cupo;
    }

    public int getDuracion() {
        return Duracion;
    }

    public void setDuracion(int duracion) {
        Duracion = duracion;
    }

    public List<SalonesDTO> getSalones() {
        return Salon;
    }

    public void setSalones(List<SalonesDTO> salones) {
        this.Salon = salones;
    }

    @Override
    public String toString() {
        return "DetailETSDTO{" +
                "idETS=" + idETS +
                ", UnidadAprendizaje='" + UnidadAprendizaje + '\'' +
                ", tipoETS=" + tipoETS +
                ", idPeriodo='" + idPeriodo + '\'' +
                ", Turno='" + Turno + '\'' +
                ", Fecha='" + Fecha + '\'' +
                ", Cupo=" + Cupo +
                ", Duracion=" + Duracion +
                ", Salon=" + Salon +
                '}';
    }
}
