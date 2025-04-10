package com.example.PruebaCRUD.DTO;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos detallados de los ETS
 */
public class ETSDTO {
    private int idETS;
    private String UnidadAprendizaje;
    private char tipoETS;
    private String idPeriodo;
    private String Turno;
    private String Fecha;
    private Integer Cupo;
    private Integer Duracion;
    private String Hora; // Nuevo atributo para la hora

    // ==================== CONSTRUCTORES =====================
    public ETSDTO(int idETS, String UnidadAprendizaje, char tipoETS, String idPeriodo, String Turno, String Fecha, Integer Cupo, Integer Duracion, String Hora) {
        this.idETS = idETS;
        this.UnidadAprendizaje = UnidadAprendizaje;
        this.tipoETS = tipoETS;
        this.idPeriodo = idPeriodo;
        this.Turno = Turno;
        this.Fecha = Fecha;
        this.Cupo = Cupo;
        this.Duracion = Duracion;
        this.Hora = Hora; // Inicializar el nuevo atributo
    }

    // ==================== SETTERS AND GETTERS ====================
    public int getIdETS() {
        return idETS;
    }

    public void setIdETS(int idETS) {
        this.idETS = idETS;
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

    public Integer getCupo() {
        return Cupo;
    }

    public void setCupo(Integer cupo) {
        Cupo = cupo;
    }

    public Integer getDuracion() {
        return Duracion;
    }

    public void setDuracion(Integer duracion) {
        Duracion = duracion;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    @Override
    public String toString() {
        return "ETSDTO{" +
                "idETS=" + idETS +
                ", UnidadAprendizaje='" + UnidadAprendizaje + '\'' +
                ", tipoETS=" + tipoETS +
                ", idPeriodo='" + idPeriodo + '\'' +
                ", Turno='" + Turno + '\'' +
                ", Fecha='" + Fecha + '\'' +
                ", Cupo=" + Cupo +
                ", Duracion=" + Duracion +
                ", Hora='" + Hora + '\'' + // Incluir la hora en el toString
                '}';
    }
}
