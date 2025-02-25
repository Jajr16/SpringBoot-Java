package com.example.PruebaCRUD.DTO;

public class ListAlumnosDTO {

    private Integer idETS;
    private String boleta;
    private String cURP;
    private String nombreA;
    private String apellidoP;
    private String apellidoM;
    private String sexo;
    private String correo;
    private String carrera;
    private Boolean aceptado; // Nueva propiedad agregada

    // Constructor
    public ListAlumnosDTO(Integer idETS, String Boleta, String CURP, String NombreA,
                          String ApellidoP, String ApellidoM, String Sexo,
                          String Correo, String Carrera, Boolean Aceptado) {
        this.idETS = idETS;
        this.boleta = Boleta;
        this.cURP = CURP;
        this.nombreA = NombreA;
        this.apellidoP = ApellidoP;
        this.apellidoM = ApellidoM;
        this.sexo = Sexo;
        this.correo = Correo;
        this.carrera = Carrera;
        this.aceptado = Aceptado;
    }

    // Getters and Setters
    public Integer getIdETS() {
        return idETS;
    }

    public void setIdETS(Integer idETS) {
        this.idETS = idETS;
    }

    public String getBoleta() {
        return boleta;
    }

    public void setBoleta(String boleta) {
        boleta = boleta;
    }

    public String getCURP() {
        return cURP;
    }

    public void setCURP(String CURP) {
        this.cURP = CURP;
    }

    public String getNombreA() {
        return nombreA;
    }

    public void setNombreA(String nombreA) {
        nombreA = nombreA;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        apellidoM = apellidoM;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        sexo = sexo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        correo = correo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        carrera = carrera;
    }

    public Boolean getAceptado() {
        return aceptado;
    }

    public void setAceptado(Boolean aceptado) {
        aceptado = aceptado;
    }

    @Override
    public String toString() {
        return "ListAlumnosDTO{" +
                "idETS=" + idETS +
                ", Boleta='" + boleta + '\'' +
                ", CURP='" + cURP + '\'' +
                ", NombreA='" + nombreA + '\'' +
                ", ApellidoP='" + apellidoP + '\'' +
                ", ApellidoM='" + apellidoM + '\'' +
                ", Sexo='" + sexo + '\'' +
                ", Correo='" + correo + '\'' +
                ", Carrera='" + carrera + '\'' +
                ", Aceptado=" + aceptado +
                '}';
    }
}


