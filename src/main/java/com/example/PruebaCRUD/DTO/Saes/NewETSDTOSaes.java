package com.example.PruebaCRUD.DTO.Saes;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de la tabla ETS (En esta ocasión es para el Request de crear un nuevo ETS)
 */
public class NewETSDTOSaes {
    private Integer idPeriodo;
    private String Turno;
    private String Fecha;
    private Integer Cupo;
    private String idUA;
    private Integer Duracion;

    private Integer salon;

    private String docenteCURP;
    private boolean titular;

    // ==================== CONSTRUCTORES =====================
    public NewETSDTOSaes() {}

    public NewETSDTOSaes(String UnidadAprendizaje, Integer idPeriodo, String Turno, String Fecha, Integer Cupo, Integer Duracion) {
        this.idUA = UnidadAprendizaje;
        this.idPeriodo = idPeriodo;
        this.Turno = Turno;
        this.Fecha = Fecha;
        this.Cupo = Cupo;
        this.Duracion = Duracion;
    }

    public NewETSDTOSaes(String UnidadAprendizaje, Integer idPeriodo, String Turno, String Fecha, Integer Cupo, Integer Duracion, Integer salon) {
        this.idUA = UnidadAprendizaje;
        this.idPeriodo = idPeriodo;
        this.Turno = Turno;
        this.Fecha = Fecha;
        this.Cupo = Cupo;
        this.Duracion = Duracion;
        this.salon = salon;
    }

    public NewETSDTOSaes(String UnidadAprendizaje, Integer idPeriodo, String Turno, String Fecha, Integer Cupo, Integer Duracion, String docenteCURP, boolean titular) {
        this.idUA = UnidadAprendizaje;
        this.idPeriodo = idPeriodo;
        this.Turno = Turno;
        this.Fecha = Fecha;
        this.Cupo = Cupo;
        this.Duracion = Duracion;
        this.docenteCURP = docenteCURP;
        this.titular = titular;
    }

    public NewETSDTOSaes(String UnidadAprendizaje, Integer idPeriodo, String Turno, String Fecha, Integer Cupo, Integer Duracion, Integer salon, String docenteCURP, boolean titular) {
        this.idUA = UnidadAprendizaje;
        this.idPeriodo = idPeriodo;
        this.Turno = Turno;
        this.Fecha = Fecha;
        this.Cupo = Cupo;
        this.Duracion = Duracion;
        this.salon = salon;
        this.docenteCURP = docenteCURP;
        this.titular = titular;
    }

    // ==================== SETTERS AND GETTERS ====================
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

    public Integer getSalon() {
        return salon;
    }

    public void setSalon(Integer salon) {
        this.salon = salon;
    }

    public String getDocenteCURP() {
        return docenteCURP;
    }

    public void setDocenteCURP(String docenteCURP) {
        this.docenteCURP = docenteCURP;
    }

    public boolean isTitular() {
        return titular;
    }

    public void setTitular(boolean titular) {
        this.titular = titular;
    }

    @Override
    public String toString() {
        return "NewETSDTOSaes{" +
                "idPeriodo=" + idPeriodo +
                ", Turno='" + Turno + '\'' +
                ", Fecha='" + Fecha + '\'' +
                ", Cupo=" + Cupo +
                ", idUA='" + idUA + '\'' +
                ", Duracion=" + Duracion +
                ", salon=" + salon +
                ", docenteCURP='" + docenteCURP + '\'' +
                ", titular=" + titular +
                '}';
    }
}
