package com.example.PruebaCRUD.DTO.Saes;

public class ETSDTOSaes {
    private int idETS;
    private String idUA;
    private String unidadAprendizaje;
    private char tipoETS;
    private String idPeriodo;
    private String turno;
    private String fecha;
    private Integer cupo;
    private Integer duracion;
    private String programaAcademico;

    public ETSDTOSaes(int idETS, String idUA, String UA, char tipoETS, String idPeriodo, String turno, String fecha,
                      Integer cupo, Integer duracion, String programaAcademico) {
        this.idETS = idETS;
        this.idUA = idUA;
        this.unidadAprendizaje = UA;
        this.tipoETS = tipoETS;
        this.idPeriodo = idPeriodo;
        this.turno = turno;
        this.fecha = fecha;
        this.cupo = cupo;
        this.duracion = duracion;
        this.programaAcademico = programaAcademico;
    }

    public int getIdETS() {
        return idETS;
    }

    public void setIdETS(int idETS) {
        this.idETS = idETS;
    }

    public String getIdUA() {
        return idUA;
    }

    public void setIdUA(String idUA) {
        this.idUA = idUA;
    }

    public String getUnidadAprendizaje() {
        return unidadAprendizaje;
    }

    public void setUnidadAprendizaje(String unidadAprendizaje) {
        this.unidadAprendizaje = unidadAprendizaje;
    }

    public char getTipoETS() {
        return tipoETS;
    }

    public void setTipoETS(char tipoETS) {
        this.tipoETS = tipoETS;
    }

    public String getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(String idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getCupo() {
        return cupo;
    }

    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getProgramaAcademico() {
        return programaAcademico;
    }

    public void setProgramaAcademico(String programaAcademico) {
        this.programaAcademico = programaAcademico;
    }

    @Override
    public String toString() {
        return "ETSDTOSaes{" +
                "idETS=" + idETS +
                ", idUA='" + idUA + '\'' +
                ", unidadAprendizaje='" + unidadAprendizaje + '\'' +
                ", tipoETS=" + tipoETS +
                ", idPeriodo='" + idPeriodo + '\'' +
                ", turno='" + turno + '\'' +
                ", fecha='" + fecha + '\'' +
                ", cupo=" + cupo +
                ", duracion=" + duracion +
                ", programaAcademico='" + programaAcademico + '\'' +
                '}';
    }
}
