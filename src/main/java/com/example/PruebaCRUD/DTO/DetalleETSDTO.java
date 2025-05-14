package com.example.PruebaCRUD.DTO;

import java.util.List;

/**
 * Clase DTO la cuál sirve para pasar o recibir datos entre un cliente y un servidor, en este caso, ayudará a pasar
 * datos para enlistar datos del ETS y del salón
 */
public class DetalleETSDTO {

    private ETSDTO ets;
    private List<SalonesDTO> Salon;

    // ==================== CONSTRUCTORES =====================
    public DetalleETSDTO(ETSDTO ets, List<SalonesDTO> Salon) {
        this.ets = ets;
        this.Salon = Salon;
    }

    public DetalleETSDTO(ETSDTO ets) {
        this.ets = ets;
    }

    // ==================== SETTERS AND GETTERS ====================
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
        return "DetalleETSDTO{" +
                "ets=" + ets +
                ", Salon=" + Salon +
                '}';
    }
}
