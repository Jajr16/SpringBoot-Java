package com.example.PruebaCRUD.DTO.Saes;

public class SalonETSDTOSaes {
    private Integer salon;
    private Integer idETS;

    public SalonETSDTOSaes(Integer salon, Integer idETS) {
        this.salon = salon;
        this.idETS = idETS;
    }

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
