package com.example.PruebaCRUD.DTO;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de la tabla Salon
 */
public class SalonesDTO {
    private Integer numSalon;
    private Integer Edificio;
    private Integer Piso;
    private String tipoSalon;

    // ==================== CONSTRUCTORES =====================
    public SalonesDTO(){}

    public SalonesDTO(Integer numSalon, Integer Edificio, Integer piso, String tipoSalon) {
        this.numSalon = numSalon;
        this.Edificio = Edificio;
        this.Piso = piso;
        this.tipoSalon = tipoSalon;
    }

    // ==================== SETTERS AND GETTERS ====================
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

    @Override
    public String toString() {
        return "SalonesDTO{" +
                "numSalon=" + numSalon +
                ", Edificio=" + Edificio +
                ", Piso=" + Piso +
                ", tipoSalon='" + tipoSalon + '\'' +
                '}';
    }
}
