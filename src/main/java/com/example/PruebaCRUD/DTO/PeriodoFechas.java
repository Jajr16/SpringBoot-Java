package com.example.PruebaCRUD.DTO;

import java.time.LocalDate;

public class PeriodoFechas {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public PeriodoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }
}