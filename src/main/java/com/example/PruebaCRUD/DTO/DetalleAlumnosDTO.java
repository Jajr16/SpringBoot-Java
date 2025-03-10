package com.example.PruebaCRUD.DTO;

public class DetalleAlumnosDTO {
    private String imagenCredencial;
    private String boleta;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String ets;
    private String docente;
    private String turno;
    private String fecha;

    public DetalleAlumnosDTO(String imagenCredencial, String boleta, String nombre, String apellidoP, String apellidoM, String ets, String docente,
                             String turno, String fecha) {
        this.imagenCredencial = imagenCredencial;
        this.boleta = boleta;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.ets = ets;
        this.docente = docente;
        this.turno = turno;
        this.fecha = fecha;
    }


    public String getImagenCredencial() {
        return imagenCredencial;
    }

    public void setImagenCredencial(String imagenCredencial) {
        this.imagenCredencial = imagenCredencial;
    }

    public String getBoleta() {
        return boleta;
    }

    public void setBoleta(String boleta) {
        this.boleta = boleta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getEts() {
        return ets;
    }

    public void setEts(String ets) {
        this.ets = ets;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
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

    @Override
    public String toString() {
        return "DetalleAlumnosDTO{" +
                "imagenCredencial='" + imagenCredencial + '\'' +
                ", boleta='" + boleta + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidoP='" + apellidoP + '\'' +
                ", apellidoM='" + apellidoM + '\'' +
                ", ets='" + ets + '\'' +
                ", docente='" + docente + '\'' +
                ", turno='" + turno + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
