package com.example.PruebaCRUD.DTO;

public class ListAlumnosDTO {

    private Integer idETS;
    private String Boleta;
    private String CURP;
    private String NombreA;
    private String ApellidoP;
    private String ApellidoM;
    private String Sexo;
    private String Correo;
    private String Carrera;
    private Boolean Aceptado; // Nueva propiedad agregada

    // Constructor
    public ListAlumnosDTO(Integer idETS, String Boleta, String CURP, String NombreA,
                          String ApellidoP, String ApellidoM, String Sexo,
                          String Correo, String Carrera, Boolean Aceptado) {
        this.idETS = idETS;
        this.Boleta = Boleta;
        this.CURP = CURP;
        this.NombreA = NombreA;
        this.ApellidoP = ApellidoP;
        this.ApellidoM = ApellidoM;
        this.Sexo = Sexo;
        this.Correo = Correo;
        this.Carrera = Carrera;
        this.Aceptado = Aceptado;
    }

    // Getters and Setters
    public Integer getIdETS() {
        return idETS;
    }

    public void setIdETS(Integer idETS) {
        this.idETS = idETS;
    }

    public String getBoleta() {
        return Boleta;
    }

    public void setBoleta(String boleta) {
        Boleta = boleta;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getNombreA() {
        return NombreA;
    }

    public void setNombreA(String nombreA) {
        NombreA = nombreA;
    }

    public String getApellidoP() {
        return ApellidoP;
    }

    public void setApellidoP(String apellidoP) {
        ApellidoP = apellidoP;
    }

    public String getApellidoM() {
        return ApellidoM;
    }

    public void setApellidoM(String apellidoM) {
        ApellidoM = apellidoM;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getCarrera() {
        return Carrera;
    }

    public void setCarrera(String carrera) {
        Carrera = carrera;
    }

    public Boolean getAceptado() {
        return Aceptado;
    }

    public void setAceptado(Boolean aceptado) {
        Aceptado = aceptado;
    }

    @Override
    public String toString() {
        return "ListAlumnosDTO{" +
                "idETS=" + idETS +
                ", Boleta='" + Boleta + '\'' +
                ", CURP='" + CURP + '\'' +
                ", NombreA='" + NombreA + '\'' +
                ", ApellidoP='" + ApellidoP + '\'' +
                ", ApellidoM='" + ApellidoM + '\'' +
                ", Sexo='" + Sexo + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Carrera='" + Carrera + '\'' +
                ", Aceptado=" + Aceptado +
                '}';
    }
}


