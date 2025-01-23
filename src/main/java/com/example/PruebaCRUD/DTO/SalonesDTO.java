package com.example.PruebaCRUD.DTO;

public class SalonesDTO {
    private Integer numSalon;
    private Integer Edificio;
    private Integer Piso;
    private String tipoSalon;

    public SalonesDTO(){}

    public SalonesDTO(Integer numSalon, Integer Edificio, Integer piso, String tipoSalon) {
        this.numSalon = numSalon;
        this.Edificio = Edificio;
        this.Piso = piso;
        this.tipoSalon = tipoSalon;
    }

    public Integer getNumSalon() {
        return numSalon;
    }

    public void setNumSalon(Integer numSalon) {
        this.numSalon = numSalon;
    }

    public Integer getEdificio() {
        return Edificio;
    }

    public void setEdificio(Integer edificio) {
        Edificio = edificio;
    }

    public Integer getPiso() {
        return Piso;
    }

    public void setPiso(Integer piso) {
        Piso = piso;
    }

    public String getTipoSalon() {
        return tipoSalon;
    }

    public void setTipoSalon(String tipoSalon) {
        this.tipoSalon = tipoSalon;
    }
}
