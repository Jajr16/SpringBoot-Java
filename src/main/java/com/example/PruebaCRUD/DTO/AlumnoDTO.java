package com.example.PruebaCRUD.DTO;

public class AlumnoDTO {

    private String boleta;
    private String Nombre;
    private String Apellido_P;
    private String Apellido_M;
    private String fecha;  // Nuevo campo para la fecha
    private String periodo; // Nuevo campo para el periodo

    // Constructor actualizado
    public AlumnoDTO(String boleta, String Nombre, String Apellido_P, String Apellido_M, String fecha, String periodo) {
        this.boleta = boleta;
        this.Nombre = Nombre;
        this.Apellido_P = Apellido_P;
        this.Apellido_M = Apellido_M;
        this.fecha = fecha;
        this.periodo = periodo;
    }

    // Getters y Setters
    public String getBoleta() {
        return boleta;
    }

    public void setBoleta(String boleta) {
        this.boleta = boleta;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido_P() {
        return Apellido_P;
    }

    public void setApellido_P(String Apellido_P) {
        this.Apellido_P = Apellido_P;
    }

    public String getApellido_M() {
        return Apellido_M;
    }

    public void setApellido_M(String Apellido_M) {
        this.Apellido_M = Apellido_M;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    // toString (opcional)
    @Override
    public String toString() {
        return "AlumnoDTO{" +
                "boleta='" + boleta + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Apellido_P='" + Apellido_P + '\'' +
                ", Apellido_M='" + Apellido_M + '\'' +
                ", fecha='" + fecha + '\'' +
                ", periodo='" + periodo + '\'' +
                '}';
    }
}
