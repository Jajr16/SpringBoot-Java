package com.example.PruebaCRUD.DTO;

import java.util.List;

public class DetailETSDTO {

    private ETSDTO ets;
    private List<SalonesDTO> Salon;

    public DetailETSDTO(ETSDTO ets, List<SalonesDTO> Salon) {
        this.ets = ets;
        this.Salon = Salon;
    }

    public DetailETSDTO(ETSDTO ets) {
        this.ets = ets;
    }

    public ETSDTO getEts() {
        return ets;
    }

    public void setEts(ETSDTO ets) {
        this.ets = ets;
    }

    public List<SalonesDTO> getSalon() {
        return Salon;
    }

    public void setSalon(List<SalonesDTO> salon) {
        Salon = salon;
    }

    @Override
    public String toString() {
        return "DetailETSDTO{" +
                "ets=" + ets +
                ", Salon=" + Salon +
                '}';
    }
}
