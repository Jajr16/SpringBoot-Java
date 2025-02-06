package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.periodoETS;
import com.example.PruebaCRUD.DTO.Saes.PeriodosETSProjectionSaes;
import com.example.PruebaCRUD.DTO.TimeToETSDTO;
import com.example.PruebaCRUD.Repositories.periodoETSRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodoETSService {
    HashMap<String, Object> datos = new HashMap<>();

    private final periodoETSRepository periodRepo;

    public PeriodoETSService(periodoETSRepository periodoService) {
        this.periodRepo = periodoService;
    }

    public List<periodoETS> getPeriodos() {
        return periodRepo.findAll();
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

    public ResponseEntity<Object> newPeriodo(periodoETS periodoETS) {
        datos = new HashMap<>();

        if (periodoETS.getPeriodo() == null || periodoETS.getPeriodo().isEmpty()
                || periodoETS.getFecha_Inicio() == null || periodoETS.getFecha_Fin() == null
                || periodoETS.getTipo() == '\0') {
            datos = new HashMap<>();
            datos.put("Error", true);
            datos.put("message", "No puedes dejar ningún campo vacío.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        Optional<periodoETS> response = periodRepo.findByPeriodoAndTipo(periodoETS.getPeriodo(), periodoETS.getTipo());

        if (response.isPresent()) {
            datos.put("Error", true);
            datos.put("message", "Ese periodo ya está registrada.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        periodRepo.save(periodoETS);
        datos.put("data", periodoETS);
        datos.put("message", "El periodo fue registrado exitosamente.");

        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public List<PeriodosETSProjectionSaes> obtenerPeriodos() {
        return periodRepo.findAllBy();
    }
}
