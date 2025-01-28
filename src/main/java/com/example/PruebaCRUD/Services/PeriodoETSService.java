package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.TimeToETSDTO;
import com.example.PruebaCRUD.Repositories.periodoETSRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class PeriodoETSService {
    private final periodoETSRepository periodRepo;

    public PeriodoETSService(periodoETSRepository periodoService) {
        this.periodRepo = periodoService;
    }

    public TimeToETSDTO getTimeToETS() {
        Integer año = LocalDate.now().getYear();
        Integer mes = LocalDate.now().getMonthValue();

        LocalDate today = LocalDate.now();

        String periodo = "";

        String año_abreviado = año.toString().substring(2);

        if (mes >= 8 || mes <= 1) {
            periodo = año_abreviado.concat("/1");
        } else {
            periodo = año_abreviado.concat("/2");
        }

        String fechaETS = periodRepo.findFechaByPeriodo(periodo);
        LocalDate fechaETSLD = LocalDate.parse(fechaETS);

        long diasDeDiferencia = ChronoUnit.DAYS.between(today, fechaETSLD);

        String response = "Faltan " + diasDeDiferencia + " días para el periodo de ETS.";

        System.out.println(response);


        return new TimeToETSDTO(response);
    }
}
