package com.example.PruebaCRUD.DTO;

public class CredencialDTO {
    private String imagenCredencial;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String boleta;
    private String curp;
    private String carrera;
    private String unidadAcademica;

    public CredencialDTO(String imagenCredencial, String nombre, String apellidoP, String apellidoM, String boleta, String curp, String carrera, String unidadAcademica) {
        this.imagenCredencial = imagenCredencial;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.boleta = boleta;
        this.curp = curp;
        this.carrera = carrera;
        this.unidadAcademica = unidadAcademica;
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

    public String getcurp() {
        return curp;
    }

    public void setcurp(String curp) {
        this.curp = curp;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getUnidadAcademica() {
        return unidadAcademica;
    }

    public void setUnidadAcademica(String unidadAcademica) {
        this.unidadAcademica = unidadAcademica;
    }

    @Override
    public String toString() {
        return "CredencialDTO{" +
                "imagenCredencial='" + imagenCredencial + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidoP='" + apellidoP + '\'' +
                ", apellidoM='" + apellidoM + '\'' +
                ", boleta='" + boleta + '\'' +
                ", curp='" + curp + '\'' +
                ", carrera='" + carrera + '\'' +
                ", unidadAcademica='" + unidadAcademica + '\'' +
                '}';
    }
}
