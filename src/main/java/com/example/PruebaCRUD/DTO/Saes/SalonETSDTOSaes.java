package com.example.PruebaCRUD.DTO.Saes;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos de la tabla SalonETS
 */
public class SalonETSDTOSaes {
    private Integer salon;
    private Integer idETS;

    // ==================== CONSTRUCTORES =====================
    public SalonETSDTOSaes(Integer salon, Integer idETS) {
        this.salon = salon;
        this.idETS = idETS;
    }

    // ==================== SETTERS AND GETTERS ====================
    public Integer getSalon() {
        return salon;
    }

    public void setSalon(Integer salon) {
        this.salon = salon;
    }

    public Integer getIdETS() {
        return idETS;
    }

    public void setIdETS(Integer idETS) {
        this.idETS = idETS;
    }

    @Override
    public String toString() {
        return "SalonETSDTOSaes{" +
                "salon=" + salon +
                ", idETS=" + idETS +
                '}';
    }
}
