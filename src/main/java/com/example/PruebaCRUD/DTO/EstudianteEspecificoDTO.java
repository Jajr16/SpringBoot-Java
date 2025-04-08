package com.example.PruebaCRUD.DTO;

public class EstudianteEspecificoDTO {
    String nombre;
    String apellidoP;
    String apellidoM;
    String boleta;
    String curp;
    String unidadAcademica;

    // ==================== CONSTRUCTORES =====================
    public EstudianteEspecificoDTO(String nombre, String apellidoP, String apellidoM, String boleta, String curp, String unidadAcademica) {
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.boleta = boleta;
        this.curp = curp;
        this.unidadAcademica = unidadAcademica;
    }

    // ==================== SETTERS AND GETTERS ====================

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

    public String getBoleta() {
        return boleta;
    }

    public void setBoleta(String boleta) {
        this.boleta = boleta;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getUnidadAcademica() {
        return unidadAcademica;
    }

    public void setUnidadAcademica(String unidadAcademica) {
        this.unidadAcademica = unidadAcademica;
    }

    @Override
    public String toString() {
        return "EstudianteEspecificoDTO{" +
                "nombre='" + nombre + '\'' +
                ", apellidoP='" + apellidoP + '\'' +
                ", apellidoM='" + apellidoM + '\'' +
                ", boleta='" + boleta + '\'' +
                ", curp='" + curp + '\'' +
                ", unidadAcademica='" + unidadAcademica + '\'' +
                '}';


    }
}

